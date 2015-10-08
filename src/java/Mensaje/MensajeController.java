package Mensaje;

import Session.LoginMB;
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

@Named("mensajeController")
@ViewScoped
public class MensajeController implements Serializable{
    
    @EJB
    private MensajeFacade ejbMensaje;
    
    private List<Mensaje> items = new ArrayList<>();
    private Mensaje selected = new Mensaje();
    
//    public void sendSelected(){
//        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("selected", selected);
//    selected = (Mensaje) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("selected");
//
    @PostConstruct
    public void init() {
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
    
    public void deleteSelected() {
        ejbMensaje.remove(selected);
        updateItems();
        selected = null;
    }
    
    public void delete(int id) {
        Mensaje u = ejbMensaje.find(id);
        ejbMensaje.remove(u);
        updateItems();
        selected = null;
    }
    
    public void createSelected(){
        selected.setFecha(new Date());
        ejbMensaje.create(selected);
        obtenerParameterOnItems();
        selected = new Mensaje();
    }
    
    private void updateItems(){
        items=ejbMensaje.findAll();
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
//        selected.setDesde(loginMB.getUserSession().getNick());
    }
    
    
    public void obtenerParameterOnItems(){
        
//        if(bandejaDeSalida()){
//            items=ejbMensaje.getMensajesEnviados(loginMB.getUserSession().getNick());
//        }else{
//            items=ejbMensaje.getMensajesRecividos(loginMB.getUserSession().getNick());
//        }
        items=ejbMensaje.findAll();
        
    }
    
    //Verifica si es bandeja de salida
    public boolean bandejaDeSalida(){
        Map<String, String> params =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String vista = params.get("vista");
        return (vista!=null && vista.equals("SALIDA"));
    }
    
    public void responder(){
        Mensaje nuevo=new Mensaje();
        nuevo.setDesde(selected.getPara());
        nuevo.setPara(selected.getDesde());
        selected=nuevo;
    }
    
//    @Inject
//    LoginMB loginMB;
}