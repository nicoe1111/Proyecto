package InterfazUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named("dateTransorm")
@ViewScoped
public class DateTransorm implements Serializable{
    
    public String obtenerYMDFormat(Date date){
        String newstring = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return newstring;
    }
}
