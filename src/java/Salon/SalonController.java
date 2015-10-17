package Salon;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("salonController")
@ViewScoped
public class SalonController implements Serializable{
    
    @EJB
    private SalonFacade ejbSalon;
    
    private List<Salon> items = null;
    private Salon selected = null;
    
    @PostConstruct
    private void init(){
        updateItems();
    }
    
    public List<Salon> getItems() {
        return items;
    }
    
//    public List<String> obtenerNicks(String query){
//        List<Usuario> users = ejbSalon.findAll();
//        List<String> nicks=new ArrayList<>();
//        for(Usuario u:users){
//            if(u.getNick().toLowerCase().startsWith(query)){
//                nicks.add(u.getNick());
//            }
//        }
//        return nicks;
//    }
    
    public void setItems(List<Salon> items) {
        this.items = items;
    }
    
    public Salon getSelected() {
        if(selected==null){
            selected = new Salon();
        }
        return selected;
    }
    
    public void setSelected(Salon selected) {
        this.selected = selected;
    }
    
    public void updateSelected(){
        ejbSalon.edit(selected);
        updateItems();
        selected = null;
    }
    
    public void update(int id){
        Salon u = ejbSalon.find(id);
        ejbSalon.edit(u);
        updateItems();
        selected = null;
    }
    
    public void deleteSelected() {
        ejbSalon.remove(selected);
        updateItems();
        selected = null;
    }
    
    public void delete(int id) {
        Salon u = ejbSalon.find(id);
        ejbSalon.remove(u);
        updateItems();
        selected = null;
    }
    
    public void createSelected(){
        ejbSalon.create(selected);
        updateItems();
        selected = null;
    }
    
    private void updateItems(){
        items=ejbSalon.findAll();
    }
    
    public void loadSelected(int id){
        selected=ejbSalon.find(id);
    }
    
    private int SalonID;
    
    public int getSalonID() {
        return SalonID;
    }

    public void setSalonID(int SalonID) {
        setSelected(ejbSalon.find(SalonID));
        this.SalonID = SalonID;
    }
}