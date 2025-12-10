package tp.tp_jsf;

import dao.DAOException;
import dao.jpa.DAOThemeJPA;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import mappedclass.Theme;

import java.util.List;

@Named("beanTheme")
@ApplicationScoped
public class BeanTheme {
    public List<Theme> getThemes(){
        try{
            return DAOThemeJPA.getInstance().read("");
        }catch(DAOException ex){
            ex.printStackTrace();
        }
        return null;
    }

}
