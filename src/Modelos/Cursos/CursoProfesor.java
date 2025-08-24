package Modelos.Cursos;

import java.sql.Connection;

import Modelos.Personas.Profesor;
import Modelos.Universidad.Curso;

public class CursoProfesor {

    private Profesor profesor;
    private int anio;
    private int semestre;
    private Curso curso;

    public CursoProfesor(){}

    public CursoProfesor(Profesor profesor, int anio, int semestre, Curso curso) {
        this.profesor = profesor;
        this.anio = anio;
        this.semestre = semestre;
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "CursoProfesor{" +
                "profesor=" + profesor +
                ", anio=" + anio +
                ", semestre=" + semestre +
                ", curso=" + curso +
                '}';
    }

    public void guardar(Connection conn){
        
        profesor.guardar(conn);
        curso.guardar(conn);

        String sql = "MERGE INTO CURSO_PROFESOR (profesor_id, anio, semestre, curso_id) KEY(profesor_id, anio, semestre, curso_id) VALUES (?, ?, ?, ?)";
        try (var ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ((int)this.profesor.getId()));
            ps.setInt(2, this.anio);
            ps.setInt(3, this.semestre);
            ps.setInt(4, (int)this.curso.getID());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("Error al guardar CURSO_PROFESOR: " + e.getMessage());
        }
    }

    public void cargar(Connection conn, int id){

        String sql = "SELECT * FROM CURSO_PROFESOR WHERE id = ?";
        try (var ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                this.profesor = new Profesor();
                this.profesor.cargar(conn, rs.getInt("profesor_id"));

                this.curso = new Curso();
                this.curso.cargar(conn, rs.getInt("curso_id"));

                this.anio = rs.getInt("anio");
                this.semestre = rs.getInt("semestre");
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error al cargar CURSO_PROFESOR: " + e.getMessage());
        }
    }
}
