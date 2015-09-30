/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Materia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("materiaController")
@SessionScoped
public class MateriaController implements Serializable {

    @EJB
    private MateriaFacade ejbMateria;
    
    private List<Materia> items;
    private Materia selected;
    private List<String> semestres=Arrays.asList("Primer Semestre", "Segundo Semestre", "Tercer Semestre", "Cuarto Semestre", "Quinto Semestre", "Sexto Semestre");

    public List<String> getSemestres() {
        return semestres;
    }

    public void setSemestres(List<String> semestres) {
        this.semestres = semestres;
    }

    public List<Materia> getItems() {
        items = ejbMateria.findAll();
        return items;
    }

    public void setItems(List<Materia> items) {
        this.items = items;
    }

    public Materia getSelected() {
        if(selected==null){
            selected = new Materia();
        }
        return selected;
    }

    public void setSelected(Materia selected) {
        this.selected = selected;
    }
    
    public void updateSelected(){
        ejbMateria.edit(selected);
        updateItems();
    }
    
    public void update(int id){
        Materia u = ejbMateria.find(id);
        ejbMateria.edit(u);
        updateItems();
    }
    
    public void deleteSelected() {
        ejbMateria.remove(selected);
        updateItems();
        selected = null;
    }
    
    public void delete(int id) {
        Materia u = ejbMateria.find(id);
        ejbMateria.remove(u);
        updateItems();
        selected = null;
    }
    
    public void createSelected(){
        ejbMateria.create(selected);
        updateItems();
        selected = null;
    }
    
    private void updateItems(){
        items=ejbMateria.findAll();
    }
    
    public void loadSelected(int id){
        selected=ejbMateria.find(id);
    }
}
