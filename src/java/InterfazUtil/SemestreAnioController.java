/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package InterfazUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
//combobox controller
@Named("semestreAnioController")
@ViewScoped
public class SemestreAnioController implements Serializable{
    
    private List<String> semestres=Arrays.asList("Primer Semestre", "Segundo Semestre", "Tercer Semestre", "Cuarto Semestre", "Quinto Semestre", "Sexto Semestre");
    private String semestreSelected;
    private int anioSelected;
    private int actualYear = Calendar.getInstance().get(Calendar.YEAR);
    private List<String> years = loadyears();
    
    private List<String> loadyears(){
        List<String> years = new ArrayList();
        for(int i=(actualYear-5); i<=actualYear+5 ;i++){
            years.add(String.valueOf(i));
        }
        return years;
    }

    public int getActualYear() {
        return actualYear;
    }

    public void setActualYear(int actualYear) {
        this.actualYear = actualYear;
    }
    
    
    
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
    
    public List<String> getSemestres() {
        return semestres;
    }
    
    public void setSemestres(List<String> semestres) {
        this.semestres = semestres;
    }
    
    private List<String> DiasSemana=Arrays.asList("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado");
    private String DiaSemanaSelected;
    
    public List<String> getDiasSemana() {
        return DiasSemana;
    }
    
    public void setDiasSemana(List<String> DiasSemana) {
        this.DiasSemana = DiasSemana;
    }
    
    public String getDiaSemanaSelected() {
        return DiaSemanaSelected;
    }
    
    public void setDiaSemanaSelected(String DiaSemanaSelected) {
        this.DiaSemanaSelected = DiaSemanaSelected;
    }
    
    ////////////////////////////////////////////////////////////////////
    
    private List<String> inicios;
    private List<String> fines;
    
    private String inicioSelected;
    private String finSelected;
    
    @PostConstruct
    private void init(){
        loadIniciosFines();
        anioSelected=actualYear;
        semestreSelected="Primer Semestre";
    }
    
    private void loadIniciosFines(){
        inicios = new ArrayList<>();
        fines = new ArrayList<>();
        
        HoraMinuto HM= new HoraMinuto(9,00);
        HoraMinuto HMfin= new HoraMinuto(23,00);
        
        while(HM.compare(HMfin)==-1){
            HoraMinuto inicio=new HoraMinuto(HM.getHora(), HM.getMinuto());
            inicios.add(inicio.combertir());
            HM.addMinutos(30);
            HoraMinuto fin = new HoraMinuto(HM.getHora(), HM.getMinuto());
            fines.add(fin.combertir());
        }
    }

    public List<String> getInicios() {
        return inicios;
    }

    public void setInicios(List<String> inicios) {
        this.inicios = inicios;
    }

    public List<String> getFines() {
        return fines;
    }

    public void setFines(List<String> fines) {
        this.fines = fines;
    }

    public String getInicioSelected() {
        return inicioSelected;
    }

    public void setInicioSelected(String inicioSelected) {
        this.inicioSelected = inicioSelected;
    }

    public String getFinSelected() {
        return finSelected;
    }

    public void setFinSelected(String finSelected) {
        this.finSelected = finSelected;
    }
    
    private String tipoInstanciaSelected;
    private List<String> tipoInstancia = Arrays.asList("Examen", "Parcial", "Laboratorio");

    public List<String> getTipoInstancia() {
        return tipoInstancia;
    }

    public void setTipoInstancia(List<String> tipoInstancia) {
        this.tipoInstancia = tipoInstancia;
    }

    public String getTipoInstanciaSelected() {
        return tipoInstanciaSelected;
    }

    public void setTipoInstanciaSelected(String tipoInstanciaSelected) {
        this.tipoInstanciaSelected = tipoInstanciaSelected;
    }
}
