/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Rol;

import Curso.Curso;
import RespuestaPregunta.RespuestaPregunta;
import ResultadoInstancia.ResultadoInstancia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Alumno extends TipoRol{
    
    @ManyToMany(mappedBy="alumnos", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Curso> cursos;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RespuestaPregunta> respuestasPreguntas= new ArrayList<>();

    public Alumno() {    }
    
    public List<Curso> getCursos() {
        return cursos;
    }
    
    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }
    
    public List<RespuestaPregunta> getRespuestasPreguntas() {
        return respuestasPreguntas;
    }
    
    public void setRespuestasPreguntas(List<RespuestaPregunta> respuestasPreguntas) {
        this.respuestasPreguntas = respuestasPreguntas;
    }
}
