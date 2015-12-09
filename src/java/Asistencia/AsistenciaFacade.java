/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Asistencia;

import General.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class AsistenciaFacade extends AbstractFacade<Asistencia> {
    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AsistenciaFacade() {
        super(Asistencia.class);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Asistencia> getAsistenciaClaseDada(int id) {
        Query query = em.createQuery("SELECT a FROM Asistencia a WHERE a.claseDada.idClaseDada = :idClaseDada ORDER BY a.claseDada.fecha DESC");
        query.setParameter("idClaseDada", id);
        List<Asistencia> asistencias = query.getResultList();
        return asistencias;
    }
//    
//        @TransactionAttribute(TransactionAttributeType.REQUIRED)
//    public List<RespuestaPregunta> obtenerRespPreguntaIdLogCurso(int idEncuesta, int idUserLog, int idCurso) {
//        Query query = em.createQuery("SELECT r FROM RespuestaPregunta r WHERE r.encuesta.idEncuesta = :idEncuesta AND r.alumno.idRol = :idUser AND r.curso.idCurso = :idCurso");
//        query.setParameter("idUser", idUserLog);
//        query.setParameter("idEncuesta", idEncuesta);
//        query.setParameter("idCurso", idCurso);
//        List<RespuestaPregunta> encuesta = query.getResultList();
//        return encuesta;
//    }
}
