/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClaseDada;

import Curso.Curso;
import Curso.CursoFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("claseDadaController")
@ViewScoped
public class ClaseDadaController implements Serializable{
    
    ClaseDada claseDada;
    List<Curso> cursos = new ArrayList<>();
    @EJB
    ClaseDadaFacade ejbClaseDada;
    @EJB
    CursoFacade ejbCurso;

    public ClaseDada getClaseDada() {
        if(claseDada == null){
            claseDada = new ClaseDada();
        }
        return claseDada;
    }

    public void setClaseDada(ClaseDada claseDada) {
        this.claseDada = claseDada;
    }

    public List<Curso> getCursos() {
        cursos = ejbCurso.findAll();
        return cursos;
    }
    
}
