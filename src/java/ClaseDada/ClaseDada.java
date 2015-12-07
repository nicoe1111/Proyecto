/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClaseDada;


import Asistencia.Asistencia;
import Curso.Curso;
import Materia.Materia;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ClaseDada implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idClaseDada;
    private Date fecha;
    private String temaDado;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_curso")
    private Curso curso;
    
    @OneToMany(mappedBy = "claseDada", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Asistencia> asistencias= new ArrayList<>();

    
//++++++++++++++CONSTRUCTORES++++++++++++++++
    public ClaseDada() {
    }

    public ClaseDada(int idClaseDada, Date fecha, String temaDado, Curso curso) {
        this.idClaseDada = idClaseDada;
        this.fecha = fecha;
        this.temaDado = temaDado;
        this.curso = curso;
    }

    
//+++++++++++++++SETTERS+++++++++++++++++++++
    public void setIdClaseDada(int idClaseDada) {
        this.idClaseDada = idClaseDada;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setTemaDado(String temaDado) {
        this.temaDado = temaDado;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }
    
 //+++++++++++++GETTERS++++++++++++++++++++++

    public int getIdClaseDada() {
        return idClaseDada;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getTemaDado() {
        return temaDado;
    }

    public Curso getCurso() {
        return curso;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }
    
}
