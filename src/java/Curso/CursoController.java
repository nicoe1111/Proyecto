/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Curso;

import Materia.MateriaController;
import Usuario.UsuarioController2;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("cursoController")
@SessionScoped
public class CursoController implements Serializable {
    
    @EJB
    private CursoFacade ejbCurso;
    
    private List<Curso> items;
    private Curso selected;
    
    public List<Curso> getItems() {
        items = ejbCurso.findAll();
        return items;
    }
    
    public void setItems(List<Curso> items) {
        this.items = items;
    }
    
    public Curso getSelected() {
        if(selected==null){
            selected = new Curso();
        }
        return selected;
    }
    
    public void setSelected(Curso selected) {
        this.selected = selected;
    }
    
    public void updateSelected(){
        ejbCurso.edit(selected);
        updateItems();
    }
    
    public void update(int id){
        Curso u = ejbCurso.find(id);
        ejbCurso.edit(u);
        updateItems();
    }
    
    public void deleteSelected() {
        ejbCurso.remove(selected);
        updateItems();
        selected = null;
    }
    
    public void delete(int id) {
        Curso u = ejbCurso.find(id);
        ejbCurso.remove(u);
        updateItems();
        selected = null;
    }
    
    public void createSelected(){
        beforCreate();
        ejbCurso.create(selected);
        updateItems();
        selected = null;
        usuarioController2.setSelected(null);
        materiaController.setSelected(null);
    }
    
    private void updateItems(){
        items=ejbCurso.findAll();
    }
    
    public void loadSelected(int id){
        selected=ejbCurso.find(id);
    }
    
    @Inject
    private UsuarioController2 usuarioController2;
    
    @Inject
    private MateriaController materiaController;
    
    public void beforCreate(){
        selected.setDocente(usuarioController2.getSelected().getRolDocente());
        selected.setMateria(materiaController.getSelected());
    }
    
    /////filtrossssss/////////
    
     private List<Curso> filteredCursos;
    
    public List<Curso> getFilteredCursos() {
        return filteredCursos;
    }
    
    public void setFilteredCursos(List<Curso> filteredCursos) {
        this.filteredCursos = filteredCursos;
    }
    
     //////////////////////////
    
    public void vaciarControllersSelecteds(){
        setSelected(null);
        usuarioController2.setSelected(null);
        usuarioController2.setIdDocenteSelected(0);
        materiaController.setSelected(null);
    }
    
    public void cargarControllersSelecteds(){
        usuarioController2.setIdDocenteSelected(selected.getDocente().getIdRol());
        materiaController.setSelected(selected.getMateria());
    }
     
}
