package Pais;

import Departamento.Departamento;
import Localidad.Localidad;
import Pais.util.JsfUtil;
import Pais.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("localidadController")
@SessionScoped
public class LocalidadController implements Serializable {

    @EJB
    private LocalidadFacade ejbFacade;
    private List<Localidad> items = null;
    private Localidad selected;

    public LocalidadController() {
    }

    public Localidad getSelected() {
        return selected;
    }

    public void setSelected(Localidad selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private LocalidadFacade getFacade() {
        return ejbFacade;
    }

    public Localidad prepareCreate() {
        selected = new Localidad();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {        
        setDeptoSeleccionado(getStringDepto());        
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle1").getString("LocalidadCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    @EJB
    DepartamentoFacade ejbDepto;
    public void setDeptoSeleccionado(String nombreDpto){
        List<Departamento> deptos = ejbDepto.findNombre(nombreDpto);
        selected.setDepartamento(deptos.get(0));
    }
       
    public void update() {        
        setDeptoSeleccionado(getStringDepto());        
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle1").getString("LocalidadUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle1").getString("LocalidadDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Localidad> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle1").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle1").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Localidad getLocalidad(int id) {
        return getFacade().find(id);
    }

    public List<Localidad> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Localidad> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Localidad.class)
    public static class LocalidadControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LocalidadController controller = (LocalidadController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "localidadController");
            return controller.getLocalidad(getKey(value));
        }

        int getKey(String value) {
            int key;
            key = Integer.parseInt(value);
            return key;
        }

        String getStringKey(int value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Localidad) {
                Localidad o = (Localidad) object;
                return getStringKey(o.getIdLocalidad());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Localidad.class.getName()});
                return null;
            }
        }

    }
    
    @EJB
    DepartamentoFacade ejbDepartamento;
    private String stringDepto;
    private List<String> stringDeptos = new ArrayList<String>();

    public List<String> getItemDeptos(){
        stringDeptos.clear();
        List<Departamento> deptos = ejbDepartamento.findAll();
        for (int i = 0; i < deptos.size(); i++) {
            stringDeptos.add(deptos.get(i).getNombre());
        }
        return stringDeptos;
    }

    public String getStringDepto() {
        return stringDepto;
    }

    public void setStringDepto(String stringDepto) {
        this.stringDepto = stringDepto;
    }

    public List<String> getStringDeptos() {
        return stringDeptos;
    }

    public void setStringDeptos(List<String> stringDeptos) {
        this.stringDeptos = stringDeptos;
    }


}
