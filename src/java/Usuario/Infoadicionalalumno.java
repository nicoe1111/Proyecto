package Usuario;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Agus
 */
@Entity
public class Infoadicionalalumno implements Serializable  {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idinfoalumno;
    private Boolean carnetsalud;
    private Boolean certificadoestudio;
    private Date expedidocarnetsalud;
    private Boolean fdocente;
    private String fdocenteorientacion;
    private String facultaduniversidad;
    private Date fechainscripcion;
    private Boolean fotocarnet;
    private String generacion;
    private Boolean militar;
    private String militarorientacion;
    private int nromatricula;
    private Boolean secuandaria;
    private String secundariaorientacion;
    private String tramitecarnetsalud;
    private String turno;
    private Boolean universidad;
    private Boolean utu;
    private String utuorientacion;
    private Boolean vacunaantitetanica;
    private Date  vencimcarnetsalud;
    private Date  vencimvacunaantitetanica;
    
    @OneToOne(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST}, orphanRemoval = true)
    @JoinColumn(name="id_user")
    Usuario userAlumno;

    public Infoadicionalalumno() {    }

    public Usuario getUserAlumno() {
        return userAlumno;
    }

    public void setUserAlumno(Usuario userAlumno) {
        this.userAlumno = userAlumno;
    }
    
    public int getIdinfoalumno() {
        return idinfoalumno;
    }

    public void setIdinfoalumno(int idinfoalumno) {
        this.idinfoalumno = idinfoalumno;
    }

    public Boolean getCarnetsalud() {
        return carnetsalud;
    }

    public void setCarnetsalud(Boolean carnetsalud) {
        this.carnetsalud = carnetsalud;
    }

    public Boolean getCertificadoestudio() {
        return certificadoestudio;
    }

    public void setCertificadoestudio(Boolean certificadoestudio) {
        this.certificadoestudio = certificadoestudio;
    }

    public Date getExpedidocarnetsalud() {
        return expedidocarnetsalud;
    }

    public void setExpedidocarnetsalud(Date expedidocarnetsalud) {
        this.expedidocarnetsalud = expedidocarnetsalud;
    }

    public Boolean getFdocente() {
        return fdocente;
    }

    public void setFdocente(Boolean fdocente) {
        this.fdocente = fdocente;
    }

    public String getFdocenteorientacion() {
        return fdocenteorientacion;
    }

    public void setFdocenteorientacion(String fdocenteorientacion) {
        this.fdocenteorientacion = fdocenteorientacion;
    }

    public String getFacultaduniversidad() {
        return facultaduniversidad;
    }

    public void setFacultaduniversidad(String facultaduniversidad) {
        this.facultaduniversidad = facultaduniversidad;
    }

    public Date getFechainscripcion() {
        return fechainscripcion;
    }

    public void setFechainscripcion(Date fechainscripcion) {
        this.fechainscripcion = fechainscripcion;
    }

    public Boolean getFotocarnet() {
        return fotocarnet;
    }

    public void setFotocarnet(Boolean fotocarnet) {
        this.fotocarnet = fotocarnet;
    }

    public String getGeneracion() {
        return generacion;
    }

    public void setGeneracion(String generacion) {
        this.generacion = generacion;
    }

    public Boolean getMilitar() {
        return militar;
    }

    public void setMilitar(Boolean militar) {
        this.militar = militar;
    }

    public String getMilitarorientacion() {
        return militarorientacion;
    }

    public void setMilitarorientacion(String militarorientacion) {
        this.militarorientacion = militarorientacion;
    }

    public int getNromatricula() {
        return nromatricula;
    }

    public void setNromatricula(int nromatricula) {
        this.nromatricula = nromatricula;
    }

    public Boolean getSecuandaria() {
        return secuandaria;
    }

    public void setSecuandaria(Boolean secuandaria) {
        this.secuandaria = secuandaria;
    }

    public String getSecundariaorientacion() {
        return secundariaorientacion;
    }

    public void setSecundariaorientacion(String secundariaorientacion) {
        this.secundariaorientacion = secundariaorientacion;
    }

    public String getTramitecarnetsalud() {
        return tramitecarnetsalud;
    }

    public void setTramitecarnetsalud(String tramitecarnetsalud) {
        this.tramitecarnetsalud = tramitecarnetsalud;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Boolean getUniversidad() {
        return universidad;
    }

    public void setUniversidad(Boolean universidad) {
        this.universidad = universidad;
    }

    public Boolean getUtu() {
        return utu;
    }

    public void setUtu(Boolean utu) {
        this.utu = utu;
    }

    public String getUtuorientacion() {
        return utuorientacion;
    }

    public void setUtuorientacion(String utuorientacion) {
        this.utuorientacion = utuorientacion;
    }

    public Boolean getVacunaantitetanica() {
        return vacunaantitetanica;
    }

    public void setVacunaantitetanica(Boolean vacunaantitetanica) {
        this.vacunaantitetanica = vacunaantitetanica;
    }

    public Date getVencimcarnetsalud() {
        return vencimcarnetsalud;
    }

    public void setVencimcarnetsalud(Date vencimcarnetsalud) {
        this.vencimcarnetsalud = vencimcarnetsalud;
    }

    public Date getVencimvacunaantitetanica() {
        return vencimvacunaantitetanica;
    }

    public void setVencimvacunaantitetanica(Date vencimvacunaantitetanica) {
        this.vencimvacunaantitetanica = vencimvacunaantitetanica;
    }
    
}
