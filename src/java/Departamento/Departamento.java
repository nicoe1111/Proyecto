/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Departamento;

import Localidad.Localidad;
import Pais.Pais;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Agus
 */
@Entity
public class Departamento implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDepartamento;
    @Column(unique=true)
    private String nombre;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "idPais")
    private Pais pais = new Pais();
    
    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Localidad> localidad = new ArrayList<>();

    public Departamento() {    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List<Localidad> getLocalidad() {
        return localidad;
    }

    public void setLocalidad(List<Localidad> localidad) {
        this.localidad = localidad;
    }
    
}
