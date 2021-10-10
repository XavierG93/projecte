package gerisoft.apirest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 *
 * @author Gurrea
 */

public abstract class SimpleBaseTestCase {

    private AutoCloseable closeable;//Classe que implementa mockito per tancar les imitacions de comportament

    @BeforeEach//Abans de cada test
    public void openMocks() {
        /*
        Mockejem el security holder donat que sabem que funciona, volem provar els service
        */
        closeable = MockitoAnnotations.openMocks(this);//crida les classes anotades com a Mock per a que imitin el seu comportament
        Authentication authentication = Mockito.mock(Authentication.class);//mockeja el comportament d'aquesta classe
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);//mockeja el comportament d'aquesta classe
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);//quan retorni la resposta introdueix el mock anterior com a resposta
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getAuthorities()).thenReturn(null);//Amb el null fem un salt de linia, aixi no executa la classe
    }

    @AfterEach//despres de cada test
    public void releaseMocks() throws Exception {
        closeable.close();
    }
}
