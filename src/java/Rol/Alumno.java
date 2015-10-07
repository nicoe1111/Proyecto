/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rol;

import Asistencia.Asistencia;
import Curso.Curso;
import RespuestaPregunta.RespuestaPregunta;
import ResultadoInstancia.ResultadoInstancia;
import Rol.TipoRol;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Alumno extends TipoRol{
    
    @ManyToMany(mappedBy="alumnos")
    private List<Curso> cursos = new ArrayList<>();
    
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Asistencia> asistencias= new ArrayList<>();
    
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RespuestaPregunta> respuestasPreguntas= new ArrayList<>();
    
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ResultadoInstancia> resultadosInstancias= new ArrayList<>();

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }

    public List<RespuestaPregunta> getRespuestasPreguntas() {
        return respuestasPreguntas;
    }

    public void setRespuestasPreguntas(List<RespuestaPregunta> respuestasPreguntas) {
        this.respuestasPreguntas = respuestasPreguntas;
    }

    public List<ResultadoInstancia> getResultadosInstancias() {
        return resultadosInstancias;
    }

    public void setResultadosInstancias(List<ResultadoInstancia> resultadosInstancias) {
        this.resultadosInstancias = resultadosInstancias;
    }
}
