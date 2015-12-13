package RespuestaPregunta;

import Correo.Mail;
import Encuesta.Encuesta;
import Rol.Alumno;
import Session.LoginMB;
import Usuario.Usuario;
import Usuario.UsuarioFacade;
import Usuario.util.JsfUtil;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.mail.MessagingException;

@Named("respupreguntaControl")
@ViewScoped
public class RespuestaPreguntaController implements Serializable{
    
    @EJB
    private RespuestaPreguntaFacade ejbRespuestaPregunta;
    
    private List<RespuestaPregunta> items = null;
    private List<RespuestaPregunta> itemsEncuesta = null;
    private RespuestaPregunta selected = null;
    private Usuario userLog;
    private String profesor = "";
    private String materia = "";
    private List<String> puntajes = new ArrayList<>();
    String mensaje = "";
    private List<RespuestaPregunta> itemsPendientes = null;
    private Alumno alumnoPendiente;
    
    public List<RespuestaPregunta> getItems() {
        items = obtenerRespuestaPregunta();
        return items;
    }
    
    public List<RespuestaPregunta> getItemsPendientes() {
        itemsPendientes = obtenerEncuestasPendientes();
        return itemsPendientes;
    }
    
    public void setItemsPendientes(List<RespuestaPregunta> itemsPendientes) {
        this.itemsPendientes = itemsPendientes;
    }
    
    public List<RespuestaPregunta> getItemsEncuesta() {
        return itemsEncuesta;
    }
    
    public void resetVariables(){
        items.clear();
        
    }
    ArrayList<String> puntajeCursosSemestre = new ArrayList<>();
    ArrayList<ArrayList<String>> listaRespPreguntas = new ArrayList<>();
    public void  obtenerDatos(){
        listaRespPreguntas.clear();
        puntajeCursosSemestre.clear();
        String idRespuestaPregunta = obtenerParametros();
        if(idRespuestaPregunta != null){
            RespuestaPregunta rp =ejbRespuestaPregunta.find(Integer.valueOf(idRespuestaPregunta));
            materia = rp.getCurso().getMateria().getNombre();
            profesor = rp.getCurso().getDocente().getUsuario().getPrimerNombre() + "  " + rp.getCurso().getDocente().getUsuario().getPrimerApellido();
            List<RespuestaPregunta> listRp = ejbRespuestaPregunta.obtenerRespPreguntaIdLogCurso(rp.getEncuesta().getIdEncuesta(), rp.getAlumno().getIdRol(), rp.getCurso().getIdCurso());
            setItemsEncuesta(listRp);
        }
        puntajes = getPuntajes();
        int j = 0;
        for (int i = 0; i < itemsEncuesta.size(); i++) {
            for (String itemPuntaje : puntajes) {
                puntajeCursosSemestre = new ArrayList<>();
                puntajeCursosSemestre.add(String.valueOf(itemsEncuesta.get(i).getIdRespuestaPregunta()));
                puntajeCursosSemestre.add(itemPuntaje);
                listaRespPreguntas.add(puntajeCursosSemestre);
            }
        }
    }
    
    public ArrayList<ArrayList<String>>  obtenerDatosChek(int idRespuestaPregunta){
        listaRespPreguntas.clear();
        puntajeCursosSemestre.clear();
        RespuestaPregunta rp = null;
        puntajes = getPuntajes();
        for (int i = 0; i < itemsEncuesta.size(); i++) {
            if(itemsEncuesta.get(i).getIdRespuestaPregunta() == idRespuestaPregunta){
                rp = new RespuestaPregunta();
                rp = itemsEncuesta.get(i);
            }
        }
        if(rp != null){
            for (String itemPuntaje : puntajes) {
                puntajeCursosSemestre = new ArrayList<>();
                puntajeCursosSemestre.add(String.valueOf(rp.getIdRespuestaPregunta()));
                puntajeCursosSemestre.add(itemPuntaje);
                listaRespPreguntas.add(puntajeCursosSemestre);
            }
        }
        return listaRespPreguntas;
    }
    
    public ArrayList<String> getPuntajeCursosSemestre() {
        return puntajeCursosSemestre;
    }
    
    public ArrayList<ArrayList<String>> getListaRespPreguntas() {
        return listaRespPreguntas;
    }
    String itemPreguntaSelec;
    ArrayList<String> selectedlistaRespPreguntas = new ArrayList<>();
    ArrayList<String> preguntasContestadas = new ArrayList<>();
    public String realizarEncuesta() throws IOException{
        mensaje = "";
        preguntasContestadas = getSelectedlistaRespPreguntas();
        
        List<RespuestaPregunta> listRp = pasarAlista(preguntasContestadas);
        
        if(preguntasContestadas.size() == itemsEncuesta.size()){
            
            for (int i = 0; i < listRp.size(); i++) {
                ejbRespuestaPregunta.edit(listRp.get(i));
            }
        }else{
            mensaje =  "Faltan preguntas por contestar!!";
            selectedlistaRespPreguntas.clear();
        }
        return "ListarEncuestas.xhtml";
    }
    
    public List<RespuestaPregunta> pasarAlista(ArrayList<String> preguntasContestadas){
        List<RespuestaPregunta>listRp = new ArrayList<RespuestaPregunta>();
        for (int i = 0; i < preguntasContestadas.size(); i++) {
            String rp = preguntasContestadas.get(i).replace("[", "");
            rp = rp.replace("]", "");
            int largo = rp.length();
            String puntaje = rp.substring(largo-1);
            rp = rp.substring(0, largo-1);
            rp = rp.replace(",", "");
            RespuestaPregunta respuestaPregunta = ejbRespuestaPregunta.find(Integer.valueOf(rp.substring(0, largo-3)));
            respuestaPregunta.setPuntaje(Integer.valueOf(puntaje));
            respuestaPregunta.setContesto(true);
            listRp.add(respuestaPregunta);
        }
        return listRp;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public String getItemPreguntaSelec() {
        return itemPreguntaSelec;
    }
    
    public void setItemPreguntaSelec(String itemPreguntaSelec) {
        this.itemPreguntaSelec = itemPreguntaSelec;
        setSelectedlistaRespPreguntas(itemPreguntaSelec);
    }
    
    public void setSelectedlistaRespPreguntas(String selectedRespPreguntas) {
        this.selectedlistaRespPreguntas.add(selectedRespPreguntas);
    }
    
    public ArrayList<String> getSelectedlistaRespPreguntas() {
        return selectedlistaRespPreguntas;
    }
    
    public ArrayList<String> getPreguntasContestadas() {
        return preguntasContestadas;
    }
    
    public void setPreguntasContestadas(ArrayList<String> preguntasContestadas) {
        this.preguntasContestadas = preguntasContestadas;
    }
    
    
    
    public List<String> getPuntajes() {
        puntajes.clear();
        puntajes.add("1");
        puntajes.add("2");
        puntajes.add("3");
        puntajes.add("4");
        puntajes.add("5");
        return puntajes;
    }
    
    public void setPuntajes(List<String> puntajes) {
        this.puntajes = puntajes;
    }
    
    public String getProfesor() {
        return profesor;
    }
    
    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }
    
    public String getMateria() {
        return materia;
    }
    
    public void setMateria(String materia) {
        this.materia = materia;
    }
    
    public void setItemsEncuesta(List<RespuestaPregunta> itemsEncuesta) {
        this.itemsEncuesta = itemsEncuesta;
    }
    
    public void setItems(List<RespuestaPregunta> items) {
        this.items = items;
    }
    
    public Usuario getUserLog() {
        if(userLog == null){
            getUserSession();
        }
        return userLog;
    }
    
    public void setUserLog(Usuario userLog) {
        this.userLog = userLog;
    }
    
    public RespuestaPregunta getSelected() {
        return selected;
    }
    
    public void setSelected(RespuestaPregunta selected) {
        this.selected = selected;
    }
    
    public void updateSelected(){
        ejbRespuestaPregunta.edit(selected);
        selected = null;
    }
    
    public void update(int id){
        RespuestaPregunta p = ejbRespuestaPregunta.find(id);
        ejbRespuestaPregunta.edit(p);
        selected = null;
    }
    
    public void deleteSelected() {
        ejbRespuestaPregunta.remove(selected);
        selected = null;
    }
    
    public void delete(int id) {
        RespuestaPregunta p = ejbRespuestaPregunta.find(id);
        ejbRespuestaPregunta.remove(p);
        selected = null;
    }
    
    public void createSelected(){
        ejbRespuestaPregunta.create(selected);
        updateItems();
        selected = null;
    }
    
    private void updateItems(){
        items = obtenerRespuestaPregunta();
    }
    
    /////filtrossssss/////////
    
    private List<RespuestaPregunta> filteredPregunta;
    
    public List<RespuestaPregunta> getFilteredPreguntas() {
        return filteredPregunta;
    }
    
    public void setFilteredPreguntas(List<RespuestaPregunta> filteredRespuestaPregunta) {
        this.filteredPregunta = filteredRespuestaPregunta;
    }
    
    //////////////////////////
    
    public void loadSelected(int id){
        selected=ejbRespuestaPregunta.find(id);
    }
    
    public void getUserSession(){
        LoginMB login = new LoginMB();
        userLog = new Usuario();
        userLog = login.getUsuarioLogeado();
    }
    
    public List<Encuesta> obtenerEncuesta(){
        boolean bool = false;
        List<Encuesta> encuestas = new ArrayList<Encuesta>();
        getUserLog();
        
        List<RespuestaPregunta> respuestasPreguntas = ejbRespuestaPregunta.obtenerRespuestasPreguntaIdLog(userLog.getRolAlumno().getIdRol());
        for (int i = 0; i < respuestasPreguntas.size(); i++) {
            bool = false;
            if(!respuestasPreguntas.get(i).isContesto()){
                for (int j = 0; j < encuestas.size(); j++) {
                    if(encuestas.get(j).getIdEncuesta() == respuestasPreguntas.get(i).getEncuesta().getIdEncuesta()){
                        bool = true;
                    }
                }
                if(!bool){
                    encuestas.add(respuestasPreguntas.get(i).getEncuesta());
                }
            }
        }
        return encuestas;
    }
    
    public List<RespuestaPregunta> obtenerRespuestaPregunta(){
        boolean bool = false;
        List<RespuestaPregunta> encuestasRespuestas = new ArrayList<>();
        getUserLog();
        if(userLog.getRolAlumno() != null){
            List<RespuestaPregunta> respuestasPreguntas = ejbRespuestaPregunta.obtenerRespuestasPreguntaIdLog(userLog.getRolAlumno().getIdRol());
            for (int i = 0; i < respuestasPreguntas.size(); i++) {
                bool = false;
                for (int j = 0; j < encuestasRespuestas.size(); j++) {
                    if(encuestasRespuestas.get(j).getEncuesta().getIdEncuesta() == respuestasPreguntas.get(i).getEncuesta().getIdEncuesta()
                            && encuestasRespuestas.get(j).getCurso().getIdCurso() == respuestasPreguntas.get(i).getCurso().getIdCurso()){
                        bool = true;
                    }
                }
                if(!bool){
                    encuestasRespuestas.add(respuestasPreguntas.get(i));
                }
            }
        }
        return encuestasRespuestas;
    }
    
    private int actualYear = Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    public List<RespuestaPregunta> obtenerEncuestasPendientes(){
        boolean bool = false;
        List<RespuestaPregunta> encuestasRespuestas = new ArrayList<RespuestaPregunta>();
        List<RespuestaPregunta> respuestasPreguntas = ejbRespuestaPregunta.findAll();
        for (int i = 0; i < respuestasPreguntas.size(); i++) {
            bool = false;
            if(!respuestasPreguntas.get(i).isContesto() && respuestasPreguntas.get(i).getEncuesta().getFecha() == actualYear){
                for (int j = 0; j < encuestasRespuestas.size(); j++) {
                    if(encuestasRespuestas.get(j).getEncuesta().getIdEncuesta() == respuestasPreguntas.get(i).getEncuesta().getIdEncuesta() &&
                            encuestasRespuestas.get(j).getAlumno().getUsuario().getId_user() ==  respuestasPreguntas.get(i).getAlumno().getUsuario().getId_user()){
                        bool = true;
                    }
                }
                if(!bool){
                    encuestasRespuestas.add(respuestasPreguntas.get(i));
                }
            }
        }
        return encuestasRespuestas;
    }

    public int getActualYear() {
        return actualYear;
    }
    
    public RespuestaPregunta getRespuestaPregunta(int id) {
        return ejbRespuestaPregunta.find(id);
    }
    
    public String obtenerParametros(){
        Map<String, String> params =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String idRespuestaPregunta = params.get("idRespuestaPregunta");
        return idRespuestaPregunta;
    }
    
    public String getCurso(int id){
        RespuestaPregunta respuesta = ejbRespuestaPregunta.find(id);
        return  respuesta.getCurso().getMateria().getSemestre() + "  -  " +respuesta.getCurso().getMateria().getNombre();
    }
    
    public Alumno getAlumnoPendiente(int idRespPregunta) {
        RespuestaPregunta respuesta = ejbRespuestaPregunta.find(idRespPregunta);
        alumnoPendiente = new Alumno();
        alumnoPendiente = respuesta.getAlumno();
        return alumnoPendiente;
    }
    
    public Alumno getAlumnoPendiente() {
        return alumnoPendiente;
    }
    
    public void setAlumnoPendiente(Alumno alumnoPendiente) {
        this.alumnoPendiente = alumnoPendiente;
    }
    
    
    public void crearCorreoTodos() throws MessagingException, UnsupportedEncodingException{
        Mail MailCorreo = new Mail();
        MailCorreo.getCorreo().setMensaje(ResourceBundle.getBundle("/Bundle").getString("msjVariosEncuesta"));
        MailCorreo.getCorreo().setAsunto(ResourceBundle.getBundle("/Bundle").getString("AsuntoEncuesta"));
        String alumnos = "";
        Alumno alumno;
        for (int i = 0; i < itemsPendientes.size(); i++) {
            alumno = getAlumnoPendiente(itemsPendientes.get(i).getIdRespuestaPregunta());
            alumnos = alumnos + alumno.getUsuario().getMail();
            if(i+1 < itemsPendientes.size()){
                alumnos = alumnos + ", ";
            }
        }
        MailCorreo.getCorreo().setTo(alumnos);
        MailCorreo.function();
    }
    
    @EJB
            UsuarioFacade ejbUser;
    public void enviarCorreoUsuario(int idUser) throws MessagingException, UnsupportedEncodingException{
        Usuario user = ejbUser.find(idUser);
        if(!user.getMail().isEmpty()){
            Mail MailCorreo = new Mail();
            MailCorreo.getCorreo().setMensaje(ResourceBundle.getBundle("/Bundle").getString("msjEncuesta"));
            MailCorreo.getCorreo().setAsunto(ResourceBundle.getBundle("/Bundle").getString("AsuntoEncuesta"));
            
            MailCorreo.getCorreo().setTo(user.getMail());
            MailCorreo.function();
        }else{
            JsfUtil.addErrorMessage("No fue posible enviarle correo a " + user.getPrimerNombre() + " "+ user.getPrimerApellido() +
                    " verifique que el usuario tenga un mail valido");
        }
    }
    
}