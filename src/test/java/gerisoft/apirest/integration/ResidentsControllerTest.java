package gerisoft.apirest.integration;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;
/**
 *
 * @author Gurrea
 */
public class ResidentsControllerTest extends APITest{

    @Test
    public void listResidents_withFilter() {//Fem una prova amb un filtre per a la cerca de residents
        
        given().
                contentType("application/json").
                headers(HEADER_AUTHENTICATION, tokenAdmin).
                port(port).
                when().
                get("/residents?nom=3").
                then().
                statusCode(200).
                body("results.size()", equalTo(13));//Ha de ser un array de 13, el 3, el 13, el 23 i tots els 30 i algo
    }
    @Test
    public void listResidents_invalidToken() {//Fem una prova amb el token de sessio erroni
        
        given().
                contentType("application/json").
                headers(HEADER_AUTHENTICATION, tokenAdmin+"invalidToken").
                port(port).
                when().
                get("/residents?nom=3").
                then().
                statusCode(401);
    }
}
