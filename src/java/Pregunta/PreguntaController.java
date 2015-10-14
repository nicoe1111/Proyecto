package Pregunta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named("preguntaController")
@SessionScoped
public class PreguntaController implements Serializable{
    
    @EJB
    private PreguntaFacade ejbPregunta;
    @EJB
    private Rol.RolFacade ejbRol;
    private List<Pregunta> items = null;
    private Pregunta selected = null;
    
    public List<Pregunta> getItems() {
        items = ejbPregunta.findAll();
        return items;
    }
    
    public List<String> obtenerNicks(String query){
        List<Pregunta> users = ejbPregunta.findAll();
        List<String> preguntas=new ArrayList<>();
        for(Pregunta p:users){
            if(p.getPregunta().toLowerCase().startsWith(query)){
                preguntas.add(p.getPregunta());
            }
        }
        return preguntas;
    }
    
    public void setItems(List<Pregunta> items) {
        this.items = items;
    }
    
    public Pregunta getSelected() {
        if(selected==null){
            selected = new Pregunta();
        }
        return selected;
    }
    
    public void setSelected(Pregunta selected) {
        this.selected = selected;
    }
    
    public void updateSelected(){
        ejbPregunta.edit(selected);
        updateItems();
        selected = null;
    }
    
    public void update(int id){
        Pregunta p = ejbPregunta.find(id);
        ejbPregunta.edit(p);
        updateItems();
        selected = null;
    }
    
    public void deleteSelected() {
        ejbPregunta.remove(selected);
        updateItems();
        selected = null;
    }
    
    public void delete(int id) {
        Pregunta p = ejbPregunta.find(id);
        ejbPregunta.remove(p);
        updateItems();
        selected = null;
    }
    
    public void createSelected(){
        ejbPregunta.create(selected);
        updateItems();
        selected = null;
    }
    
    private void updateItems(){
        items = ejbPregunta.findAll();
    }
    
    /////filtrossssss/////////
    
    private List<Pregunta> filteredPregunta;
    
    public List<Pregunta> getFilteredPreguntas() {
        return filteredPregunta;
    }
    
    public void setFilteredPreguntas(List<Pregunta> filteredPregunta) {
        this.filteredPregunta = filteredPregunta;
    }
    
    //////////////////////////
    
    public void loadSelected(int id){
        selected=ejbPregunta.find(id);
    }
    

}