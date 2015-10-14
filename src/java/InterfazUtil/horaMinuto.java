/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package InterfazUtil;

/**
 *
 * @author Diego
 */
public class horaMinuto {
    
    private int hora;
    private int minuto;
    
    public int getHora() {
        return hora;
    }
    
    public void setHora(int hora) {
        this.hora = hora;
    }
    
    public int getMinuto() {
        return minuto;
    }
    
    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }
    
    public horaMinuto(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }
    
    public horaMinuto addMinutos(int minutos){
        minuto = minuto + minutos;
        while(minuto>60){
            if(hora<23){
                hora+= 1;
                minuto-= 60;
            }else{
                hora=0;
                minuto-= 60;
            }
        }
        return this;
    }
    
}
