package com.ejemplo.infra;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.StringJoiner;

public final class SqlErrorDetailer {

  private static final int MAX_PARAM_LEN = 200;

  private SqlErrorDetailer() {}

  public static RuntimeException wrap(Exception ex, String operacion, String sql, Object... params) {
    StringBuilder sb = new StringBuilder(512);

    // Cabecera
    sb.append("Fallo JDBC ").append('[').append(operacion).append(']').append('\n');
    sb.append("⏱  Momento      : ").append(OffsetDateTime.now()).append('\n');
    sb.append("🧵  Hilo         : ").append(Thread.currentThread().getName()).append('\n');

    // SQL + parámetros
    sb.append("🧾  SQL          : ").append(safe(sql)).append('\n');
    sb.append("📦  Parámetros   : ").append(formatParams(params)).append('\n');

    // Si viene de SQLException, agregamos metadatos y cadena de causas/next
    SQLException sqle = findSqlException(ex);
    if (sqle != null) {
      appendConnectionMeta(sb, sqle);
      appendSqlExceptionChain(sb, sqle);
    } else {
      sb.append("⚠  No es SQLException. Causa raíz: ")
        .append(rootCause(ex).getClass().getName())
        .append(": ").append(safe(rootCause(ex).getMessage()))
        .append('\n');
    }

    // Stacktrace reducido (primeras 5 líneas) para ubicar el origen en tu código
    StackTraceElement[] trace = ex.getStackTrace();
    sb.append("🧭  Origen       : ");
    int limit = Math.min(5, trace.length);
    for (int i = 0; i < limit; i++) {
      StackTraceElement ste = trace[i];
      sb.append(ste.getClassName()).append('.').append(ste.getMethodName())
        .append('(').append(ste.getFileName()).append(':').append(ste.getLineNumber()).append(')');
      if (i < limit - 1) sb.append(" ⇢ ");
    }
    sb.append('\n');

    return new RuntimeException(sb.toString(), ex);
  }

  private static void appendConnectionMeta(StringBuilder sb, SQLException e) {
    try {
      // Buscar una Connection desde el Statement si existe
      // (No siempre accesible; esto es un "best effort")
      // Nota: muchos drivers no exponen la Connection aquí.
      // Si no, no pasa nada: se omite.
      // Se deja el bloque por si en el futuro quieres pasarlo explícito.
    } catch (Exception ignored) {}
    // Metadatos básicos útiles que sí tenemos:
    sb.append("🔢 SQLState     : ").append(nvl(e.getSQLState(), "—")).append('\n');
    sb.append("🏷  ErrorCode    : ").append(e.getErrorCode()).append('\n');
  }

  private static void appendSqlExceptionChain(StringBuilder sb, SQLException e) {
    int i = 0;
    for (SQLException cur = e; cur != null; cur = cur.getNextException(), i++) {
      sb.append(i == 0 ? "💥  Excepción    : " : "➡   Next[").append(i).append(i==0?"":"]").append('\n');
      sb.append("    Tipo        : ").append(cur.getClass().getName()).append('\n');
      sb.append("    SQLState    : ").append(nvl(cur.getSQLState(), "—")).append('\n');
      sb.append("    ErrorCode   : ").append(cur.getErrorCode()).append('\n');
      sb.append("    Mensaje     : ").append(safe(cur.getMessage())).append('\n');
    }

    // También recorremos la cadena de causas (cause)
    Throwable cause = e.getCause();
    int c = 0;
    while (cause != null && cause != e) {
      sb.append(c == 0 ? "🔗  Causes      :\n" : "");
      sb.append("    Cause[").append(c++).append("] ")
        .append(cause.getClass().getName()).append(": ")
        .append(safe(cause.getMessage())).append('\n');
      cause = cause.getCause();
    }
  }

  private static SQLException findSqlException(Throwable t) {
    while (t != null) {
      if (t instanceof SQLException) return (SQLException) t;
      t = t.getCause();
    }
    return null;
  }

  private static Throwable rootCause(Throwable t) {
    Throwable r = t;
    while (r.getCause() != null && r.getCause() != r) r = r.getCause();
    return r;
  }

  private static String formatParams(Object... params) {
    if (params == null || params.length == 0) return "[]";
    StringJoiner j = new StringJoiner(", ", "[", "]");
    for (Object p : params) j.add(renderParam(p));
    return j.toString();
  }

  private static String renderParam(Object p) {
    if (p == null) return "NULL";
    if (p instanceof byte[]) return "byte[" + ((byte[]) p).length + "]";
    if (p instanceof char[]) return "char[" + ((char[]) p).length + "]";
    String s = String.valueOf(p);
    if (s.length() > MAX_PARAM_LEN) s = s.substring(0, MAX_PARAM_LEN) + "…(trunc)";
    if (p instanceof String) return '"' + s + '"';
    if (p instanceof java.util.Date || p instanceof java.time.temporal.TemporalAccessor) return s;
    return s;
  }

  private static String safe(String s) {
    return s == null ? "—" : s;
  }

  private static String nvl(String s, String def) {
    return (s == null || s.isBlank()) ? def : s;
  }
}
