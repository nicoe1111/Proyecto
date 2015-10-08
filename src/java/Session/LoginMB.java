/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Usuario.Usuario;
import Usuario.UsuarioController;
import Usuario.UsuarioController2;
import Usuario.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Diego
 */
@ManagedBean(name="login")
@SessionScoped
public class LoginMB implements Serializable{
    
    @EJB
    UsuarioFacade persistUser;
    
    private String nick;
    private String password;
    boolean logged = false;
    
    public boolean getLogged() {
        return logged;
    }
    
    public String getNick() {
        return nick;
    }
    
    public void setNick(String nick) {
        this.nick = nick;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void login() throws IOException{
        if(persistUser.validarUsuario(nick, password)){
            this.logged=true;
            setUsuarioActual();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Success" ,"Bien benido " + nick));
            FacesContext.getCurrentInstance().getExternalContext().dispatch("index.xhtml");
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",  "No existe ese usuario"));
            FacesContext.getCurrentInstance().getExternalContext().dispatch("login.xhtml");
        }
    }
 
    public void setUsuarioActual(){
        Usuario u = persistUser.findByNick(nick).get(0);
        UsuarioController2 user = new UsuarioController2();
        user.setSelected(u);///seteo usuario logeado del controlador
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute("controladorUsuario2", user);
    }
    
    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        this.logged = false;
        return "Logout";
    }
    
    public void obtenerParametros(){
        FacesContext facesContext = FacesContext. getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        if(params.size() > 0){
            Boolean categorySelected = new Boolean((String) params.get("login"));
          if(!categorySelected){
              logout();
          }
        }
    }
    
    public Usuario getUserSession(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        UsuarioController2 uc = (UsuarioController2) session.getAttribute("usuarioController2");
        return uc.getSelected();
    }
}

