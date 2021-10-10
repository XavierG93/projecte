package gerisoft.apirest.integration;

import gerisoft.apirest.config.JwtToken;
import gerisoft.apirest.entity.Activities;
import gerisoft.apirest.entity.Attentions;
import gerisoft.apirest.entity.Residents;
import gerisoft.apirest.entity.User;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.repository.ActivitiesRepository;
import gerisoft.apirest.repository.AttentionsRepository;
import gerisoft.apirest.repository.ResidentsRepository;
import gerisoft.apirest.repository.UserRepository;
import gerisoft.apirest.service.UserService;
import gerisoft.apirest.service.impl.JwtUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import org.springframework.test.context.TestPropertySource;

/**
 *
 * @author Gurrea
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//aixeca un servidor fals amb un port random
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)//
@TestPropertySource("classpath:integration-application.properties")
public class APITest {

    /*
    Fem una prova d'integracio amb tots els elements pero a un servidor "fals", fem crides igual que al postman
     */
    protected String HEADER_AUTHENTICATION = "Authorization";
    protected String tokenAdmin;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private ResidentsRepository residentsRepository;
    @Autowired
    private ActivitiesRepository activitiesRepository;
    @Autowired
    private AttentionsRepository attentionsRepository;
    
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private UserService usersService;
    @LocalServerPort
    public int port;

    @BeforeEach
    public void init() throws PrivilegesException, ValidationServiceException {//generem el token del usuari admin que ja existeix
        User user = usersService.findByUsername("admin", true).get();
        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(user.getUsername());
        this.tokenAdmin = "Bearer " + jwtToken.generateToken(userDetails, user);
        createResidents();
        createActivities();
        createAttentions();
    }

    /*
    Creem residents per a fer la prova
     */
    private void createResidents() {
        residentsRepository.deleteAll();
        for (long i = 0; i < 40L; i++) {
            Residents r = new Residents();
            r.setNom("nom " + i);
            r.setDataNaixement(new Date(1000L * i));
            r.setDataIngres(new Date());
            r.setDni(i + "0000001");
            r.setCognom1("cognom1 test" + i);
            r.setCognom2("cognom2 test" + i);
            residentsRepository.save(r);
        }
    }

    /*
    Creem activities per a fer la prova
     */
    private void createActivities() {
        activitiesRepository.deleteAll();
        for (long i = 0; i < 40L; i++) {
            Activities a = new Activities();
            a.setName("nom " + i);
            activitiesRepository.save(a);
        }
    }
    /*
    Creem attentions per a fer la prova
     */
    private void createAttentions() {
        attentionsRepository.deleteAll();
        for (long i = 0; i < 40L; i++) {
            Attentions a = new Attentions();
            a.setName("nom " + i);
            
            attentionsRepository.save(a);
        }
    }

}
