package Rol;

import Curso.Curso;
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
