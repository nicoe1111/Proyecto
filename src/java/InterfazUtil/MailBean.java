package InterfazUtil;

import com.sun.mail.smtp.SMTPSendFailedException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.primefaces.context.RequestContext;

@Named("mailBean")
@ViewScoped
public class MailBean implements Serializable{
    
    public void function() throws MessagingException, UnsupportedEncodingException{
        Properties props = new Properties();
        
        String userFrom = "tecnoif.2013@gmail.com";
        String pass = "TISJ2013";
        String to = "asdg";
        String asunto = "Hola";
        String textoMensaje = "Mensajito con Java Mail";
        
        String status="";
        
//        props.setProperty("mail.smtp.host","smtp.gmail.com");
//        props.put("mail.transport.protocol","smtp");
//        props.setProperty("mail.smtp.starttls.enable", "true");
//        props.setProperty("mail.smtp.port","587");
//        props.put("mail.smtp.auth", "true");
//        props.setProperty("mail.smtp.user", user);
//        props.setProperty("mail.smtp.password", pass);
        
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        
// TLS si está disponible
        props.setProperty("mail.smtp.starttls.enable", "true");
        
// Puerto de gmail para envio de correos
        props.setProperty("mail.smtp.port","587");
        
// Nombre del usuario
        props.setProperty("mail.smtp.user", userFrom);
        
// Si requiere o no usuario y password para conectarse.
        props.setProperty("mail.smtp.auth", "true");
        
        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);
        
        Message msg = new MimeMessage(mailSession);
        
        msg.setSubject(asunto);
        msg.setFrom(new InternetAddress(userFrom));
        msg.addRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress(to) });
        msg.setText(textoMensaje);
        
        Transport t = mailSession.getTransport("smtp");
        t.connect(userFrom, pass);
        
        try {
            t.sendMessage(msg,msg.getAllRecipients());
            status="Mail Entregado !";
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",  status));
        }
        catch (  AddressException e) {
            status="Hay un error parceando la direccion. Error al enviar !" + e.getMessage();
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",  status));
            throw e;
        }
        catch (  AuthenticationFailedException e) {
            //status="The Username or Password may be wrong. Sending Failed !" + e.getMessage();
            status="El usuario o contraseña son incorrectos. Error al enviar !" + e.getMessage();
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",  status));
            throw e;
        }
        catch (  SMTPSendFailedException e) {
            //status="Sorry, There was an error sending the message. Sending Failed !" + e.getMessage();
            status="Perdon, hubo un error al embiar el mensaje. Error al enviar !" + e.getMessage();
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",  status));
            throw e;
        }
        catch (  MessagingException e) {
            //status="There was an unexpected error. Sending Failed ! " + e.getMessage();
            status="Hubo un error inesperado. Error al enviar ! " + e.getMessage();
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",  status));
            throw e;
        }
        catch (  Exception e) {
            //status="There was an unexpected error. Sending Falied !" + e.getMessage();
            status="Hubo un error inesperado. Error al enviar ! " + e.getMessage();
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",  status));
            throw e;
        }
        
        t.close();
        
        
        System.out.println(status);
    }
}
