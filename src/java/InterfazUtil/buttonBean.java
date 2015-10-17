package InterfazUtil;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class buttonBean implements Serializable{
    private Boolean disabled = true;

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
    
    public void onRowSelect(SelectEvent event) {
        disabled = false;
    }
    
     public void onRowUnselect(SelectEvent event) {
        disabled = true;
    }
}
