/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Gurrea
 */
public class ActivitiesControllerTest extends APITest {//Provem les crides de Activities

    @Test
    public void listActivities_withFilter() {//Fem una prova amb un filtre per a la cerca de Activities

        given().
                contentType("application/json").                
                headers(HEADER_AUTHENTICATION, tokenAdmin).
                port(port).
                when().
                get("/activities?name=3").
                then().
                statusCode(200).
                body("results.size()", equalTo(13));//Ha de ser un array de 13, el 3, el 13, el 23 i tots els 30 i algo
    }

   
    @Test
    public void listActivities_invalidToken() {//Fem una prova amb el token de sessio erroni
        given().
                contentType("applicacion/json").
                headers(HEADER_AUTHENTICATION, tokenAdmin + "invalidToken").
                port(port).
                when().
                get("/activities?name=3").
                then().
                statusCode(401);

    }

}
