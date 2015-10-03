package Pais;

import Departamento.Departamento;
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

@Named("departamentoController")
@SessionScoped
public class DepartamentoController implements Serializable {

    @EJB
    private DepartamentoFacade ejbFacade;
    private List<Departamento> items = null;
    private Departamento selected;

    public DepartamentoController() {
    }

    public Departamento getSelected() {
        return selected;
    }

    public void setSelected(Departamento selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DepartamentoFacade getFacade() {
        return ejbFacade;
    }

    public Departamento prepareCreate() {
        selected = new Departamento();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        setPaisSeleccionado(getStringPais());
        stringPaises.clear();
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle1").getString("DepartamentoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void setPaisSeleccionado(String nombrePais){
        List<Pais> paises = ejbPais.findNombre(nombrePais);
        selected.setPais(paises.get(0));
    }
    
    public void update() {
        setPaisSeleccionado(getStringPais());
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle1").getString("DepartamentoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle1").getString("DepartamentoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Departamento> getItems() {
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

    public Departamento getDepartamento(int id) {
        return getFacade().find(id);
    }

    public List<Departamento> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Departamento> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Departamento.class)
    public static class DepartamentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DepartamentoController controller = (DepartamentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "departamentoController");
            return controller.getDepartamento(getKey(value));
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
            if (object instanceof Departamento) {
                Departamento o = (Departamento) object;
                return getStringKey(o.getIdDepartamento());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Departamento.class.getName()});
                return null;
            }
        }

    }
   
        @EJB
        PaisFacade ejbPais;
        private String stringPais;
        private List<String> stringPaises = new ArrayList<String>();
        
        public List<String> getItemPaises(){
            stringPaises.clear();
            List<Pais> paises = ejbPais.findAll();
            for (int i = 0; i < paises.size(); i++) {
                stringPaises.add(paises.get(i).getNombre());
            }
            return stringPaises;
        }

    public String getStringPais() {
        return stringPais;
    }

    public List<String> getStringPaises() {
        return stringPaises;
    }

    public void setStringPais(String stringPais) {
        this.stringPais = stringPais;
    }

    public void setStringPaises(List<String> stringPaises) {
        this.stringPaises = stringPaises;
    }
        
    }
