package gerisoft.apirest.service.impl;

import gerisoft.apirest.SimpleBaseTestCase;
import gerisoft.apirest.entity.Role;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class RoleServiceImplTest extends SimpleBaseTestCase {

    @Mock
    RoleRepository roleRepository;
    @InjectMocks
    RoleServiceImpl roleService;

    @Test
    void findAllRoles_OK() {
        //GIVEN
        List<Role> roles = buildTestRoleList(10L);
        Mockito.when(roleRepository.findAll()).thenReturn(roles);
        //when
        List<Role> result = roleService.findAllRoles();
        //then
        Assertions.assertEquals(roles, result);
    }

    @Test
    void findRoleById_OK() {
        //GIVEN
        Role roleExpected = new Role();
        roleExpected.setId(1L);
        roleExpected.setName("CREATE_USER");
        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(roleExpected));
        //when
        Optional<Role> result = roleService.findRoleById(1L);
        //then
        Assertions.assertEquals(roleExpected, result.get());
    }

    @Test
    void saveRole_OK() throws ValidationServiceException {
        //GIVEN
        Role roleExpected = new Role();
        roleExpected.setId(1L);
        roleExpected.setName("CREATE_USER");
        Mockito.when(roleRepository.save(roleExpected)).thenReturn(roleExpected);
        //when
        Role result = roleService.saveRole(roleExpected);
        //then
        Assertions.assertEquals(roleExpected, result);
    }

    @Test
    void saveRole_ValidationServiceException() {
        //GIVEN
        //when-then
        Assertions.assertThrows(ValidationServiceException.class, () -> roleService.saveRole(null));
    }

    @Test
    void deleteRole_OK() {
        //GIVEN
        String resultExpected = "Rol eliminat correctament.";
        Role roleExpected = new Role();
        roleExpected.setId(1L);
        roleExpected.setName("ADMIN");
        Mockito.doNothing().when(roleRepository).delete(roleExpected);
        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(roleExpected));
        //WHEN
        String result = roleService.deleteRole(1L);
        //Then
        Assertions.assertEquals(resultExpected, result);
    }

    @Test
    void deleteRole_notFound() {
        //GIVEN
        String resultExpected = "Error! El rol introduit no existeix!";
        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        //WHEN
        String result = roleService.deleteRole(1L);
        //Then
        Assertions.assertEquals(resultExpected, result);
    }

    @Test
    void updateRole_OK() throws ValidationServiceException {
        //GIVEN
        Role roleExpected = new Role();
        roleExpected.setId(1L);
        roleExpected.setName("CREATE_USER");
        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(roleExpected));
        Mockito.when(roleRepository.findByNomExcludingId("CREATE_USER", 1L)).thenReturn(Optional.empty());
        Mockito.when(roleRepository.save(any(Role.class))).thenReturn(roleExpected);
        //WHEN
        Role result = roleService.updateRole(roleExpected);
        //then
        Assertions.assertEquals( roleExpected, result);
    }

    @Test
    void updateRole_validationServiceException() {
        //GIVEN
        Role roleNotFound = new Role();
        roleNotFound.setId(1L);
        roleNotFound.setName("Role 1");
        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        Role roleNotUnique = new Role();
        roleNotUnique.setId(2L);
        roleNotUnique.setName("Role 2");
        Mockito.when(roleRepository.findById(2L)).thenReturn(Optional.of(roleNotUnique));
        Mockito.when(roleRepository.findByNomExcludingId("Role 2",2L)).thenReturn(Optional.of(roleNotUnique));
        Role roleIdNull = new Role();
        roleIdNull.setName("Role 3");
        //when/then
        Assertions.assertThrows(ValidationServiceException.class, () -> roleService.updateRole(roleNotFound));
        Assertions.assertThrows(ValidationServiceException.class, () -> roleService.updateRole(roleNotUnique));
        Assertions.assertThrows(ValidationServiceException.class, () -> roleService.updateRole(roleIdNull));
    }

    private List buildTestRoleList(Long size) {

        List<Role> roles = new ArrayList<>();
        for(Long i=1L; i<= size; i++) {
            Role role = new Role();
            role.setId(i);
            role.setName("Test Role "+i);
            roles.add(role);
        }
        return roles;
    }

}
