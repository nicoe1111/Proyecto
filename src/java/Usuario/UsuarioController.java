package Usuario;

import Rol.Administrador;
import Rol.Administrativo;
import Rol.Alumno;
import Rol.Docente;
import Rol.TipoRol;
import Usuario.util.JsfUtil;
import Usuario.util.JsfUtil.PersistAction;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;


@Named("usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    @EJB
    private UsuarioFacade ejbUsuario;
    @EJB
    private Rol.RolFacade ejbRol;
    private List<Usuario> items = null;
    private Usuario selected;
    private infoadicionalalumno selectedInfoAdicional;

    private UploadedFile fileImagen;

    
    public UsuarioController() {
    }

    public Usuario getSelected() {
        return selected;
    }

    public void setSelected(Usuario selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UsuarioFacade getFacade() {
        return ejbUsuario;
    }

    public Usuario prepareCreate() {
        selected = new Usuario();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        
        seteoRolesSeleccionados();
        
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
  
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdated"));
        
        selectedRoles.clear();
    }
    
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UsuarioDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Usuario> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Usuario getUsuario(int id) {
        return getFacade().find(id);
    }

    public List<Usuario> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Usuario> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
  

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.getUsuario(getKey(value));
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
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getId_user());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Usuario.class.getName()});
                return null;
            }
        }

    }
    //////////Subir Imagen ///////////////////////////////////////
   public void upload(FileUploadEvent event) {        
        try {
            copyFile(event.getFile().getContentType(), event.getFile().getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }  
   
    public void copyFile(String fileTipo, InputStream in) {
        try {
            String url = getUrlImagenes();
             String nombreImagen = String.valueOf(selected.getId_user());
             OutputStream out = new FileOutputStream(new File(url + nombreImagen +  ".jpg"));
             url = url + nombreImagen + ".jpg";
             selected.setImagen(url);
             update();
             int read = 0;
             byte[] bytes = new byte[1024];

             while ((read = in.read(bytes)) != -1) {
                 out.write(bytes, 0, read);
             }
             in.close();
             out.flush();
             out.close();
             System.out.println("New file created!");
             } catch (IOException e) {
                System.out.println(e.getMessage());
             }
    }
    
    public String getUrlImagenes(){
//        ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        String url = ResourceBundle.getBundle("/Bundle").getString("Path");
//        url = url + "\\resources\\FotoPerfil\\";
        return url;
    }
    /////////////////////////roles////////////////////////////////////////////////////////////
    private final List<String> roles = new ArrayList<>();//lista de string para cargar los check
    private List<String> selectedRoles = new ArrayList<>();//lista que se carga cuando el usuario hace click en el check
    private List<String> rolesSelectedUser = new ArrayList<>();
    @PostConstruct
    void init(){
        roles.add("Alumno");
        roles.add("Administrativo");
        roles.add("Docente");
        roles.add("Administrador");
    }

    public List<String> getRoles() {
        return roles;
    } 

    public List<String> getSelectedRoles() {
        return selectedRoles;
    }

    public void setSelectedRoles(List<String> selectedRoles) {
        this.selectedRoles = selectedRoles;
    }
    
    /// Antes de crear el usuario verifico los roles seleccionados y se los seteo al usuario
    public void seteoRolesSeleccionados(){
        
        for (int i = 0; i < selectedRoles.size(); i++) { //lista de roles seleccionado es un String
            addRolUser(selectedRoles.get(i));
        }
        selectedRoles.clear();//luego de agregados los roles a la lista de usuarios borro los datos
    }
    //obtengo los roles del usuario seleccionado 
    public List<String> getRolesSelectedUser() {
        selectedRoles.clear();
        if(selected != null){
            for (int i = 0; i < selected.getRoles().size(); i++) {                                                   
                if(selected.getRoles().get(i).getClass().getName().contains("Administrador")){
                selectedRoles.add("Administrador");
                }else if(selected.getRoles().get(i).getClass().getName().contains("Administrativo")){
                    selectedRoles.add("Administrativo");
                }else if(selected.getRoles().get(i).getClass().getName().contains("Docente")){
                    selectedRoles.add("Docente");
                }else{
                    selectedRoles.add("Alumno");
                }
            }
        }
        rolesSelectedUser = selectedRoles;        
        return rolesSelectedUser;
    }

    public void setRolesSelectedUser(List<String> rolesSelectedUser) {
        this.rolesSelectedUser = rolesSelectedUser;
    }
    
    public void addRolUser(String tiporol){/// segun el string pasado por parametro crea el objeto rol asociado al usuario 
        if(tiporol.equals("Administrador")){    //y el rol es agregado a una lista de roles del usuario
            Administrador administrador = new Administrador();
            administrador.setUsuario(selected);// agrego el usuario al rol
            selected.addRol(administrador);// agreaga el rol al usuario selected
        }else if(tiporol.equals("Administrativo")){
            Administrativo administrativo = new Administrativo();
            administrativo.setUsuario(selected);// agrego el usuario al rol
            selected.addRol(administrativo);// agreaga el rol al usuario selected
        }else if(tiporol.equals("Docente")){
            Docente docente = new Docente();
            docente.setUsuario(selected);// agrego el usuario al rol
            selected.addRol(docente);// agreaga el rol al usuario selected
        }else{ 
            Alumno alumno = new Alumno();
            alumno.setUsuario(selected);// agrego el usuario al rol
            selected.addRol(alumno); // agreaga el rol al usuario selected
        }
    }
    
    ///verifica antes de update del usuario que los roles seleccionados y los persistidos sean iguales sino que agregue o elimine
    public void verificarRolesUserUpdate(){
        if(selectedRoles.size() > selected.getRoles().size()){
            
            
        }
        
    }
       
    public void destroyRolAntiguo(){
        Boolean bool = false;
        List<TipoRol> listaRoles = ejbRol.findRolUser(selected.getId_user());
        if(listaRoles.size() > 0){
            for (int i = 0; i < listaRoles.size(); i++) {
                for (int j = 0; j < selected.getRoles().size(); j++) {
                    if(listaRoles.get(i) == selected.getRoles().get(j)){
                        bool = true;
                }
                    if(bool == false){
                         ejbRol.remove(listaRoles.get(i));
                    }else{
                        bool = false;
                    }
                }
            }
        }
    }
    
  private StreamedContent graphicImage;

  public StreamedContent getGraphicImage() throws FileNotFoundException {
      if(selected.getImagen()!= null){
       FileInputStream fileImage = new FileInputStream(selected.getImagen());
       graphicImage = new DefaultStreamedContent(fileImage, "image/png");
      }
        return graphicImage;
    }

    public void setGraphicImage(StreamedContent graphicImage) {
        this.graphicImage = graphicImage;
    }
    public UploadedFile getFileImagen() {
        return fileImagen;
    }

    public void setFileImagen(UploadedFile fileImagen) {
        this.fileImagen = fileImagen;
    }
    
    public void uploadFile(FileUploadEvent event) {
        try {
            String path = getPathImagenPerfil();
            String archivo = path + File.separator + event.getFile().getFileName();

            FileOutputStream fileOutputStream = new FileOutputStream(archivo);
            byte[] buffer = new byte[6124];
            int bulk;
            InputStream inputStream = event.getFile().getInputstream();
            while (true) {
            bulk = inputStream.read(buffer);
            if (bulk < 0) {
              break;
            }
            fileOutputStream.write(buffer, 0, bulk);
            fileOutputStream.flush();
        }
        fileOutputStream.close();
        inputStream.close();

        selected.setImagen(event.getFile().getFileName());

        } catch (IOException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error al subir el archivo"));
        }
    }
    
    public String getPathImagenPerfil(){
        return ResourceBundle.getBundle("/Bundle").getString("Path");
    }
    
    public void getUserSession(){
        selected = new Usuario();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        UsuarioController uc = new UsuarioController();
        uc = (UsuarioController) session.getAttribute("usuarioMB");
        selected = uc.getSelected();
    }
    
    public void setPassnickUserCreate(String cedula){
        selected.setPass(cedula);
        selected.setNick(cedula);
    }
    
    public void setNickUserCreate(String cedula){
        selected.setNick(cedula);
    }
}
