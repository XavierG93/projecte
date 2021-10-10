package gerisoft.apirest.service.impl;

import gerisoft.apirest.SimpleBaseTestCase;
import gerisoft.apirest.entity.Attentions;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.repository.AttentionsRepository;
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

public class AttentionsServiceImplTest extends SimpleBaseTestCase {

    @Mock
    private AttentionsRepository attentionsRepository;
    @Mock
    private PrivilegesChecker privilegesChecker;
    @InjectMocks
    private AttentionsServiceImpl attentionsService;

    @Test
    void findAllAttentions_OK() throws PrivilegesException {
        //GIVEN
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        List<Attentions> attentions = buildTestAttentionList(10L);
        Page<Attentions> expectedPage = new PageImpl(attentions, page, 10L);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(attentionsRepository.findAll(any(),eq(page))).thenReturn(expectedPage);
        //when
        Page<Attentions> result = attentionsService.findAllAttentions(params, page);
        //then
        Assertions.assertEquals(expectedPage, result);
    }

    @Test
    void findAllAttentions_privilegeExeption() {
        //GIVEN
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> attentionsService.findAllAttentions(params, page));
    }

    @Test
    void findAttentionsById_OK() throws PrivilegesException {
        //GIVEN
        Long id = 1L;
        Attentions attentions = new Attentions();
        attentions.setId(id);
        attentions.setName("Activitie test");
        Optional<Attentions> responseExpected = Optional.of(attentions);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(attentionsRepository.findById(id)).thenReturn(responseExpected);
        //When
        Optional<Attentions> result = attentionsService.findAttentionsById(id);
        //Then
        Assertions.assertEquals(responseExpected, result);
    }

    @Test
    void findAttentionById_privilegeExeption() {
        //GIVEN
        Long id = 1L;
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> attentionsService.findAttentionsById(id));
    }

    @Test
    void findAttentionsByName_OK() throws PrivilegesException {
        //GIVEN
        String name = "Attention test";
        Attentions attentions = new Attentions();
        attentions.setId(3L);
        attentions.setName("Attention test");
        Optional<Attentions> responseExpected = Optional.of(attentions);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(attentionsRepository.findByName(name)).thenReturn(responseExpected);
        //When
        Optional<Attentions> result = attentionsService.findAttentionsByName(name);
        //Then
        Assertions.assertEquals(responseExpected, result);
    }

    @Test
    void findAttentionsByName_privilegeExeption() {
        //GIVEN
        String name = "Test 1";
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> attentionsService.findAttentionsByName(name));
    }

    @Test
    void saveAttentions_OK() throws ValidationServiceException, PrivilegesException {
        //GIVEN
        Attentions attention = new Attentions();
        attention.setName("Attenton test");
        Attentions responseExpected = attention;
        attention.setId(1L);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(attentionsRepository.save(attention)).thenReturn(responseExpected);
        //When
        Attentions result = attentionsService.saveAttentions(attention);
        //then
        Assertions.assertEquals(responseExpected, result);
    }
    @Test
    void saveAttentions_privilegeExeption() {
        //GIVEN
        Attentions attention = new Attentions();
        attention.setName("Attention test");
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> attentionsService.saveAttentions(attention));
    }
    @Test
    void saveAttentions_validationServiceException() {
        //GIVEN
        Attentions attention = null;
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        //when/then
        Assertions.assertThrows(ValidationServiceException.class, () -> attentionsService.saveAttentions(attention));
    }

    @Test
    void deleteAttentions_OK() throws PrivilegesException {
        //GIVEN
        Long id = 1L;
        Attentions attention = new Attentions();
        attention.setId(3L);
        attention.setName("Attention test");
        String expectedResponse = "Attention esborrada correctament.";
        Optional<Attentions> attentionOptional = Optional.of(attention);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(attentionsRepository.findById(id)).thenReturn(attentionOptional);
        Mockito.doNothing().when(attentionsRepository).deleteById(id);
        //when
        String response = attentionsService.deleteAttentions(id);
        //then
        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    void deleteAttentions_NOT_FOUNND() throws PrivilegesException {
        //GIVEN
        Long id = 1L;
        String expectedResponse = "Error. La attention no existeix!";
        Optional<Attentions> attentionsOptional = Optional.empty();
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(attentionsRepository.findById(id)).thenReturn(attentionsOptional);
        Mockito.doNothing().when(attentionsRepository).deleteById(id);
        //when
        String response = attentionsService.deleteAttentions(id);
        //then
        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    void deleteAttentions_privilegeExeption() {
        //GIVEN
        Long id = 1L;
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> attentionsService.deleteAttentions(id));
    }

    @Test
    void updateAttentions_OK() throws ValidationServiceException, PrivilegesException {
        //GIVEN
        Attentions expectedResponse = new Attentions();
        expectedResponse.setId(1L);
        expectedResponse.setName("Attention test");
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(attentionsRepository.findById(1L)).thenReturn(Optional.of(expectedResponse));
        Mockito.when(attentionsRepository.findByNomExcludingId("Activitie test",1L)).thenReturn(Optional.empty());
        Mockito.when(attentionsRepository.save(any(Attentions.class))).thenReturn(expectedResponse);
        //when
        Attentions response = attentionsService.updateAttentions(expectedResponse);
        //then
        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    void updateAttentions_privilegeExeption() {
        //GIVEN
        Attentions attentionsUpdate = new Attentions();
        attentionsUpdate.setId(1L);
        attentionsUpdate.setName("Activitie test");
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> attentionsService.updateAttentions(attentionsUpdate));
    }

    @Test
    void updateAttentions_validationServiceException() {
        //GIVEN
        Attentions attentionsNotFound = new Attentions();
        attentionsNotFound.setId(1L);
        attentionsNotFound.setName("Attention 1");
        Mockito.when(attentionsRepository.findById(1L)).thenReturn(Optional.empty());
        Attentions attentionsNotUnique = new Attentions();
        attentionsNotUnique.setId(2L);
        attentionsNotUnique.setName("Attention 2");
        Mockito.when(attentionsRepository.findById(2L)).thenReturn(Optional.of(attentionsNotUnique));
        Mockito.when(attentionsRepository.findByNomExcludingId("Attention 2",2L)).thenReturn(Optional.of(attentionsNotUnique));
        Attentions attentionsIdNull = new Attentions();
        attentionsIdNull.setName("Attention 3");
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        //when/then
        Assertions.assertThrows(ValidationServiceException.class, () -> attentionsService.updateAttentions(attentionsNotFound));
        Assertions.assertThrows(ValidationServiceException.class, () -> attentionsService.updateAttentions(attentionsNotUnique));
        Assertions.assertThrows(ValidationServiceException.class, () -> attentionsService.updateAttentions(attentionsIdNull));
    }

    private List buildTestAttentionList(Long size) {

        List<Attentions> attentions = new ArrayList<>();
        for(Long i=1L; i<= size; i++) {
            Attentions attention = new Attentions();
            attention.setId(i);
            attention.setName("Test attention "+i);
            attentions.add(attention);
        }
        return attentions;
    }
}
