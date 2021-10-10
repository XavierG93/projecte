package gerisoft.apirest.service.impl;

import gerisoft.apirest.SimpleBaseTestCase;
import gerisoft.apirest.entity.Activities;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.repository.ActivitiesRepository;
import gerisoft.apirest.utils.PrivilegesChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

/**
 *
 * @author Gurrea
 */
//Testejem els service, començem amb activities, volem fer una prova de tots els metodes que conté aquesta classe
public class ActivitiesServiceImplTest extends SimpleBaseTestCase {

    @Mock//li diem que faci el comportament de la classe que cridem
    private ActivitiesRepository activitiesRepository;
    @Mock
    private PrivilegesChecker privilegesChecker;
    @InjectMocks//injectem les dependencies del Mock
    private ActivitiesServiceImpl activitiesService;

    @Test
    void findAllActivities_OK() throws PrivilegesException {//Resultats correctes
        //GIVEN; preparem els arguments per a fer la crida
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        List<Activities> activities = buildTestActivitiesList(10L);//Creem 10 activitats
        Page<Activities> expectedPage = new PageImpl(activities, page, 10L);//Creem un objecte page de entitats creades anteriorment
        //Simulem el comportament de les classes de seguretat
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);//Davant qualsevol resposta, retorna true
        Mockito.when(activitiesRepository.findAll(any(),eq(page))).thenReturn(expectedPage);//Davant qualsevol resposta, retorna el resultat que nosaltres hem creat
        //when
        Page<Activities> result = activitiesService.findAllActivities(params, page);//Executem la clase de veritat amb els securitys mockejats
        //then
        Assertions.assertEquals(expectedPage, result);//Comprovem que el resultat es semblant a el que esperem
    }

    @Test
    void findAllActivities_privilegeExeption() {//Resultat d'error de privilegis
        //GIVEN
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);//Davant qualsevol resposta, retorna false
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> activitiesService.findAllActivities(params, page));//llencem l'excepcio directament
    }

    @Test
    void findActivitiesById_OK() throws PrivilegesException {
        //GIVEN
        Long id = 1L;
        Activities activitie = new Activities();
        activitie.setId(id);
        activitie.setName("Activitie test");
        Optional<Activities> responseExpected = Optional.of(activitie);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(activitiesRepository.findById(id)).thenReturn(responseExpected);//Retorna el activitie creat si te resposta
        //When
        Optional<Activities> result = activitiesService.findActivitiesById(id);//Activitie creat amb aquest id
        //Then
        Assertions.assertEquals(responseExpected, result);//Comparem si el retornat es igual al que s'espera
    }

    @Test
    void findActivitiesById_privilegeExeption() {
        //GIVEN
        Long id = 1L;
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> activitiesService.findActivitiesById(id));
    }

    @Test
    void findActivitiesByName_OK() throws PrivilegesException {
        //GIVEN
        String name = "Activitie test";
        Activities activitie = new Activities();
        activitie.setId(3L);
        activitie.setName("Activitie test");
        Optional<Activities> responseExpected = Optional.of(activitie);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(activitiesRepository.findByName(name)).thenReturn(responseExpected);
        //When
        Optional<Activities> result = activitiesService.findActivitiesByName(name);
        //Then
        Assertions.assertEquals(responseExpected, result);
    }

    @Test
    void findActivitiesByName_privilegeExeption() {
        //GIVEN
        String name = "Test 1";
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> activitiesService.findActivitiesByName(name));
    }

    @Test
    void saveActivities_OK() throws ValidationServiceException, PrivilegesException {
        //GIVEN
        Activities activitie = new Activities();
        activitie.setName("Activitie test");
        Activities responseExpected = activitie;
        activitie.setId(1L);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(activitiesRepository.save(activitie)).thenReturn(responseExpected);
        //When
        Activities result = activitiesService.saveActivities(activitie);
        //then
        Assertions.assertEquals(responseExpected, result);
    }
    @Test
    void saveActivities_privilegeExeption() {
        //GIVEN
        Activities activitie = new Activities();
        activitie.setName("Activitie test");
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> activitiesService.saveActivities(activitie));
    }
    @Test
    void saveActivities_validationServiceException() {
        //GIVEN
        Activities activitie = null;
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        //when/then
        Assertions.assertThrows(ValidationServiceException.class, () -> activitiesService.saveActivities(activitie));
    }

    @Test
    void deleteActivities_OK() throws PrivilegesException {
        //GIVEN
        Long id = 1L;
        Activities activitie = new Activities();
        activitie.setId(3L);
        activitie.setName("Activitie test");
        String expectedResponse = "Activitat esborrada correctament.";
        Optional<Activities> activitiesOptional = Optional.of(activitie);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(activitiesRepository.findById(id)).thenReturn(activitiesOptional);
        Mockito.doNothing().when(activitiesRepository).deleteById(id);
        //when
        String response = activitiesService.deleteActivities(id);
        //then
        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    void deleteActivities_NOT_FOUNND() throws PrivilegesException {
        //GIVEN
        Long id = 1L;
        String expectedResponse = "Error. La activitat no existeix!";
        Optional<Activities> activitiesOptional = Optional.empty();
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(activitiesRepository.findById(id)).thenReturn(activitiesOptional);
        Mockito.doNothing().when(activitiesRepository).deleteById(id);
        //when
        String response = activitiesService.deleteActivities(id);
        //then
        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    void deleteActivities_privilegeExeption() {
        //GIVEN
        Long id = 1L;
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> activitiesService.deleteActivities(id));
    }

    @Test
    void updateActivities_OK() throws ValidationServiceException, PrivilegesException {
        //GIVEN
        Activities expectedResponse = new Activities();
        expectedResponse.setId(1L);
        expectedResponse.setName("Activitie test");
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(activitiesRepository.findById(1L)).thenReturn(Optional.of(expectedResponse));
        Mockito.when(activitiesRepository.findByNomExcludingId("Activitie test",1L)).thenReturn(Optional.empty());
        Mockito.when(activitiesRepository.save(any(Activities.class))).thenReturn(expectedResponse);
        //when
        Activities response = activitiesService.updateActivities(expectedResponse);
        //then
        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    void updateActivities_privilegeExeption() {
        //GIVEN
        Activities activitiesUpdate = new Activities();
        activitiesUpdate.setId(1L);
        activitiesUpdate.setName("Activitie test");
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> activitiesService.updateActivities(activitiesUpdate));
    }

    @Test
    void updateActivities_validationServiceException() {
        //GIVEN
        Activities activitiesNotFound = new Activities();
        activitiesNotFound.setId(1L);
        activitiesNotFound.setName("Activitie 1");
        Mockito.when(activitiesRepository.findById(1L)).thenReturn(Optional.empty());
        Activities activitiesNotUnique = new Activities();
        activitiesNotUnique.setId(2L);
        activitiesNotUnique.setName("Activitie 2");
        Mockito.when(activitiesRepository.findById(2L)).thenReturn(Optional.of(activitiesNotUnique));
        Mockito.when(activitiesRepository.findByNomExcludingId("Activitie 2",2L)).thenReturn(Optional.of(activitiesNotUnique));
        Activities activitiesIdNull = new Activities();
        activitiesIdNull.setName("Activitie 3");
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        //when/then
        Assertions.assertThrows(ValidationServiceException.class, () -> activitiesService.updateActivities(activitiesNotFound));
        Assertions.assertThrows(ValidationServiceException.class, () -> activitiesService.updateActivities(activitiesNotUnique));
        Assertions.assertThrows(ValidationServiceException.class, () -> activitiesService.updateActivities(activitiesIdNull));
    }

    private List buildTestActivitiesList(Long size) {//Clase que genera entitats, en aquest cas activities

        List<Activities> activities = new ArrayList<>();
        for(Long i=1L; i<= size; i++) {
            Activities activitie = new Activities();
            activitie.setId(i);
            activitie.setName("Test activitie "+i);
            activities.add(activitie);
        }
        return activities;
    }
}
