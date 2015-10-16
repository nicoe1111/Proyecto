package InterfazUtil;

import Curso.*;
import Salon.SalonFacade;
import SalonCurso.SalonCurso;
import SalonCurso.SalonCursoController;
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
    
    @EJB
    private SalonFacade ejbSalon;
    
    @EJB
    CursoFacade ejbCurso;
    
    @PostConstruct
    private void init() {
        getGenerateRows();
    }
    
    @Inject
    SalonCursoController salonCursoController;
    
    List<horariosGrid> rows = new ArrayList<>();
    
    public void getGenerateRows(){
        horaMinuto HM= new horaMinuto(9,00);
        horaMinuto HMfin= new horaMinuto(23,00);
        
        
        while(HM.compare(HMfin)==-1){
            horaMinuto inicio=new horaMinuto(HM.getHora(), HM.getMinuto());
            HM.addMinutos(30);
            horaMinuto fin = new horaMinuto(HM.getHora(), HM.getMinuto());
            horariosGrid nuevo= new horariosGrid();
            nuevo.inicio=inicio.combertir();
            nuevo.fin=fin.combertir();
            spanRow(inicio, fin, nuevo);
            rows.add(nuevo);
        }
    }
    
    void spanRow(horaMinuto inicio, horaMinuto fin, horariosGrid nuevo){
        List<SalonCurso> horarios = getPruebaSalonCurso();
        for(SalonCurso sc : horarios){
            horaMinuto scInicio= new horaMinuto();
            scInicio.transformarStringEnHoraMinuto(sc.getHoraInicio());
            horaMinuto scfin= new horaMinuto();
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
            }else abilitarCol(nuevo, sc);
        }
    }
    
    void setSpanrow(horariosGrid nuevo, SalonCurso sc, horaMinuto inicio, horaMinuto fin, horaMinuto scInicio, horaMinuto scfin){
        if(scInicio.compare(inicio)==0&&scfin.compare(fin)==1){
            horaMinuto helper = new horaMinuto();
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
    
    void setDiasHorario(horariosGrid nuevo, SalonCurso sc){
        if(sc.getDiadelaSemana().equalsIgnoreCase("lunes")){
            nuevo.lunes.dato=sc.getSalon().getNombreNumero()+" "+sc.getIdSalonMateria();
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
    
    void desabilitarCol(horariosGrid nuevo, SalonCurso sc){
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
    
    void abilitarCol(horariosGrid nuevo, SalonCurso sc){
        if(sc.getDiadelaSemana().equalsIgnoreCase("lunes")){
            nuevo.lunes.enable=true;
        }
        if(sc.getDiadelaSemana().equalsIgnoreCase("martes")){
            if(sc.getSalon()!=null&&sc.getCurso()!=null){
                nuevo.martes.enable=true;
            }
        }
        if(sc.getDiadelaSemana().equalsIgnoreCase("miercoles")){
            if(sc.getSalon()!=null&&sc.getCurso()!=null){
                nuevo.miercoles.enable=true;
            }
        }
        if(sc.getDiadelaSemana().equalsIgnoreCase("jueves")){
            if(sc.getSalon()!=null&&sc.getCurso()!=null){
                nuevo.jueves.enable=true;
            }
        }
        if(sc.getDiadelaSemana().equalsIgnoreCase("viernes")){
            if(sc.getSalon()!=null&&sc.getCurso()!=null){
                nuevo.viernes.enable=true;
            }
        }
        if(sc.getDiadelaSemana().equalsIgnoreCase("sabado")){
            if(sc.getSalon()!=null&&sc.getCurso()!=null){
                nuevo.sabado.enable=true;
            }
        }
    }
    
    public List<horariosGrid> getRows() {
        return rows;
    }
    
    public void setRows(List<horariosGrid> rows) {
        this.rows = rows;
    }
    
    
    
    List<SalonCurso> getPruebaSalonCurso(){
        List<SalonCurso> items = new ArrayList();
        SalonCurso sc = new SalonCurso();
        sc.setDiadelaSemana("jueves");
        sc.setHoraInicio("12:30");
        sc.setHoraFin("20:00");
        sc.setCurso(ejbCurso.find(5));
        sc.setSalon(ejbSalon.find(6));
        items.add(sc);
        return items;
    }
    
}