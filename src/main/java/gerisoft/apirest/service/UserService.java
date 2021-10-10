/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.service;

import gerisoft.apirest.dto.PaginateResponse;
import gerisoft.apirest.entity.User;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.exception.ValidationServiceException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Gurrea
 */
@Service
public interface UserService {

    /**
     * Classe on implementarem generics dels metodes que farem servir amb la
     * classe User
     * Canviem el tipus a paginate per a poder donar format als resultats
     */
    PaginateResponse findAllUsers(Map<String, String> params, Pageable page) throws PrivilegesException;

    Optional<User> findByUsername(String username) throws PrivilegesException, ValidationServiceException;

    Optional<User> findByUsername(String username, boolean saltarSeguridad) throws PrivilegesException, ValidationServiceException;

    Optional<User> findUserById(Long id) throws PrivilegesException, ValidationServiceException;

    Optional<User> findUserByPhone(String phone) throws PrivilegesException, ValidationServiceException;

    Optional<User> findUserByEmail(String email) throws PrivilegesException, ValidationServiceException;

    User saveUser(User userNew) throws PrivilegesException, ValidationServiceException;

    void deleteUser(Long id) throws PrivilegesException, ValidationServiceException;

    User updateUser(User userUpdate) throws PrivilegesException, ValidationServiceException;
}
