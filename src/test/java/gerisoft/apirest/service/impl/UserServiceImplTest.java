package gerisoft.apirest.service.impl;

import gerisoft.apirest.SimpleBaseTestCase;
import gerisoft.apirest.dto.PaginateResponse;
import gerisoft.apirest.entity.Activities;
import gerisoft.apirest.entity.Residents;
import gerisoft.apirest.entity.Role;
import gerisoft.apirest.entity.User;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.repository.RoleRepository;
import gerisoft.apirest.repository.UserRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class UserServiceImplTest extends SimpleBaseTestCase {

    @Mock//En aquest cas espicifquem els generics introduits en UserService
    UserRepository userRepository;
    @Mock//En aquest cas espicifquem els generics introduits en UserService
    RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PrivilegesChecker privilegesChecker;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findAllUsers_OK() throws PrivilegesException {
        //GIVEN
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        List<User> activities = buildTestUserList(10L);
        Page<User> expectedPage = new PageImpl(activities, page, 10L);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(userRepository.findAll(any(),eq(page))).thenReturn(expectedPage);
        //when
        PaginateResponse result = userService.findAllUsers(params, page);
        //then
        Assertions.assertEquals(Long.valueOf(expectedPage.getTotalElements()), Long.valueOf(result.getResults().size()));
    }

    @Test
    void findAllUsers_privilegeExeption() {
        //GIVEN
        Map<String, String> params = new HashMap<>();
        Pageable page = PageRequest.of(0, 20);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> userService.findAllUsers(params, page));
    }

    @Test
    void findUserById_OK() throws PrivilegesException, ValidationServiceException {
        //GIVEN
        Long id = 1L;
        User user = buildTestUserList(1L).get(0);
        Optional<User> responseExpected = Optional.of(user);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(userRepository.findById(id)).thenReturn(responseExpected);
        //When
        Optional<User> result = userService.findUserById(id);
        //Then
        Assertions.assertEquals(responseExpected, result);
    }

    @Test
    void findUserById_privilegeExeption() {
        //GIVEN
        Long id = 1L;
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> userService.findUserById(id));
    }

    @Test
    void findUsersById_ValidationServiceException() {
        //GIVEN
        //when/then
        Assertions.assertThrows(ValidationServiceException.class, () -> userService.findUserById(null));
    }

    @Test
    void findUserByPhone_OK() throws PrivilegesException, ValidationServiceException {
        //GIVEN
        User user = buildTestUserList(1L).get(0);
        Optional<User> responseExpected = Optional.of(user);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(userRepository.findByTelefon(user.getTelefon())).thenReturn(responseExpected);
        //When
        Optional<User> result = userService.findUserByPhone(user.getTelefon());
        //Then
        Assertions.assertEquals(responseExpected, result);
    }

    @Test
    void findUserByPhone_privilegeExeption() {
        //GIVEN
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> userService.findUserByPhone("11111"));
    }

    @Test
    void findUsersByPhone_ValidationServiceException() {
        //GIVEN
        //when/then
        Assertions.assertThrows(ValidationServiceException.class, () -> userService.findUserByPhone(null));
    }

    @Test
    void findUserByEmail_OK() throws PrivilegesException, ValidationServiceException {
        //GIVEN
        User user = buildTestUserList(1L).get(0);
        Optional<User> responseExpected = Optional.of(user);
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(true);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(responseExpected);
        //When
        Optional<User> result = userService.findUserByEmail(user.getEmail());
        //Then
        Assertions.assertEquals(responseExpected, result);
    }

    @Test
    void findUserByEmail_privilegeExeption() {
        //GIVEN
        Mockito.when(privilegesChecker.hasPrivilege(any(), any())).thenReturn(false);
        //when/then
        Assertions.assertThrows(PrivilegesException.class, () -> userService.findUserByEmail("any@any.com"));
    }

    @Test
    void findUsersByEmail_ValidationServiceException() {
        //GIVEN
        //when/then
        Assertions.assertThrows(ValidationServiceException.class, () -> userService.findUserByEmail(null));
    }

    private List<User> buildTestUserList(Long size) {

        List<User> users = new ArrayList<>();
        for(Long i=1L; i<= size; i++) {
            User user = new User();
            user.setId(i);
            user.setNom("Test nom "+i);
            user.setUsername("username"+i);
            user.setRole(new Role());
            user.setCiutat("any");
            user.setCodiPostal("any");
            user.setCognom1("cognom"+i);
            user.setCognom2("cognom"+i);
            user.setDataNaixement(new Date());
            user.setDireccio("any");
            user.setEmail(i+"a@.edu.es");
            user.setPais("es");
            user.setTelefon("1234567"+i);
            users.add(user);
        }
        return users;
    }
}
