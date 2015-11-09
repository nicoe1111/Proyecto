/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InstanciaEvaluacion;

import javax.persistence.Entity;

@Entity(name="Parcial")
public class Parcial extends Evaluacion{
    private int porcentajeCurso;

    public int getPorcentajeCurso() {
        return porcentajeCurso;
    }

    public void setPorcentajeCurso(int porcentajeCurso) {
        this.porcentajeCurso = porcentajeCurso;
    }

    public Parcial() {
    }

}
