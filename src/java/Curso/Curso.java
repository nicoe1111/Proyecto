/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Curso;

import ClaseDada.ClaseDada;
import Encuesta.Encuesta;
import InstanciaEvaluacion.InstanciaEvaluacion;
import Materia.Materia;
import Rol.Alumno;
import Rol.Docente;
import SalonCurso.SalonCurso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class Curso implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idCurso;
    private int anio;
    private Date FechaInicio;
    
    @ManyToOne(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST})
    @JoinColumn(name = "id_materia")
    private Materia materia;
        
    @ManyToOne(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST})
    @JoinColumn(name = "id_docente")
    private Docente docente;
    
    @ManyToMany(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST})
    private List<Alumno> alumnos = new ArrayList<>();
    
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ClaseDada> clasesDadas= new ArrayList<>();
    
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SalonCurso> salonesCurso= new ArrayList<>();
    
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<InstanciaEvaluacion> instanciasEvaluaciones= new ArrayList<>();
    
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Encuesta encuesta;

    public Curso() {    }

    public Curso(int idCurso, int anio, Date FechaInicio, Materia materia, Docente docente, Encuesta encuesta) {
        this.idCurso = idCurso;
        this.anio = anio;
        this.FechaInicio = FechaInicio;
        this.materia = materia;
        this.docente = docente;
        this.encuesta = encuesta;
    }

    

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Date getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(Date FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

//    public Docente getDocente() {
//        return docente;
//    }
//
//    public void setDocente(Docente docente) {
//        this.docente = docente;
//    }

//    public List<Alumno> getAlumnos() {
//        return alumnos;
//    }
//
//    public void setAlumnos(List<Alumno> alumnos) {
//        this.alumnos = alumnos;
//    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public List<ClaseDada> getClasesDadas() {
        return clasesDadas;
    }

    public void setClasesDadas(List<ClaseDada> clasesDadas) {
        this.clasesDadas = clasesDadas;
    }

    public List<SalonCurso> getSalonesCurso() {
        return salonesCurso;
    }

    public void setSalonesCurso(List<SalonCurso> salonesCurso) {
        this.salonesCurso = salonesCurso;
    }

    public List<InstanciaEvaluacion> getInstanciasEvaluaciones() {
        return instanciasEvaluaciones;
    }

    public void setInstanciasEvaluaciones(List<InstanciaEvaluacion> instanciasEvaluaciones) {
        this.instanciasEvaluaciones = instanciasEvaluaciones;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }
    
}
