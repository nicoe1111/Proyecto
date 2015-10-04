package Mensaje;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named("mensajeController")
@SessionScoped
public class MensajeController implements Serializable{
    
    @EJB
    private MensajeFacade ejbMensaje;
    
    private List<Mensaje> items = null;
    private Mensaje selected = null;
    
    public List<Mensaje> getItems() {
        items = ejbMensaje.findAll();
        return items;
    }
    
    public void setItems(List<Mensaje> items) {
        this.items = items;
    }
    
    public Mensaje getSelected() {
        if(selected==null){
            selected = new Mensaje();
        }
        return selected;
    }
    
    public void setSelected(Mensaje selected) {
        this.selected = selected;
    }
    
    public void updateSelected(){
        ejbMensaje.edit(selected);
        updateItems();
        selected = null;
    }
    
    public void update(int id){
        Mensaje u = ejbMensaje.find(id);
        ejbMensaje.edit(u);
        updateItems();
        selected = null;
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
        ejbMensaje.create(selected);
        updateItems();
        selected = null;
    }
    
    private void updateItems(){
        items=ejbMensaje.findAll();
    }
}