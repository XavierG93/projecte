package gerisoft.apirest.service.impl;

import gerisoft.apirest.SimpleBaseTestCase;
import gerisoft.apirest.entity.Priviliege;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.repository.PriviliegeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class PriviliegeServiceIImplTest extends SimpleBaseTestCase {

    @Mock
    PriviliegeRepository priviliegeRepository;

    @InjectMocks
    PriviliegeServiceImpl priviliegeService;

    @Test
    void findAllPrivilieges_OK() {
        //GIVEN
        List<Priviliege> privilieges = buildTestPriviliegeList(10L);
        Mockito.when(priviliegeRepository.findAll()).thenReturn(privilieges);
        //when
        List<Priviliege> result = priviliegeService.findAllPrivilieges();
        //then
        Assertions.assertEquals(privilieges, result);
    }

    @Test
    void findPriviliegeById_OK() {
        //GIVEN
        Long priviliegeId = 1L;
        Priviliege priviliegeExpected = new Priviliege();
        priviliegeExpected.setId(1L);
        priviliegeExpected.setName("CREATE_USER");
        Mockito.when(priviliegeRepository.findById(1L)).thenReturn(Optional.of(priviliegeExpected));
        //when
        Optional<Priviliege> result = priviliegeService.findPriviliegeById(1L);
        //then
        Assertions.assertEquals(priviliegeExpected, result.get());
    }

    @Test
    void savePriviliege_OK() throws ValidationServiceException {
        //GIVEN
        Priviliege priviliegeExpected = new Priviliege();
        priviliegeExpected.setId(1L);
        priviliegeExpected.setName("CREATE_USER");
        Mockito.when(priviliegeRepository.save(priviliegeExpected)).thenReturn(priviliegeExpected);
        //when
        Priviliege result = priviliegeService.savePriviliege(priviliegeExpected);
        //then
        Assertions.assertEquals(priviliegeExpected, result);
    }

    @Test
    void savePriviliege_ValidationServiceException() {
        //GIVEN
        //when-then
        Assertions.assertThrows(ValidationServiceException.class, () -> priviliegeService.savePriviliege(null));
    }

    @Test
    void deletePriviliege_OK() {
        //GIVEN
        String resultExpected = "Privilegi eliminat correctament.";
        Priviliege priviliegeExpected = new Priviliege();
        priviliegeExpected.setId(1L);
        priviliegeExpected.setName("CREATE_USER");
        Mockito.doNothing().when(priviliegeRepository).delete(priviliegeExpected);
        Mockito.when(priviliegeRepository.findById(1L)).thenReturn(Optional.of(priviliegeExpected));
        //WHEN
        String result = priviliegeService.deletePriviliege(1L);
        //Then
        Assertions.assertEquals(resultExpected, result);
    }

    @Test
    void deletePriviliege_notFound() {
        //GIVEN
        String resultExpected = "Error! El privilegi introduit no existeix!";
        Mockito.when(priviliegeRepository.findById(1L)).thenReturn(Optional.empty());
        //WHEN
        String result = priviliegeService.deletePriviliege(1L);
        //Then
        Assertions.assertEquals(resultExpected, result);
    }

    @Test
    void updatePriviliege_OK() throws ValidationServiceException {
        //GIVEN
        Priviliege priviliegeExpected = new Priviliege();
        priviliegeExpected.setId(1L);
        priviliegeExpected.setName("CREATE_USER");
        Mockito.when(priviliegeRepository.findById(1L)).thenReturn(Optional.of(priviliegeExpected));
        Mockito.when(priviliegeRepository.findByNameExcludingId("CREATE_USER", 1L)).thenReturn(Optional.empty());
        Mockito.when(priviliegeRepository.save(any(Priviliege.class))).thenReturn(priviliegeExpected);
        //WHEN
        Priviliege result = priviliegeService.updatePriviliege(priviliegeExpected);
        //then
        Assertions.assertEquals( priviliegeExpected, result);
    }

    @Test
    void updatePriviliege_validationServiceException() {
        //GIVEN
        Priviliege priviliegeNotFound = new Priviliege();
        priviliegeNotFound.setId(1L);
        priviliegeNotFound.setName("Priviliege 1");
        Mockito.when(priviliegeRepository.findById(1L)).thenReturn(Optional.empty());
        Priviliege priviliegeNotUnique = new Priviliege();
        priviliegeNotUnique.setId(2L);
        priviliegeNotUnique.setName("Priviliege 2");
        Mockito.when(priviliegeRepository.findById(2L)).thenReturn(Optional.of(priviliegeNotUnique));
        Mockito.when(priviliegeRepository.findByNameExcludingId("Priviliege 2",2L)).thenReturn(Optional.of(priviliegeNotUnique));
        Priviliege priviliegeIdNull = new Priviliege();
        priviliegeIdNull.setName("Priviliege 3");
        //when/then
        Assertions.assertThrows(ValidationServiceException.class, () -> priviliegeService.updatePriviliege(priviliegeNotFound));
        Assertions.assertThrows(ValidationServiceException.class, () -> priviliegeService.updatePriviliege(priviliegeNotUnique));
        Assertions.assertThrows(ValidationServiceException.class, () -> priviliegeService.updatePriviliege(priviliegeIdNull));
    }

    private List buildTestPriviliegeList(Long size) {

        List<Priviliege> privlieges = new ArrayList<>();
        for(Long i=1L; i<= size; i++) {
            Priviliege priviliege = new Priviliege();
            priviliege.setId(i);
            priviliege.setName("Test attention "+i);
            privlieges.add(priviliege);
        }
        return privlieges;
    }
}
