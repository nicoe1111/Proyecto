package SalonCurso;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("salonCursoController")
@ViewScoped
public class SalonCursoController implements Serializable{
    
    @EJB
    private SalonCursoFacade ejbSalon;
    
    private List<SalonCurso> items = null;
    private SalonCurso selected = null;
    
    @PostConstruct
    private void init(){
        updateItems();
    }
    
    public List<SalonCurso> getItems() {
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
    
    public void setItems(List<SalonCurso> items) {
        this.items = items;
    }
    
    public SalonCurso getSelected() {
        if(selected==null){
            selected = new SalonCurso();
        }
        return selected;
    }
    
    public void setSelected(SalonCurso selected) {
        this.selected = selected;
    }
    
    public void updateSelected(){
        ejbSalon.edit(selected);
        updateItems();
        selected = null;
    }
    
    public void update(int id){
        SalonCurso u = ejbSalon.find(id);
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
        SalonCurso u = ejbSalon.find(id);
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
    
}