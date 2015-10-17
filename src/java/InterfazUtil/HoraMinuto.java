package InterfazUtil;

public class HoraMinuto {
    
    private int hora;
    private int minuto;

    public HoraMinuto() {
    }
    
    
    
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
    
    public HoraMinuto(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }
    
    public HoraMinuto addMinutos(int minutos){
        minuto = minuto + minutos;
        while(minuto>=60){
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
    
    public int compare(HoraMinuto b){
        if(this.esIgualQue(b)){
            return 0;
        }else if(this.esMayorQue(b)){
            return 1;
        }else if(this.esMenorQue(b)){
            return -1;
        }
        return -2;
    }
    
    private boolean esMayorQue(HoraMinuto b){
        if(this.hora>b.hora){
            return true;
        }else{
            if(this.hora==b.hora){
                if(this.minuto>b.minuto){
                    return true;
                }else return false;
            }else return false;
        }
    }
    
    private boolean esMenorQue(HoraMinuto b){
        if(this.hora<b.hora){
            return true;
        }else{
            if(this.hora==b.hora){
                if(this.minuto<b.minuto){
                    return true;
                }else return false;
            }else return false;
        }
    }
    
    private boolean esIgualQue(HoraMinuto b){
        if(this.hora==b.hora&&this.minuto==b.minuto){
            return true;
        }else return false;
    }
    
    public String combertir(){
        String horastr;
        if(hora<10){
            horastr="0"+String.valueOf(hora);
        }else horastr=String.valueOf(hora);
        String minutostr;
        if(minuto<10){
            minutostr="0"+String.valueOf(minuto);
        }else minutostr=String.valueOf(minuto);
        return horastr+":"+minutostr;
    }
    
    public void transformarStringEnHoraMinuto(String obj){
        String[] valores = obj.split(":");
        int hora =0;
        int minuto =0;
        if(valores[0]!=null){
            hora=Integer.valueOf(valores[0]);
        }
        if(valores[1]!=null){
            minuto=Integer.valueOf(valores[1]);
        }
        this.hora=hora;
        this.minuto=minuto;
    }
}
