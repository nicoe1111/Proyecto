/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package InterfazUtil;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Diego
 */
@Named
@SessionScoped
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
