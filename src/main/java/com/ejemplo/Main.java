package com.ejemplo;

import java.util.Arrays;
import java.util.List;

import java.util.Date; 
import com.ejemplo.Modelos.Cursos.CursoProfesor;
import com.ejemplo.Modelos.Personas.Persona;
import com.ejemplo.Modelos.Personas.Profesor;
import com.ejemplo.Modelos.Universidad.Curso;
import com.ejemplo.Modelos.Universidad.Facultad;
import com.ejemplo.Modelos.Universidad.Programa;
import com.ejemplo.Repositorios.DB;
import com.ejemplo.Repositorios.Cursos.CursosProfesores;

public class Main {

   public static List<CursoProfesor> datosIniciales(){
      // ====== Personas y facultades ======
      Persona decanoIng = new Persona( "Laura", "Mejía", "laura.mejia@uni.com");
      Persona decanoCiencias = new Persona( "Héctor", "Núñez", "hector.nunez@uni.com");

      Facultad facIng      = new Facultad(1d, "Ingeniería",         decanoIng);
      Facultad facCiencias = new Facultad(2d, "Ciencias Exactas",   decanoCiencias);

      // ====== Programas ======
      Programa progSis = new Programa(1d, "Ingeniería de Sistemas", 10d, new Date(), facIng);
      Programa progMat = new Programa(2d, "Matemáticas",             8d, new Date(), facCiencias);

      // ====== Cursos ======
      Curso cursoProg1 = new Curso(101, "Programación I",       progSis, true);
      Curso cursoED    = new Curso(102, "Estructuras de Datos", progSis, true);
      Curso cursoBD    = new Curso(103, "Bases de Datos",       progMat, true);

      // ====== Profesores (extienden Persona) ======
      Profesor profAna    = new Profesor( "Ana",    "García", "ana.garcia@uni.com",    "Tiempo completo");
      Profesor profCarlos = new Profesor( "Carlos", "López",  "carlos.lopez@uni.com",  "Cátedra");
      Profesor profMaria  = new Profesor( "María",  "Ruiz",   "maria.ruiz@uni.com",    "Medio tiempo");

      // ====== Asignaciones Curso-Profesor (anio, semestre) ======
      CursoProfesor cp1 = new CursoProfesor(profAna,    2025, 1, cursoProg1);
      CursoProfesor cp2 = new CursoProfesor(profCarlos, 2025, 1, cursoED);
      CursoProfesor cp3 = new CursoProfesor(profMaria,  2025, 2, cursoBD);

      // ====== Lista inicial y repositorio ======
      return Arrays.asList(cp1, cp2, cp3);
   }

   public static void main(String[] args) {
       
      DB.initSchema();
   
      CursosProfesores cursos = new CursosProfesores();

      cursos.cargarDatos();

      System.out.println("\n\n");
      System.out.println(cursos.toString());
      System.out.println("\n\n");

      cursos = new CursosProfesores();

      for(CursoProfesor cp : datosIniciales()){
         cursos.inscribir(cp);
         cursos.guardarInformacion(cp);
      }

      System.out.println("\n\n");
      System.out.println(cursos.toString());
      System.out.println("\n\n");

      cursos = new CursosProfesores(); // Carga desde BD

      cursos.cargarDatos();

      System.out.println("\n\n");
      System.out.println(cursos.toString());
      System.out.println("\n\n");

      System.out.println("Creado por Daniel y Dhanielt");

   }

}
