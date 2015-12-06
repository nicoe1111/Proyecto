/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Session;

import Usuario.Usuario;
import Usuario.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name="login")
@SessionScoped
public class LoginMB implements Serializable{
    
    @EJB
            UsuarioFacade persistUser;
    
    private String nick;
    private String password;
    boolean logged = false;
    private Usuario userLoged = null;
    
    public boolean isLogged() {
        return logged;
    }
    
    public void setLogged(boolean logged) {
        this.logged = logged;
    }
    
    public Usuario getUserLoged() {
        return userLoged;
    }
    
    public void setUserLoged(Usuario userLoged) {
        this.userLoged = userLoged;
    }
    
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
    
    public String login() throws IOException{
        if(persistUser.validarUsuario(nick, password)){
            this.logged=true;
            setUsuarioActual();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido " + nick, "Success"));
//            FacesContext.getCurrentInstance().getExternalContext().dispatch("index.xhtml");
            return "/vistas/index.xhtml";
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No existe ese usuario", "Error"));
//            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",  "No existe ese usuario"));
//            FacesContext.getCurrentInstance().getExternalContext().dispatch("login.xhtml");
            return "login.xhtml";
        }
    }
    
    public void setUsuarioActual(){
        userLoged = persistUser.findByNick(nick).get(0);
    }
    
    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        this.logged = false;
        return "../../";
    }
    
    public Usuario getUsuarioLogeado(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        LoginMB uc = (LoginMB) session.getAttribute("login");
        return uc.getUserLoged();
    }
    
    
}

