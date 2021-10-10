/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.controller.impl;

import gerisoft.apirest.controller.UserController;
import gerisoft.apirest.dto.PaginateResponse;
import gerisoft.apirest.dto.UserDTO;
import gerisoft.apirest.entity.User;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.service.UserService;
import static gerisoft.apirest.utils.ConvertToDTO.convert;
import static gerisoft.apirest.utils.ConvertToEntity.convertToEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Gurrea 
 * Darrera classe on establim on es faran les crides per segons
 * quines funcions del controlador.
 */
@RestController
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

   
    @Autowired
    private UserService userService;

    //Serveix per a fer la crida al servidor localhost:8080/paraulaClau
    // http://localhost:8080/user (GET)
    // @RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json")
    @GetMapping
    @Override
    public PaginateResponse getUsers(@RequestParam Map<String, String> params, Pageable page) {
        try {
            return userService.findAllUsers(params, page);
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    // http://localhost:8080/user (GET)
    @GetMapping("/{id}")
    @Override
    // @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = "application/json")
    public UserDTO findUserById(@PathVariable Long id) {
        User user;
        try {
            user = userService.findUserById(id)
                              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuari no trobat"));
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (PrivilegesException ex) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return convert(user);

    }
    // http://localhost:8080/user/add (POST)

    @PostMapping
    @Override
    // @RequestMapping(value = "/user/add", method = RequestMethod.POST, produces = "application/json")
    public UserDTO saveUser(@RequestBody User user) {
        //User user = convertToEntity(userNewDTO);
        try {
            user = userService.saveUser(user);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
        return convert(user);
    }

    // http://localhost:8080/user/update (PATCH)
    @PutMapping("/{id}")
    @Override
    // @RequestMapping(value = "/user/update", method = RequestMethod.PATCH, produces = "application/json")
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        User user=convertToEntity(userDTO,true);        
        User userUpdated;
        try {
           userUpdated = userService.updateUser(user);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
        userDTO=convert(userUpdated);
        return userDTO;

    }

    // http://localhost:8080/user/delete/1 (GET)
    @DeleteMapping(value =  "/{id}")
    @Override
    // @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET, produces = "application/json")
    public void deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }

    }

}
