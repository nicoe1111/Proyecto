/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name="menuView")
public class menuView implements Serializable{
     
    public void menu() throws IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
        context.redirect("faces/index.xhtml?menu=gestionUser");
        addMessage("Success", "Gestion de Usuario");
    }
     
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
