package Modelos.Universidad;

import Modelos.Personas.Estudiante;
import java.sql.Connection;
import java.sql.SQLException;

public class Inscripcion {
    private Curso curso;
    private int anio;
    private int semestre;
    private Estudiante estudiante;

    public Inscripcion(){}
    
    public Inscripcion(Curso curso, int anio, int semestre, Estudiante estudiante){
        this.curso = curso;
        this.anio = anio;
        this.semestre = semestre;
        this.estudiante = estudiante;
    }

    public void guardar(Connection conn){
        curso.guardar(conn);
        estudiante.guardar(conn);

        String sql = "MERGE INTO INSCRIPCION (curso_id, anio, semestre, estudiante_id) KEY (curso_id, anio, semestre, estudiante_id) VALUES (?, ?, ?, ?)";
        try (var ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ((int)this.curso.getID()));
            ps.setInt(2, this.anio);
            ps.setInt(3, this.semestre);
            ps.setInt(4, (int)this.estudiante.getId());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("Error al guardar : INSCRIPCION" + e.getMessage());
        }
    }

    public void cargar(Connection conn, int id){

        String sql = "SELECT * FROM INSCRIPCION WHERE id = ?";
        try (var ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                this.curso = new Curso();
                this.curso.cargar(conn, rs.getInt("curso_id"));

                this.estudiante = new Estudiante();
                this.estudiante.cargar(conn, rs.getInt("estudiante_id"));

                this.anio = rs.getInt("anio");
                this.semestre = rs.getInt("semestre");
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar INSCRIPCION: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Año: " + this.anio + ", Semestre: " + this.semestre + ", Curso: " + this.curso.toString() + ", Estudiante: " + this.estudiante.toString();
    }
}
