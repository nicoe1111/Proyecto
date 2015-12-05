package Mensaje;

import Session.LoginMB;
import Usuario.Usuario;
import Usuario.UsuarioController2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named("mensajeController")
@ViewScoped
public class MensajeController implements Serializable{
    
    @EJB
    private MensajeFacade ejbMensaje;
    
    private List<Mensaje> items = new ArrayList<>();
    private Mensaje selected = new Mensaje();
    
    private Usuario UserLogged =null;

    @PostConstruct
    public void init() {
        UserLogged = getUserLoged();
        obtenerParameterOnItems();
    }
    
    public List<Mensaje> getItems() {
        return items;
    }
    
    public void setItems(List<Mensaje> items) {
        this.items = items;
    }
    
    public Mensaje getSelected() {
        if(selected==null){
            prepareToCreate();
        }
        return selected;
    }
    
    public void setSelected(Mensaje selected) {
        this.selected = selected;
    }
    
    public void loadSelected(Mensaje m){
        if(m.isReaded()==false){
            m.setReaded(true);
            ejbMensaje.edit(m);
        }
        selected = m;
        obtenerParameterOnItems();
    }
    
    public void createSelected(){
        selected.setFecha(new Date());
        ejbMensaje.create(selected);
        obtenerParameterOnItems();
        selected = new Mensaje();
    }
    
    public String rowColor(Mensaje msj){
        if(!bandejaDeSalida()){ //Si bandeja de entrada = true
            if(!msj.isReaded()){
                return "info";
            }
        }
        return "#";
    }
    
    public void prepareToCreate(){
        selected = new Mensaje();
        selected.setDesde(UserLogged.getNick());
    }
    
    
    public void obtenerParameterOnItems(){
        
        if(bandejaDeSalida()){
            items=ejbMensaje.getMensajesEnviados(UserLogged.getNick());
        }else{
            items=ejbMensaje.getMensajesRecividos(UserLogged.getNick());
        }
//        items=ejbMensaje.findAll();
        
    }
    
    //Verifica si es bandeja de salida
    public boolean bandejaDeSalida(){
        Map<String, String> params =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String vista = params.get("vista");
        return (vista!=null && vista.equals("SALIDA"));
    }
    
    public Usuario getUserLoged(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        LoginMB uc = (LoginMB) session.getAttribute("login");
        return uc.getUserLoged();
    }
    
    public void responder(){
        Mensaje nuevo=new Mensaje();
        nuevo.setDesde(selected.getPara());
        nuevo.setPara(selected.getDesde());
        selected=nuevo;
    }
    
    public int getMensajesSinLeer(){
        return ejbMensaje.getCauntMensajesRecividosSinLeer(UserLogged.getNick());
    }
}
