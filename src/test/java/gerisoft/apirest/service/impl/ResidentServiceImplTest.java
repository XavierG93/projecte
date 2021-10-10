package gerisoft.apirest.service.impl;

import gerisoft.apirest.SimpleBaseTestCase;
import gerisoft.apirest.dto.ActivitiesResidentDTO;
import gerisoft.apirest.dto.AttentionsResidentDTO;
import gerisoft.apirest.dto.PaginateResponse;
import gerisoft.apirest.dto.ResidentsDTO;
import gerisoft.apirest.entity.*;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.repository.ActivitiesResidentsRepository;
import gerisoft.apirest.repository.AttentionsResidentsRepository;
import gerisoft.apirest.repository.ResidentsRepository;
import gerisoft.apirest.service.ActivitiesService;
import gerisoft.apirest.service.AttentionsService;
import gerisoft.apirest.service.UserService;
import gerisoft.apirest.utils.PrivilegesChecker;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.*;

import static gerisoft.apirest.utils.ConvertToDTO.convert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class ResidentServiceImplTest extends SimpleBaseTestCase {

    @Mock//En aquest cas espicifquem els generics introduits en ResidentsService
    ResidentsRepository residentsRepository;
    @Mock
    ActivitiesResidentsRepository activitiesResidentsRepository;
    @Mock
    AttentionsResidentsRepository attentionsResidentsRepository;
    @Mock
    private PrivilegesChecker privilegesChecker;
    @Mock
    private UserService userService;
    @Mock
    private ActivitiesService activitiesService;
    @Mock
    private AttentionsService attentionsService;
    @InjectMocks
    private ResidentsServiceImpl residentService;

    @Test
    void findResidents_OK() throws PrivilegesException {
        //GIVEN
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        List<Residents> residents = buildTestResidentsList(10L);
        Page<Residents> expectedPage = new PageImpl(residents, page, 10L);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(residentsRepository.findAll(any(),eq(page))).thenReturn(expectedPage);
        //when
        Page<Residents> result = residentService.findAllResidents(params, page);
        //then
        Assertions.assertEquals(expectedPage, result);
    }
    @Test
    void findAllResidents_privilegeExeption() {
        //GIVEN
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> residentService.findAllResidents(params, page));
    }

    @Test
    void findResidentsById_OK() throws PrivilegesException, ValidationServiceException {
        //GIVEN
        Long id = 1L;
        Residents residents = buildTestResidentsList(1L).get(0);
        Optional<Residents> responseExpected = Optional.of(residents);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(residentsRepository.findById(id)).thenReturn(responseExpected);
        //When
        Optional<Residents> result = residentService.findResidentsById(id);
        //Then
        Assertions.assertEquals(responseExpected, result);
    }

    @Test
    void findResidentsById_privilegeExeption() {
        //GIVEN
        Long id = 1L;
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> residentService.findResidentsById(id));
    }

    @Test
    void findResidentsById_ValidationServiceException() {
        //GIVEN
        //when/then
        Assertions.assertThrows(ValidationServiceException.class, () -> residentService.findResidentsById(null));
    }

    @Test
    void saveResidents_OK() throws ValidationServiceException, PrivilegesException {
        //GVEN
        Residents residentExpected = buildTestResidentsList(1L).get(0);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(residentsRepository.findByDni(any())).thenReturn(Optional.empty());//Retorna que es buit, per tant no hi ha excepcio
        Mockito.when(residentsRepository.save(any(Residents.class))).thenReturn(residentExpected);
        //GIVEN
        Residents result = residentService.saveResidents(residentExpected);
        //then
        Assertions.assertEquals(residentExpected, result);
    }

    @Test
    void saveResidents_privilegeExeption() {
        //GIVEN
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> residentService.saveResidents(new Residents()));
    }

    @Test
    void saveResidents_validationServiceException() {//Comprovem quan falla dintre del save de Residents
        //GIVEN
        Residents residentDuplicated = buildTestResidentsList(1L).get(0);
        Mockito.when(residentsRepository.findByDni(residentDuplicated.getDni())).thenReturn(Optional.of(residentDuplicated));//Li diem que cerqui el mateix resident que hem introduit per a que salti si o si
        //Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        //when/then
        Assertions.assertThrows(ValidationServiceException.class, () -> residentService.saveResidents(residentDuplicated));//Prova per a quan el dni ja es troba a la base de dades
        Assertions.assertThrows(ValidationServiceException.class, () -> residentService.saveResidents(null));//Prova per a quan el resident es null
    }
    @Test
    void deleteResident_OK() throws PrivilegesException {
        //GIVEN
        Long id = 1L;
        Residents residents = buildTestResidentsList(1L).get(0);
        String expectedResponse = "Resident eliminat correctament.";
        Optional<Residents> residentsOptional = Optional.of(residents);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(residentsRepository.findById(id)).thenReturn(residentsOptional);
        Mockito.doNothing().when(residentsRepository).deleteById(id);
        //when
        String response = residentService.deleteResidents(id);
        //then
        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    void deleteResidents_NOT_FOUNND() throws PrivilegesException {
        //GIVEN
        Long id = 1L;
        String expectedResponse = "Error! El resident no existeix!";
        Optional<Residents> residentsOptional = Optional.empty();
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(residentsRepository.findById(id)).thenReturn(residentsOptional);
        Mockito.doNothing().when(residentsRepository).deleteById(id);
        //when
        String response = residentService.deleteResidents(id);
        //then
        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    void deleteResidents_privilegeExeption() {
        //GIVEN
        Long id = 1L;
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> residentService.deleteResidents(id));
    }

    @Test
    void updateResidents_OK() throws ValidationServiceException, PrivilegesException {
        //GIVEN
        Residents expectedResponse = buildTestResidentsList(1L).get(0);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(residentsRepository.findById(1L)).thenReturn(Optional.of(expectedResponse));
        Mockito.when(residentsRepository.findByCifExcludingId(expectedResponse.getDni(),1L)).thenReturn(Optional.empty());
        Mockito.when(residentsRepository.save(any(Residents.class))).thenReturn(expectedResponse);
        //when
        Residents response = residentService.updateResidents(expectedResponse);
        //then
        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    void updateResidents_privilegeExeption() {
        //GIVEN
        Residents residents = buildTestResidentsList(1L).get(0);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> residentService.updateResidents(residents));
    }

    @Test
    void updateResidents_validationServiceException() {
        //GIVEN
        List<Residents> residents = buildTestResidentsList(3L);
        Residents residentsNotFound = residents.get(0);
        Mockito.when(residentsRepository.findById(residentsNotFound.getId())).thenReturn(Optional.empty());
        Residents residentsNotUnique =residents.get(1);
        Mockito.when(residentsRepository.findById(residentsNotUnique.getId())).thenReturn(Optional.of(residentsNotUnique));
        Mockito.when(residentsRepository.findByCifExcludingId(residentsNotUnique.getDni(),residentsNotUnique.getId())).thenReturn(Optional.of(residentsNotUnique));
        Residents residentIdNull = residents.get(2);
        residentIdNull.setId(null);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        //when/then
        Assertions.assertThrows(ValidationServiceException.class, () -> residentService.updateResidents(residentsNotFound));
        Assertions.assertThrows(ValidationServiceException.class, () -> residentService.updateResidents(residentsNotUnique));
        Assertions.assertThrows(ValidationServiceException.class, () -> residentService.updateResidents(residentIdNull));
    }

    @Test
    void findResidentsByDNI_OK() throws ValidationServiceException, PrivilegesException {
        //GIVEN
        Residents residents = buildTestResidentsList(1L).get(0);
        String dniExpected = residents.getDni();
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(residentsRepository.findByDni(dniExpected)).thenReturn(Optional.of(residents));
        //WHEN
        Residents result = residentService.findResidentsByDNI(dniExpected).get();
        //then
        Assertions.assertEquals(residents, result);
    }
    @Test
    void findResidentsByDNI_privilegeExeption() {
        //GIVEN
        Residents residents = buildTestResidentsList(1L).get(0);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> residentService.findResidentsByDNI(residents.getDni()));
    }
    @Test
    void findActivitiesByResident_OK() throws PrivilegesException {
        //GIVEN
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        List<ActivitiesResidents> aResidents = buildTestActivitiesResidentsList(10L);
        Page<ActivitiesResidents> expectedPage = new PageImpl(aResidents, page, 10L);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(activitiesResidentsRepository.findAll(any(),eq(page))).thenReturn(expectedPage);
        //when
        PaginateResponse result = residentService.findActivitiesByResident(1L, params, page);
        //then
        Assertions.assertEquals(Long.valueOf(expectedPage.getTotalElements()), Long.valueOf(result.getResults().size()));
    }
    @Test
    void findActivitiesByResident_privilegeExeption() {
        //GIVEN
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> residentService.findActivitiesByResident(1L, params, page));
    }

    @Test
    void saveActivitiesResidents_privilegeExeption() {
        //GIVEN
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> residentService.saveActivitiesResidents(new ActivitiesResidentDTO()));
    }

    @Test
    void saveActivitiesResidents_NotFoundException() throws PrivilegesException, ValidationServiceException {
        //GIVEN
        ActivitiesResidentDTO aResidentsNew = convert(buildTestActivitiesResidentsList(1L).get(0));
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        Mockito.when(activitiesService.findActivitiesById(aResidentsNew.getActivitieId())).thenReturn(Optional.empty());
        Mockito.when(userService.findUserById(any())).thenReturn(Optional.empty());
        Mockito.when(residentsRepository.findById(any())).thenReturn(Optional.empty());
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        //when/then
        Assertions.assertThrows(NotFoundException.class, () -> residentService.saveActivitiesResidents(aResidentsNew));
    }

    @Test
    void saveActivitiesResidents_OK() throws PrivilegesException, ValidationServiceException, NotFoundException {
        //GIVEN
        ActivitiesResidents activitiesResidents = buildTestActivitiesResidentsList(1L).get(0);
        ActivitiesResidentDTO aResidentsNew = convert(activitiesResidents);
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        Mockito.when(activitiesService.findActivitiesById(aResidentsNew.getActivitieId())).thenReturn(Optional.of(new Activities()));
        Mockito.when(userService.findUserById(any())).thenReturn(Optional.of(new User()));
        Mockito.when(residentsRepository.findById(any())).thenReturn(Optional.of(new Residents()));
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(activitiesResidentsRepository.save(any(ActivitiesResidents.class))).thenReturn(activitiesResidents);
        //when
        ActivitiesResidentDTO result = residentService.saveActivitiesResidents(aResidentsNew);
        // then
        Assertions.assertEquals(aResidentsNew.getId(), result.getId());
    }

    @Test
    void updateActivitiesResidents_OK() throws PrivilegesException, ValidationServiceException, NotFoundException {
        //GIVEN
        ActivitiesResidents activitiesResidents = buildTestActivitiesResidentsList(1L).get(0);
        ActivitiesResidentDTO aResidentsNew = convert(activitiesResidents);
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        Mockito.when(activitiesService.findActivitiesById(aResidentsNew.getActivitieId())).thenReturn(Optional.of(new Activities()));
        Mockito.when(userService.findUserById(any())).thenReturn(Optional.of(new User()));
        Mockito.when(residentsRepository.findById(any())).thenReturn(Optional.of(new Residents()));
        Mockito.when(activitiesResidentsRepository.findById(any())).thenReturn(Optional.of(new ActivitiesResidents()));
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(activitiesResidentsRepository.save(any(ActivitiesResidents.class))).thenReturn(activitiesResidents);
        //when
        ActivitiesResidentDTO result = residentService.updateActivitiesResidents(aResidentsNew);
        // then
        Assertions.assertEquals(aResidentsNew.getId(), result.getId());
    }

    @Test
    void updateActivitiesResidents_privilegeExeption() {
        //GIVEN
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> residentService.updateActivitiesResidents(new ActivitiesResidentDTO()));
    }

    @Test
    void updateActivitiesResidents_NotFoundException() throws PrivilegesException, ValidationServiceException {
        //GIVEN
        ActivitiesResidents aActivitiesRecident= buildTestActivitiesResidentsList(1L).get(0);
        ActivitiesResidentDTO aResidentsNotFound = convert(buildTestActivitiesResidentsList(2L).get(1));
        ActivitiesResidentDTO aResidentsNew = convert(aActivitiesRecident);
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        Mockito.when(activitiesService.findActivitiesById(aResidentsNew.getActivitieId())).thenReturn(Optional.empty());
        Mockito.when(userService.findUserById(any())).thenReturn(Optional.empty());
        Mockito.when(residentsRepository.findById(any())).thenReturn(Optional.empty());
        Mockito.when(activitiesResidentsRepository.findById(aResidentsNew.getId())).thenReturn(Optional.of(aActivitiesRecident));
        Mockito.when(activitiesResidentsRepository.findById(2L)).thenReturn(Optional.empty());

        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        //when/then
        Assertions.assertThrows(NotFoundException.class, () -> residentService.updateActivitiesResidents(aResidentsNew));
        Assertions.assertThrows(NotFoundException.class, () -> residentService.updateActivitiesResidents(aResidentsNotFound));
    }

    @Test
    void deleteActivitiesResidents_privilegeExeption() {
        //GIVEN
        Long id = 1L;
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> residentService.deleteActivitiesResidents(id, id));
    }

    @Test
    void deleteActivitiesResidents_OK() throws PrivilegesException {
        //GIVEN
        Long id = 1L;
        ActivitiesResidents residents = buildTestActivitiesResidentsList(1L).get(0);
        Optional<ActivitiesResidents> residentsOptional = Optional.of(residents);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(activitiesResidentsRepository.findByIdAndResident_Id(id, id)).thenReturn(residentsOptional);
        Mockito.doNothing().when(activitiesResidentsRepository).deleteById(id);
        //when
        residentService.deleteActivitiesResidents(id, id);
    }

    @Test
    void findAttentionsByResident_OK() throws PrivilegesException {
        //GIVEN
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        List<AttentionsResidents> aResidents = buildTestAttentionsResidentsList(10L);
        Page<AttentionsResidents> expectedPage = new PageImpl(aResidents, page, 10L);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(attentionsResidentsRepository.findAll(any(),eq(page))).thenReturn(expectedPage);
        //when
        PaginateResponse result = residentService.findAttentionsByResident(1L, params, page);
        //then
        Assertions.assertEquals(Long.valueOf(expectedPage.getTotalElements()), Long.valueOf(result.getResults().size()));
    }
    @Test
    void findAttentionsByResident_privilegeExeption() {
        //GIVEN
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> residentService.findAttentionsByResident(1L, params, page));
    }

    @Test
    void saveAttentionsResidents_privilegeExeption() {
        //GIVEN
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> residentService.saveAttentionsResidents(new AttentionsResidentDTO()));
    }

    @Test
    void saveAttentionsResidents_NotFoundException() throws PrivilegesException, ValidationServiceException {
        //GIVEN
        AttentionsResidentDTO aResidentsNew = convert(buildTestAttentionsResidentsList(1L).get(0));
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        Mockito.when(attentionsService.findAttentionsById(aResidentsNew.getAttentionId())).thenReturn(Optional.empty());
        Mockito.when(userService.findUserById(any())).thenReturn(Optional.empty());
        Mockito.when(residentsRepository.findById(any())).thenReturn(Optional.empty());
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        //when/then
        Assertions.assertThrows(NotFoundException.class, () -> residentService.saveAttentionsResidents(aResidentsNew));
    }

    @Test
    void saveAttentionsResidents_OK() throws PrivilegesException, ValidationServiceException, NotFoundException {
        //GIVEN
        AttentionsResidents attentionsResidents = buildTestAttentionsResidentsList(1L).get(0);
        AttentionsResidentDTO aResidentsNew = convert(attentionsResidents);
        Mockito.when(attentionsService.findAttentionsById(aResidentsNew.getAttentionId())).thenReturn(Optional.of(new Attentions()));
        Mockito.when(userService.findUserById(any())).thenReturn(Optional.of(new User()));
        Mockito.when(residentsRepository.findById(any())).thenReturn(Optional.of(new Residents()));
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(attentionsResidentsRepository.save(any(AttentionsResidents.class))).thenReturn(attentionsResidents);
        //when
        AttentionsResidentDTO result = residentService.saveAttentionsResidents(aResidentsNew);
        // then
        Assertions.assertEquals(aResidentsNew.getId(), result.getId());
    }

    @Test
    void updateAttentionsResidents_OK() throws PrivilegesException, ValidationServiceException, NotFoundException {
        //GIVEN
        AttentionsResidents attentionsResidents = buildTestAttentionsResidentsList(1L).get(0);
        AttentionsResidentDTO aResidentsNew = convert(attentionsResidents);
        Mockito.when(attentionsService.findAttentionsById(aResidentsNew.getAttentionId())).thenReturn(Optional.of(new Attentions()));
        Mockito.when(userService.findUserById(any())).thenReturn(Optional.of(new User()));
        Mockito.when(residentsRepository.findById(any())).thenReturn(Optional.of(new Residents()));
        Mockito.when(attentionsResidentsRepository.findById(any())).thenReturn(Optional.of(new AttentionsResidents()));
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(attentionsResidentsRepository.save(any(AttentionsResidents.class))).thenReturn(attentionsResidents);
        //when
        AttentionsResidentDTO result = residentService.updateAttentionsResidents(aResidentsNew);
        // then
        Assertions.assertEquals(aResidentsNew.getId(), result.getId());
    }

    @Test
    void updateAttentionsResidents_privilegeExeption() {
        //GIVEN
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> residentService.updateAttentionsResidents(new AttentionsResidentDTO()));
    }

    @Test
    void updateAttentionsResidents_NotFoundException() throws PrivilegesException, ValidationServiceException {
        //GIVEN
        AttentionsResidents aAttentionsRecident= buildTestAttentionsResidentsList(1L).get(0);
        AttentionsResidentDTO aResidentsNotFound = convert(buildTestAttentionsResidentsList(2L).get(1));
        AttentionsResidentDTO aResidentsNew = convert(aAttentionsRecident);
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        Mockito.when(attentionsService.findAttentionsById(aResidentsNew.getAttentionId())).thenReturn(Optional.empty());
        Mockito.when(userService.findUserById(any())).thenReturn(Optional.empty());
        Mockito.when(residentsRepository.findById(any())).thenReturn(Optional.empty());
        Mockito.when(attentionsResidentsRepository.findById(aResidentsNew.getId())).thenReturn(Optional.of(aAttentionsRecident));
        Mockito.when(attentionsResidentsRepository.findById(2L)).thenReturn(Optional.empty());

        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        //when/then
        Assertions.assertThrows(NotFoundException.class, () -> residentService.updateAttentionsResidents(aResidentsNew));
        Assertions.assertThrows(NotFoundException.class, () -> residentService.updateAttentionsResidents(aResidentsNotFound));
    }

    @Test
    void deleteAttentionsResidents_privilegeExeption() {
        //GIVEN
        Long id = 1L;
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> residentService.deleteAttentionsResidents(id, id));
    }

    @Test
    void deleteAttentionsResidents_OK() throws PrivilegesException {
        //GIVEN
        Long id = 1L;
        AttentionsResidents residents = buildTestAttentionsResidentsList(1L).get(0);
        Optional<AttentionsResidents> residentsOptional = Optional.of(residents);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(attentionsResidentsRepository.findByIdAndResident_Id(id, id)).thenReturn(residentsOptional);
        Mockito.doNothing().when(attentionsResidentsRepository).deleteById(id);
        //when
        residentService.deleteAttentionsResidents(id, id);
    }

    private List<ActivitiesResidents> buildTestActivitiesResidentsList(Long size) {
        List<ActivitiesResidents> aResidents = new ArrayList<>();
        for(Long i=1L; i<= size; i++) {
            ActivitiesResidents aResident = new ActivitiesResidents();
            aResident.setId(i);
            aResident.setResident(buildTestResidentsList(1L).get(0));
            aResident.setUser(new User());
            aResident.setDataActiviti(new Date());
            aResident.setObservacion("Observacion "+i);
            aResident.setActivitie(new Activities());
            aResidents.add(aResident);
        }
        return aResidents;
    }

    private List<AttentionsResidents> buildTestAttentionsResidentsList(Long size) {
        List<AttentionsResidents> aResidents = new ArrayList<>();
        for(Long i=1L; i<= size; i++) {
            AttentionsResidents aResident = new AttentionsResidents();
            aResident.setId(i);
            aResident.setResident(buildTestResidentsList(1L).get(0));
            aResident.setUser(new User());
            aResident.setDataAtencio((new Date()));
            aResident.setObservacion("Observacion "+i);
            aResident.setAttention(new Attentions());
            aResident.setComprovat(false);
            aResidents.add(aResident);
        }
        return aResidents;
    }

    private List<Residents> buildTestResidentsList(Long size) {

        List<Residents> residents = new ArrayList<>();
        for(Long i=1L; i<= size; i++) {
            Residents resident = new Residents();
            resident.setId(i);
            resident.setNom("nom resident "+i);
            resident.setCognom1("cognom resident "+i);
            resident.setCognom2("cognom resident "+i);
            resident.setDni("95000"+i);
            resident.setDataIngres(new Date());
            resident.setDataNaixement(new Date(i*100));
            residents.add(resident);
        }
        return residents;
    }
}
