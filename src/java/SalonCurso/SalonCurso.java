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

@Entity
public class SalonCurso implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idSalonCurso;
    private String horaInicio;
    private String horaFin;
    private String DiadelaSemana;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "idCurso")
    private Curso curso;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "idSalon")
    private Salon salon;
    
//++++++++++++++++++CONSTRUCTORES+++++++++++++++++++++++  
       
    public SalonCurso() {
    }

    public SalonCurso(int idSalonMateria, String horaInicio, String horaFin, String DiadelaSemana) {
        this.idSalonCurso = idSalonMateria;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.DiadelaSemana = DiadelaSemana;
    }
    
    
  
//+++++++++++++++++++++SETTERS++++++++++++++++++++++++++

    public void setIdSalonCurso(int idSalonCurso) {
        this.idSalonCurso = idSalonCurso;
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

    public int getIdSalonCurso() {
        return idSalonCurso;
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
