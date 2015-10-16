/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SalonCurso;

import Curso.Curso;
import Salon.Salon;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Representa la asignacion de un Salon a un Curso.
 * @author Matias
 */
@Entity
public class SalonCurso implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idSalonMateria;
    private String horaInicio;
    private String horaFin;
    private String DiadelaSemana;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_Curso")
    private Curso curso;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_Salon")
    private Salon salon;
    
//++++++++++++++++++CONSTRUCTORES+++++++++++++++++++++++  
       
    public SalonCurso() {
    }

    public SalonCurso(int idSalonMateria, String horaInicio, String horaFin, String DiadelaSemana) {
        this.idSalonMateria = idSalonMateria;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.DiadelaSemana = DiadelaSemana;
    }
    
    
  
//+++++++++++++++++++++SETTERS++++++++++++++++++++++++++

    public void setIdSalonMateria(int idSalonMateria) {
        this.idSalonMateria = idSalonMateria;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public void setDiadelaSemana(String DiadelaSemana) {
        this.DiadelaSemana = DiadelaSemana;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

//+++++++++++++++++++++GETTERS++++++++++++++++++++++++++

    public int getIdSalonMateria() {
        return idSalonMateria;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public String getDiadelaSemana() {
        return DiadelaSemana;
    }

    public Curso getCurso() {
        return curso;
    }

    public Salon getSalon() {
        return salon;
    }
    

 
    
}
