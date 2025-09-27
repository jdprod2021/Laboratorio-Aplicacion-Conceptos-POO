package com.ejemplo;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

public class DbSmokeTest {

    public static void run(DataSource ds, String vendor) throws Exception {
        vendor = vendor == null ? "" : vendor.trim().toUpperCase();

        try (Connection con = ds.getConnection()) {
            System.out.println("=== DB SMOKE TEST (" + vendor + ") ===");

            // 1) Tablas esperadas
            List<String> expected = Arrays.asList(
                    "PERSONA","PROFESOR","FACULTAD","PROGRAMA","CURSO",
                    "ESTUDIANTE","INSCRIPCION","CURSO_PROFESOR"
            );
            checkTablesExist(con, vendor, expected);

            // 2) Datos mínimos para relaciones
            long personaId = insertPersona(con, "Ada", "Lovelace", "ada@uni.edu");
            long decanoId  = insertPersona(con, "Grace", "Hopper", "grace@uni.edu");

            long facultadId = insertFacultad(con, "Ingeniería", decanoId);
            long programaId = insertPrograma(con, "Software", 10, new java.sql.Date(System.currentTimeMillis()), facultadId);
            long cursoId    = insertCurso(con, "UML", programaId, 1);

            // 3) UNIQUE(email) en PERSONA
            System.out.print("UNIQUE email en PERSONA: ");
            try {
                insertPersona(con, "Ada", "Lovelace", "ada@uni.edu"); // repetido
                System.out.println("FAIL (no lanzó excepción)");
            } catch (SQLException ex) {
                System.out.println("OK (" + shortSqlState(ex) + ")");
            }

            // 4) FK ESTUDIANTE(id)->PERSONA(id) con ON DELETE CASCADE
            System.out.print("FK ESTUDIANTE(id) ON DELETE CASCADE: ");
            long pEstId = insertPersona(con, "Linus", "Torvalds", "linus@uni.edu");
            insertEstudiante(con, pEstId, 1001L, programaId, 1, 4.5);

            // Borrar persona y verificar que estudiante se fue en cascada
            try (PreparedStatement del = con.prepareStatement("DELETE FROM PERSONA WHERE id = ?")) {
                del.setLong(1, pEstId);
                del.executeUpdate();
            }
            boolean estudianteSigue = existsById(con, "ESTUDIANTE", "id", pEstId);
            System.out.println(estudianteSigue ? "FAIL (sigue el estudiante)" : "OK (cascadió)");

            // 5) CHECK(semestre IN (1,2)) en INSCRIPCION
            System.out.print("CHECK semestre in (1,2) en INSCRIPCION: ");
            long p2 = insertPersona(con, "Alan", "Turing", "alan@uni.edu");
            insertEstudiante(con, p2, 1002L, programaId, 1, 4.9);
            try {
                insertInscripcion(con, cursoId, 2025, 3, p2); // semestre inválido
                System.out.println("FAIL (no lanzó excepción)");
            } catch (SQLException ex) {
                System.out.println("OK (" + shortSqlState(ex) + ")");
            }
            // Inserción válida
            insertInscripcion(con, cursoId, 2025, 1, p2);

            // 6) UQ compuesta en INSCRIPCION (curso_id, anio, semestre, estudiante_id)
            System.out.print("UNIQUE compuesto en INSCRIPCION: ");
            try {
                insertInscripcion(con, cursoId, 2025, 1, p2); // duplicado exacto
                System.out.println("FAIL (no lanzó excepción)");
            } catch (SQLException ex) {
                System.out.println("OK (" + shortSqlState(ex) + ")");
            }

            con.commit();
            System.out.println("=== SMOKE TEST FIN ===");
        }
    }

    /* -------------------------------------------------------
       Helpers de verificación de tablas por motor
       ------------------------------------------------------- */

    private static void checkTablesExist(Connection con, String vendor, List<String> expected) throws SQLException {
        Set<String> found = new HashSet<>();

        switch (vendor) {
            case "ORACLE": {
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT table_name FROM user_tables");
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) found.add(rs.getString(1));
                }
                break;
            }
            case "MYSQL": {
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT table_name FROM information_schema.tables WHERE table_schema = DATABASE()");
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) found.add(rs.getString(1));
                }
                break;
            }
            default: { // H2
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = SCHEMA()");
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) found.add(rs.getString(1));
                }
                break;
            }
        }

        // Normalizamos a MAYÚSCULAS (Oracle/H2 registran sin comillas en upper-case)
        Set<String> foundUpper = found.stream().map(s -> s == null ? null : s.toUpperCase(Locale.ROOT))
                .collect(Collectors.toSet());
        List<String> missing = expected.stream()
                .filter(t -> !foundUpper.contains(t))
                .collect(Collectors.toList());

        if (missing.isEmpty()) {
            System.out.println("Tablas: OK " + expected);
        } else {
            System.out.println("Tablas faltantes: " + missing);
            throw new IllegalStateException("Faltan tablas: " + missing);
        }
    }

    private static boolean existsById(Connection con, String table, String idCol, long id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + table + " WHERE " + idCol + " = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    /* -------------------------------------------------------
       Inserts mínimos (funcionan en H2/MySQL/Oracle con tus esquemas)
       ------------------------------------------------------- */

    private static long insertPersona(Connection con, String nombres, String apellidos, String email) throws SQLException {
        // Para Oracle/H2 con IDENTITY puedes usar RETURN_GENERATED_KEYS.
        String sql = "INSERT INTO PERSONA(nombres, apellidos, email) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombres);
            ps.setString(2, apellidos);
            ps.setString(3, email);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getLong(1);
            }
        }
        // Si el driver no soporta getGeneratedKeys (algunas configs de Oracle), obtén el MAX(id) reciente:
        try (PreparedStatement ps = con.prepareStatement("SELECT MAX(id) FROM PERSONA");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    private static long insertFacultad(Connection con, String nombre, long decanoId) throws SQLException {
        String sql = "INSERT INTO FACULTAD(nombre, decano_id) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setLong(2, decanoId);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getLong(1);
            }
        }
        try (PreparedStatement ps = con.prepareStatement("SELECT MAX(id) FROM FACULTAD");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    private static long insertPrograma(Connection con, String nombre, Integer duracion, Date registro, long facultadId) throws SQLException {
        String sql = "INSERT INTO PROGRAMA(nombre, duracion, registro, facultad_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            if (duracion == null) ps.setNull(2, Types.INTEGER); else ps.setInt(2, duracion);
            ps.setDate(3, registro);
            ps.setLong(4, facultadId);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getLong(1);
            }
        }
        try (PreparedStatement ps = con.prepareStatement("SELECT MAX(id) FROM PROGRAMA");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    private static long insertCurso(Connection con, String nombre, long programaId, int activo) throws SQLException {
        String sql = "INSERT INTO CURSO(nombre, programa_id, activo) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setLong(2, programaId);
            ps.setInt(3, activo);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getLong(1);
            }
        }
        try (PreparedStatement ps = con.prepareStatement("SELECT MAX(id) FROM CURSO");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    private static void insertEstudiante(Connection con, long idPersona, Long codigo, long programaId, int activo, Double promedio) throws SQLException {
        String sql = "INSERT INTO ESTUDIANTE(id, codigo, programa_id, activo, promedio) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, idPersona);
            if (codigo == null) ps.setNull(2, Types.BIGINT); else ps.setLong(2, codigo);
            ps.setLong(3, programaId);
            ps.setInt(4, activo);
            if (promedio == null) ps.setNull(5, Types.DOUBLE); else ps.setDouble(5, promedio);
            ps.executeUpdate();
        }
    }

    private static void insertInscripcion(Connection con, long cursoId, int anio, int semestre, long estudianteId) throws SQLException {
        String sql = "INSERT INTO INSCRIPCION(curso_id, anio, semestre, estudiante_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, cursoId);
            ps.setInt(2, anio);
            ps.setInt(3, semestre);
            ps.setLong(4, estudianteId);
            ps.executeUpdate();
        }
    }

    private static String shortSqlState(SQLException ex) {
        // útil para ver rápidamente el tipo de error: SQLState y código del vendor
        return "SQLState=" + ex.getSQLState() + ", code=" + ex.getErrorCode();
    }
}
