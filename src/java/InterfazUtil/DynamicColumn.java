package InterfazUtil;

import Curso.*;
import SalonCurso.SalonCurso;
import SalonCurso.SalonCursoController;
import SalonCurso.SalonCursoFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named("dynamicColumn")
@ViewScoped
public class DynamicColumn implements Serializable{
    
    @PostConstruct
    private void init() {
        getGenerateRows();
    }
    
    @Inject
    SalonCursoController salonCursoController;
    
    @Inject
    CursoController cursoController;
    
    List<HorariosGrid> rows = new ArrayList<>();
    
    public void getGenerateRows(){
        rows = new ArrayList<>();
        HoraMinuto HM= new HoraMinuto(9,00);
        HoraMinuto HMfin= new HoraMinuto(23,00);
        
        
        while(HM.compare(HMfin)==-1){
            HoraMinuto inicio=new HoraMinuto(HM.getHora(), HM.getMinuto());
            HM.addMinutos(30);
            HoraMinuto fin = new HoraMinuto(HM.getHora(), HM.getMinuto());
            HorariosGrid nuevo= new HorariosGrid();
            nuevo.inicio=inicio.combertir();
            nuevo.fin=fin.combertir();
            spanRow(inicio, fin, nuevo);
            rows.add(nuevo);
        }
    }
    
    void spanRow(HoraMinuto inicio, HoraMinuto fin, HorariosGrid nuevo){
        List<SalonCurso> horarios = cursoController.getSelected().getSalonesCurso();
        for(SalonCurso sc : horarios){
            HoraMinuto scInicio= new HoraMinuto();
            scInicio.transformarStringEnHoraMinuto(sc.getHoraInicio());
            HoraMinuto scfin= new HoraMinuto();
            scfin.transformarStringEnHoraMinuto(sc.getHoraFin());
            if(scInicio.compare(inicio)==0){
                if(scfin.compare(fin)==1){
                    setDiasHorario(nuevo, sc);
                    setSpanrow(nuevo, sc, inicio, fin, scInicio, scfin);
                }else{
                    setDiasHorario(nuevo, sc);
                }
            }
            if(scInicio.compare(inicio)==-1&&(scfin.compare(fin)==1||scfin.compare(fin)==0)){
                desabilitarCol(nuevo, sc);
            }//else abilitarCol(nuevo, sc);
        }
    }
    
    void setSpanrow(HorariosGrid nuevo, SalonCurso sc, HoraMinuto inicio, HoraMinuto fin, HoraMinuto scInicio, HoraMinuto scfin){
        if(scInicio.compare(inicio)==0&&scfin.compare(fin)==1){
            HoraMinuto helper = new HoraMinuto();
            helper.setHora(fin.getHora());
            helper.setMinuto(fin.getMinuto());
            int i=1;
            while(scfin.compare(helper)==1){
                i++;
                helper.addMinutos(30);
            }
            if(sc.getDiadelaSemana().equalsIgnoreCase("lunes")){
                nuevo.lunes.rowspan=String.valueOf(i);
            }
            if(sc.getDiadelaSemana().equalsIgnoreCase("martes")){
                nuevo.martes.rowspan=String.valueOf(i);
            }
            if(sc.getDiadelaSemana().equalsIgnoreCase("miercoles")){
                nuevo.miercoles.rowspan=String.valueOf(i);
            }
            if(sc.getDiadelaSemana().equalsIgnoreCase("jueves")){
                nuevo.jueves.rowspan=String.valueOf(i);
            }
            if(sc.getDiadelaSemana().equalsIgnoreCase("viernes")){
                nuevo.viernes.rowspan=String.valueOf(i);
            }
            if(sc.getDiadelaSemana().equalsIgnoreCase("sabado")){
                nuevo.sabado.rowspan=String.valueOf(i);
            }
        }
    }
    
    void setDiasHorario(HorariosGrid nuevo, SalonCurso sc){
        if(sc.getDiadelaSemana().equalsIgnoreCase("lunes")){
            nuevo.lunes.dato=sc.getSalon().getNombreNumero()+" "+sc.getIdSalonCurso();
        }
        if(sc.getDiadelaSemana().equalsIgnoreCase("martes")){
            if(sc.getSalon()!=null&&sc.getCurso()!=null){
                nuevo.martes.dato=sc.getSalon().getNombreNumero()+" "+sc.getCurso().getMateria().getNombre();
            }
        }
        if(sc.getDiadelaSemana().equalsIgnoreCase("miercoles")){
            if(sc.getSalon()!=null&&sc.getCurso()!=null){
                nuevo.miercoles.dato=sc.getSalon().getNombreNumero()+" "+sc.getCurso().getMateria().getNombre();
            }
        }
        if(sc.getDiadelaSemana().equalsIgnoreCase("jueves")){
            if(sc.getSalon()!=null&&sc.getCurso()!=null){
                nuevo.jueves.dato=sc.getSalon().getNombreNumero()+" "+sc.getCurso().getMateria().getNombre();
            }
        }
        if(sc.getDiadelaSemana().equalsIgnoreCase("viernes")){
            if(sc.getSalon()!=null&&sc.getCurso()!=null){
                nuevo.viernes.dato=sc.getSalon().getNombreNumero()+" "+sc.getCurso().getMateria().getNombre();
            }
        }
        if(sc.getDiadelaSemana().equalsIgnoreCase("sabado")){
            if(sc.getSalon()!=null&&sc.getCurso()!=null){
                nuevo.sabado.dato=sc.getSalon().getNombreNumero()+" "+sc.getCurso().getMateria().getNombre();
            }
        }
    }
    
    void desabilitarCol(HorariosGrid nuevo, SalonCurso sc){
        if(sc.getDiadelaSemana().equalsIgnoreCase("lunes")){
            nuevo.lunes.enable=false;
        }
        if(sc.getDiadelaSemana().equalsIgnoreCase("martes")){
            if(sc.getSalon()!=null&&sc.getCurso()!=null){
                nuevo.martes.enable=false;
            }
        }
        if(sc.getDiadelaSemana().equalsIgnoreCase("miercoles")){
            if(sc.getSalon()!=null&&sc.getCurso()!=null){
                nuevo.miercoles.enable=false;
            }
        }
        if(sc.getDiadelaSemana().equalsIgnoreCase("jueves")){
            if(sc.getSalon()!=null&&sc.getCurso()!=null){
                nuevo.jueves.enable=false;
            }
        }
        if(sc.getDiadelaSemana().equalsIgnoreCase("viernes")){
            if(sc.getSalon()!=null&&sc.getCurso()!=null){
                nuevo.viernes.enable=false;
            }
        }
        if(sc.getDiadelaSemana().equalsIgnoreCase("sabado")){
            if(sc.getSalon()!=null&&sc.getCurso()!=null){
                nuevo.sabado.enable=false;
            }
        }
    }
    
    public List<HorariosGrid> getRows() {
        return rows;
    }
    
    public void setRows(List<HorariosGrid> rows) {
        this.rows = rows;
    }
    
    @EJB
    SalonCursoFacade ejbSalonCurso;
    
//    
//    List<SalonCurso> getListaSalonCurso(){
////        List<SalonCurso> items = new ArrayList();
////        SalonCurso sc = new SalonCurso();
////        sc.setDiadelaSemana("jueves");
////        sc.setHoraInicio("12:30");
////        sc.setHoraFin("20:00");
////        sc.setCurso(ejbCurso.find(3));
////        sc.setSalon(ejbSalon.find(1));
////        items.add(sc);
//        
//        return 
//    }
    
}