/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Salon;

import Curso.Curso;
import SalonCurso.SalonCurso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Representa un salon, contiene N SalonMateria.
 * @author Matias
 */
@Entity
public class Salon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idSalon;
    private String nombreNumero;
    private String descripcion;
    
    @OneToMany(mappedBy = "salon", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SalonCurso> salonesCurso = new ArrayList<>();
    
    //++++++++++++++++++CONSTRUCTORES+++++++++++++++++++++++

    public Salon() {
    }

    public Salon(int idSalon, String nombreNumero, String descripcion) {
        this.idSalon = idSalon;
        this.nombreNumero = nombreNumero;
        this.descripcion = descripcion;
    }
    
    //++++++++++++++++++++SETTERS+++++++++++++++++++++++++++

    public void setIdSalon(int idSalon) {
        this.idSalon = idSalon;
    }

    public void setNombreNumero(String nombreNumero) {
        this.nombreNumero = nombreNumero;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setSalonesMateria(List<SalonCurso> salonesMateria) {
        this.salonesCurso = salonesMateria;
    }
    
    //++++++++++++++++++++GETTERS+++++++++++++++++++++++++++

    public int getIdSalon() {
        return idSalon;
    }

    public String getNombreNumero() {
        return nombreNumero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<SalonCurso> getSalonesMateria() {
        return salonesCurso;
    }
    
    
    
}
