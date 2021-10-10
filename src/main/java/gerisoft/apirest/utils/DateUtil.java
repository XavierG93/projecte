package gerisoft.apirest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Gurrea
 */
//Classe per formatar un string a date
public class DateUtil {


    public static Date parseToDate(String date) {
        try {
            return date!=null ? new SimpleDateFormat("yyyy-MM-dd").parse(date) : null;//li donem nomes format any, mes i dia
        }catch (ParseException p) {
            return null;
        }
    }
}
