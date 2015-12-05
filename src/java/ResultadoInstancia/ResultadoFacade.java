/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResultadoInstancia;

import InstanciaEvaluacion.*;
import Curso.*;
import General.AbstractFacade;
import Rol.Alumno;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ResultadoFacade extends AbstractFacade<ResultadoInstancia> {
    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ResultadoFacade() {
        super(ResultadoInstancia.class);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ResultadoInstancia findResultadoEvalAlumno(int idEvaluacion, int idAlumno) {
        Query query = em.createQuery("SELECT ri FROM ResultadoInstancia ri WHERE ri.alumno.idRol = :idAlumno AND ri.instanciaEvaluacion.idEvaluacion = :idEvaluacion");
        query.setParameter("idAlumno", idAlumno);
        query.setParameter("idEvaluacion", idEvaluacion);
        return (ResultadoInstancia)query.getResultList().get(0);
    }
}
