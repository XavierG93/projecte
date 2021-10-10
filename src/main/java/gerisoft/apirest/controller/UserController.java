/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.controller;

import gerisoft.apirest.dto.PaginateResponse;
import gerisoft.apirest.dto.UserDTO;
import gerisoft.apirest.entity.User;
import org.springframework.data.domain.Pageable;
import java.util.Map;

/**
 *
 * @author Gurrea
 */
public interface UserController {
    /**
     * Repetim el fet a UserService, un generic dels metodes que el controlador
     * fará servir
     * canviem el tipus de getUsers per a incloure les característiques de paginateresponse
     */
    PaginateResponse getUsers(Map<String, String> params, Pageable page);

    UserDTO findUserById(Long id);

    UserDTO saveUser(User userNewDTO);

    void deleteUser(Long id);

    UserDTO updateUser(UserDTO userDTO);
    
    
}
