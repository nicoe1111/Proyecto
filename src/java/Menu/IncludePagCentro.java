/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name="includePagCentro")
public class IncludePagCentro {
    
       public String initialize() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        if(params.size() > 0){
            String Selected = (String) params.get("menu");
            
            if(Selected.equals("gestionUser")){
                return "<ui:include src=\"usuario/List.xhtml\"></ui:include>";
            }       
      }
        return "";
   }
    
}
