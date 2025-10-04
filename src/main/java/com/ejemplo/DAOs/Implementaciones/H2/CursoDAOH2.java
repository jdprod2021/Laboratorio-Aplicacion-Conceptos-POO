package com.ejemplo.DAOs.Implementaciones.H2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.CursoDAO;
import com.ejemplo.Modelos.Curso;
import com.ejemplo.Modelos.Programa;
import com.ejemplo.Utils.Erros.SqlErrorDetailer;

public class CursoDAOH2 implements CursoDAO{
    
    private final DataSource dataSource;
    private static CursoDAOH2 cursoDAOH2;

    private CursoDAOH2(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static CursoDAOH2 crearCursoDAOH2(DataSource dataSource){
        if(cursoDAOH2 == null){
            synchronized (CursoDAOH2.class){
                if(cursoDAOH2 == null){
                    cursoDAOH2 = new CursoDAOH2(dataSource);
                }
            }
        }
        return cursoDAOH2;
    }    

    @Override
    public Curso guardar(Curso entidad) {
        final String sql = "INSERT INTO CURSO (nombre, programa_id, activo) VALUES (?, ?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entidad.getNombre());
            ps.setLong(2, (long)(entidad.getPrograma() != null ? entidad.getPrograma().getID() : 0L));
            ps.setBoolean(3, entidad.isActivo());
            ps.executeUpdate();

             try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Asignar el id generado a la entidad
                    entidad.setId((int)generatedKeys.getLong(1)); // Asumiendo que el id es un Long
                }
            }

            return entidad;
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "INSERT CURSO", sql,
                entidad.getNombre(),
                (entidad.getPrograma() == null ? null : entidad.getPrograma().getID()),
                entidad.isActivo());
        }
        
    }

    @Override
    public List<Curso> listar(){
        final String sql = "SELECT id, nombre, programa_id, activo FROM CURSO ORDER BY nombre";
        List<Curso> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar CURSOS", e);
        }
        return lista;
    }

    @Override
    public void actualizar(Curso entidad) {
        final String sql = "UPDATE CURSO SET nombre = ?, programa_id = ?, activo = ? WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, entidad.getNombre());
            ps.setLong(2, (long)(entidad.getPrograma() != null ? entidad.getPrograma().getID() : 0L));
            ps.setBoolean(3, entidad.isActivo());
            ps.setLong(4, (long)entidad.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "UPDATE CURSO", sql,
                entidad.getNombre(),
                (entidad.getPrograma() == null ? null : entidad.getPrograma().getID()),
                entidad.isActivo(),
                entidad.getId());
        }
    }

    @Override
    public void eliminar(Long id) {
        final String sql = "DELETE FROM CURSO WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "DELETE CURSO", sql, id);
        }
    }

    private Curso mapRow(ResultSet rs) throws SQLException {
        Curso c = new Curso();
        c.setId((int)rs.getLong("id"));
        c.setNombre(rs.getString("nombre"));
        c.setActivo(rs.getBoolean("activo"));

        long pid = rs.getLong("programa_id");
        if (!rs.wasNull()) {
           
            Programa p = new Programa();
            p.setID(pid);
            c.setPrograma(p);
        }
        return c;
    }

}
