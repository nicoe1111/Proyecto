package InterfazUtil;

public class HorariosGrid {
    String inicio;
    String fin;
    diaSemana lunes = new diaSemana();
    diaSemana martes= new diaSemana();
    diaSemana miercoles= new diaSemana();
    diaSemana jueves= new diaSemana();
    diaSemana viernes= new diaSemana();
    diaSemana sabado= new diaSemana();

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public diaSemana getLunes() {
        return lunes;
    }

    public void setLunes(diaSemana lunes) {
        this.lunes = lunes;
    }

    public diaSemana getMartes() {
        return martes;
    }

    public void setMartes(diaSemana martes) {
        this.martes = martes;
    }

    public diaSemana getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(diaSemana miercoles) {
        this.miercoles = miercoles;
    }

    public diaSemana getJueves() {
        return jueves;
    }

    public void setJueves(diaSemana jueves) {
        this.jueves = jueves;
    }

    public diaSemana getViernes() {
        return viernes;
    }

    public void setViernes(diaSemana viernes) {
        this.viernes = viernes;
    }

    public diaSemana getSabado() {
        return sabado;
    }

    public void setSabado(diaSemana sabado) {
        this.sabado = sabado;
    }
    
    

    public HorariosGrid() {
    }
    
    public class diaSemana{
        String dato = "";
        String rowspan="0";
        boolean enable = true;

        public diaSemana() {
        }
        
        public String getDato() {
            return dato;
        }

        public void setDato(String dato) {
            this.dato = dato;
        }

        public String getRowspan() {
            return rowspan;
        }

        public void setRowspan(String rowspan) {
            this.rowspan = rowspan;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
        
    }
}
