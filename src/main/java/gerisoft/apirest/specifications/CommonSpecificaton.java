package gerisoft.apirest.specifications;

import java.util.Map;

import static org.apache.logging.log4j.util.Strings.isEmpty;
/**
 *
 * @author Gurrea
 */
public abstract class CommonSpecificaton {
    /*
    Aqui rebem les especificacions de la url de cerca
    */
    private Map<String, String> params;
    /*
    constructor on afegim els parametres
    */
    protected CommonSpecificaton(Map<String, String> params) {
        this.params = params;
    }

    protected String getFilterValue(String param) {
        return isEmpty(params.get(param)) ? null : params.get(param);
    }
}
