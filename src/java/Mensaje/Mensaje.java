package Mensaje;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Mensaje implements Serializable {
    @Id
    private int id_mensaje;
    private String desde;
    private String para;
    private String mensaje;
    
    public Mensaje() {
    }

    public Mensaje(int id_mensaje, String desde, String para, String mensaje) {
        this.id_mensaje = id_mensaje;
        this.desde = desde;
        this.para = para;
        this.mensaje = mensaje;
    }

    public int getId_mensaje() {
        return id_mensaje;
    }

    public void setId_mensaje(int id_mensaje) {
        this.id_mensaje = id_mensaje;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
