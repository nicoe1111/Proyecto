package SalonCurso;

import Curso.Curso;
import Curso.CursoController;
import Curso.CursoFacade;
import InterfazUtil.HoraMinuto;
import InterfazUtil.SemestreAnioController;
import Pais.util.JsfUtil;
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
        updateItems();
//        obtenerCursos();
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
        if(controlHorarios()){
            ejbSalonCurso.edit(selected);
            updateItems();
        }else{
            vaciarControllersSelecteds();
            JsfUtil.addErrorMessage("Los horarios coinciden con anteriores");
        }
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
        if(controlHorarios()){
            ejbSalonCurso.create(selected);
            updateItems();
        }else{
            vaciarControllersSelecteds();
            JsfUtil.addErrorMessage("Los horarios coinciden con anteriores");
        }
        selected = null;
    }
    
    private void updateItems(){
        items=ejbSalonCurso.findAll();
    }
    
    public void loadSelected(int id){
        selected=ejbSalonCurso.find(id);
    }
    ///////////////////////////  Control de relaciones  //////////////////////////////////////////////////
    
    private boolean controlHorarios(){
        List<SalonCurso> horariosAnteriores = ejbSalonCurso.getSalonCursoAñoSemestre(selected.getDiadelaSemana(), selected.getCurso().getAnio(), selected.getCurso().getMateria().getSemestre());
        boolean result= true;
        for(SalonCurso sc : horariosAnteriores){
            if(!verificarCoincidenciaHorarios(selected, sc)){
                result=false;
            }
        }
        return result;
    }
    
    private boolean verificarCoincidenciaHorarios(SalonCurso nuevo, SalonCurso viejo){
        HoraMinuto nuevoInicio = new HoraMinuto();
        nuevoInicio.transformarStringEnHoraMinuto(nuevo.getHoraInicio());
        HoraMinuto nuevoFin = new HoraMinuto();
        nuevoFin.transformarStringEnHoraMinuto(nuevo.getHoraFin());
        HoraMinuto viejoInicio = new HoraMinuto();
        viejoInicio.transformarStringEnHoraMinuto(viejo.getHoraInicio());
        HoraMinuto viejoFin = new HoraMinuto();
        viejoFin.transformarStringEnHoraMinuto(viejo.getHoraFin());
        if(((nuevoInicio.compare(viejoInicio)==1 || nuevoInicio.compare(viejoInicio)==0) && nuevoInicio.compare(viejoFin)==-1)  ||
                nuevoFin.compare(viejoInicio)==1 && (nuevoFin.compare(viejoFin)==-1 || nuevoFin.compare(viejoFin)==0) ){
            return false;
        }else return true;
    }
    
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
        
//        if(semestreSelected!=null && anioSelected!=0){
          cursoController.setItems(ejbCurso.getCursosSemestreAnio(semestreSelected, anioSelected));
//        }
//        if(semestreSelected!=null && anioSelected==0){
////            cursoController.setItems(ejbCurso.getCursosSemestre(semestreSelected));
//        }
//        if((semestreSelected==null || semestreSelected.equals("")) && anioSelected!=0){
////            cursoController.setItems(ejbCurso.getCursosAnio(anioSelected));
//        }
//        if(semestreSelected==null && anioSelected==0){
////            cursoController.setItems(ejbCurso.findAll());
//        }
    }
    
    public void obtenerCursos(){
        String semestreSelected=semestreAnioController.getSemestreSelected();
        int anioSelected = semestreAnioController.getAnioSelected();
        cursoController.setItems(ejbCurso.getCursosSemestreAnio(semestreSelected, anioSelected));
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
}