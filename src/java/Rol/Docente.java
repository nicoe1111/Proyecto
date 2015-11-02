/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rol;

import Curso.Curso;
import Rol.TipoRol;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;

@Entity
public class Docente extends TipoRol{
    
    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Curso> cursos = new ArrayList<>();

    @Transactional
    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> Cursos) {
        this.cursos = Cursos;
    }
}
