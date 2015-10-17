package SalonCurso;

import Curso.CursoController;
import Curso.CursoFacade;
import InterfazUtil.SemestreAnioController;
import Salon.SalonController;
import Salon.SalonFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named("salonCursoController")
@ViewScoped
public class SalonCursoController implements Serializable{
    
    /////////////////* fachadas *//////////////////////
    @EJB
    private SalonFacade ejbSalon;
    
    @EJB
    private SalonCursoFacade ejbSalonCurso;
    
    @EJB
    private CursoFacade ejbCurso;
    /////////////////////////////////////////////////
    
    ////controllers///////////////////////////////////////
    @Inject
    private SalonController salonController;
    
    @Inject
    private CursoController cursoController;
    
    @Inject
    private SemestreAnioController semestreAnioController;
    ////////////////////////////////////////////////////////
    
    private List<SalonCurso> items;
    private SalonCurso selected = null;
    
    @PostConstruct
    private void init(){
        //ejbCurso.getCursosSemestreAnio("Primer Semestre", 2013);
        updateItems();
    }
    
    public List<SalonCurso> getItems() {
        return items;
    }
    
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
        beforCreate();
        ejbSalonCurso.edit(selected);
        updateItems();
        selected = null;
    }
    
    public void update(int id){
        SalonCurso u = ejbSalonCurso.find(id);
        ejbSalonCurso.edit(u);
        updateItems();
        selected = null;
    }
    
    public void deleteSelected() {
        beforeDelete();
        ejbSalonCurso.remove(selected);
        updateItems();
        selected = null;
    }
    
    public void delete(int id) {
        SalonCurso u = ejbSalonCurso.find(id);
        ejbSalonCurso.remove(u);
        updateItems();
        selected = null;
    }
    
    public void createSelected(){
        beforCreate();
        ejbSalonCurso.create(selected);
        updateItems();
        selected = null;
    }
    
    private void updateItems(){
        items=ejbSalonCurso.findAll();
    }
    
    public void loadSelected(int id){
        selected=ejbSalonCurso.find(id);
    }
    ///////////////////////////  Control de relaciones  //////////////////////////////////////////////////
    
    private void beforeDelete(){
        selected.setCurso(null);
        selected.setSalon(null);
        ejbSalonCurso.edit(selected);
    }
    
    public void beforCreate(){
        selected.setDiadelaSemana(semestreAnioController.getDiaSemanaSelected());
        selected.setHoraInicio(semestreAnioController.getInicioSelected());
        selected.setHoraFin(semestreAnioController.getFinSelected());
        selected.setCurso(cursoController.getSelected());
        selected.setSalon(salonController.getSelected());
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////////////
    
    public void vaciarControllersSelecteds(){
        setSelected(null);
        salonController.setSelected(null);
        cursoController.setSelected(null);
        semestreAnioController.setDiaSemanaSelected(null);
        semestreAnioController.setInicioSelected(null);
        semestreAnioController.setFinSelected(null);
    }
    
    public void cargarControllersSelecteds(){
        salonController.setSalonID(selected.getSalon().getIdSalon());
        cursoController.setSelected(selected.getCurso());
        semestreAnioController.setDiaSemanaSelected(selected.getDiadelaSemana());
        semestreAnioController.setInicioSelected(selected.getHoraInicio());
        semestreAnioController.setFinSelected(selected.getHoraFin());
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////
    
    public void obtenerCursos(AjaxBehaviorEvent event){
        String semestreSelected=semestreAnioController.getSemestreSelected();
        int anioSelected = semestreAnioController.getAnioSelected();
        
        if(semestreSelected!=null && anioSelected!=0){
            cursoController.setItems(ejbCurso.getCursosSemestreAnio(semestreSelected, anioSelected));
        }
        if(semestreSelected!=null && anioSelected==0){
            cursoController.setItems(ejbCurso.getCursosSemestre(semestreSelected));
        }
        if((semestreSelected==null || semestreSelected.equals("")) && anioSelected!=0){
            cursoController.setItems(ejbCurso.getCursosAnio(anioSelected));
        }
        if(semestreSelected==null && anioSelected==0){
            cursoController.setItems(ejbCurso.findAll());
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////
}