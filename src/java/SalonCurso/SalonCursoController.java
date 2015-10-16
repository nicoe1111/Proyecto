package SalonCurso;

import Curso.Curso;
import Curso.CursoController;
import Curso.CursoFacade;
import Materia.Materia;
import Materia.MateriaFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
    
    @EJB
    private SalonCursoFacade ejbSalon;
    
    @EJB
    private CursoFacade ejbCurso;
    
    @EJB
    private MateriaFacade ejbMateria;
    
    private List<SalonCurso> items = null;
    private SalonCurso selected = null;
    
    @PostConstruct
    private void init(){
        ejbCurso.getCursosSemestreAnio(null, 2013);
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
    
    //////////////
    
    @Inject
            CursoController cursoController;
    
    private String semestreSelected;
    private int anioSelected;
    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private List<String> years = loadyears();
    private int materiaSelectedID;
    
    private List<String> loadyears(){
        List<String> years = new ArrayList();
        for(int i=(year-5); i<=year+5 ;i++){
            years.add(String.valueOf(i));
        }
        return years;
    }
    
    public void obtenerCursos(AjaxBehaviorEvent event){
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
    
//    public void getMateriasSemestre(){
//        List<Curso> cursos= new ArrayList<>();
//        for(Curso c:ejbCurso.findAll()){
//            if(semestreSelected!=null && anioSelected!=null){
//                if(c.getMateria().getSemestre().equalsIgnoreCase(semestreSelected)&&c.getAnio()==Integer.valueOf(anioSelected)){
//                    cursos.add(c);
//                }
//            }
//            if(semestreSelected!=null && anioSelected==null){
//                if(c.getMateria().getSemestre().equalsIgnoreCase(semestreSelected)){
//                    cursos.add(c);
//                }
//            }
//            if(semestreSelected==null && anioSelected!=null){
//                if(c.getAnio()==Integer.valueOf(anioSelected)){
//                    cursos.add(c);
//                }
//            }
//            if(semestreSelected==null && anioSelected==null){
//                cursos.add(c);
//            }
//        }
//        cursoController.setItems(cursos);
//    }
    
    public String getSemestreSelected() {
        return semestreSelected;
    }
    
    public void setSemestreSelected(String semestreSelected) {
        this.semestreSelected = semestreSelected;
    }
    
    public int getAnioSelected() {
        return anioSelected;
    }
    
    public void setAnioSelected(int anioSelected) {
        this.anioSelected = anioSelected;
    }
    
    public List<String> getYears() {
        return years;
    }
    
    public void setYears(List<String> years) {
        this.years = years;
    }
    
    public int getMateriaSelectedID() {
        return materiaSelectedID;
    }
    
    public void setMateriaSelectedID(int materiaSelectedID) {
        this.materiaSelectedID = materiaSelectedID;
    }
    //////////////
}