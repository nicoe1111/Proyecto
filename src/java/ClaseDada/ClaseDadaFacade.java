/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ClaseDada;

import Asistencia.Asistencia;
import General.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class ClaseDadaFacade extends AbstractFacade<ClaseDada> {
    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ClaseDadaFacade() {
        super(ClaseDada.class);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ClaseDada> obtenerClasesDadasIdCurso(int id) {
        Query query = em.createQuery("SELECT cd FROM ClaseDada cd WHERE cd.curso.idCurso = :idCurso");
        query.setParameter("idCurso", id);
        List<ClaseDada> clasesDadas = query.getResultList();
        return clasesDadas;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int obtenerCurso_ClaseDadaID(int id) {
        int idCursoClaseDada = 0;
        Query query = em.createQuery("SELECT cd.curso.idCurso FROM ClaseDada cd WHERE cd.idClaseDada = :idCLase");
        query.setParameter("idCLase", id);
        if(query.getResultList().size() > 0){
            idCursoClaseDada = (int) query.getResultList().get(0);
        }
        return idCursoClaseDada;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Asistencia> obtenerAsistenciaAlumno(int idAlumno) {
        List<Asistencia> asistencias;
        Query query = em.createQuery("SELECT a FROM Asistencia a WHERE a.alumno.idRol = :idAlumno");
        query.setParameter("idAlumno", idAlumno);
            asistencias = query.getResultList();
        return asistencias;
    }
    
}
