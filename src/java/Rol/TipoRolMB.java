/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rol;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class TipoRolMB {
    
    private List<String> roles = new ArrayList<>();
    
    @PostConstruct
    void init(){
        roles.add("Alumno");
        roles.add("Administrativo");
        roles.add("Docente");
        roles.add("Administrador");
    }

    public List<String> getRoles() {
        return roles;
    }  
}
