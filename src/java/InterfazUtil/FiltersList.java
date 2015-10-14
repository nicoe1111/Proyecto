/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfazUtil;

import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("filtersList")
@ViewScoped
public class FiltersList implements Serializable{
    
    private List<?> filteredItems;

    public List<?> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<?> filteredItems) {
        this.filteredItems = filteredItems;
    }
}
