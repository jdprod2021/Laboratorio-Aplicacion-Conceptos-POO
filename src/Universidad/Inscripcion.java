package Universidad;

public class Inscripcion {
    //ASOCIACION CURSO
    private int año;
    private int semestre;
    //ASOCIACION ESTUDIANTE

    public Inscripcion(){
    }
    public Inscripcion(int año, int semestre){
        this.año = año;
        this.semestre = semestre;
    }

    public String toString() {
        return this.año + " " + this.semestre;
    }
}
