/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.controller.impl;

import gerisoft.apirest.controller.RoleController;
import gerisoft.apirest.dto.RoleDTO;
import gerisoft.apirest.entity.Role;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.service.RoleService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static gerisoft.apirest.utils.ConvertToDTO.convert;
import static gerisoft.apirest.utils.ConvertToEntity.convertToEntity;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Gurrea
 */
@RestController
@RequestMapping("/role")
public class RoleControllerImpl implements RoleController {

    @Autowired
    private RoleService roleService;

    //Serveix per a fer la crida al servidor localhost:8080/paraulaClau
    // http://localhost:8080/role (GET)
    @GetMapping
    @Override
    public List<RoleDTO> getRoles() {
        try {
            List<Role> roles = roleService.findAllRoles();
            List<RoleDTO> rolesDTO = new ArrayList<RoleDTO>();
            if (roles != null) {
                for (Role r : roles) {
                    rolesDTO.add(convert(r));
                }
            }

            return rolesDTO;
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Override
    public RoleDTO findRoleById(@PathVariable Long id) {
        Optional<Role> role = null;
        try {
            role = roleService.findRoleById(id);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        }
        return convert(role.get());
    }

    @PostMapping
    @Override
    public RoleDTO saveRole(@RequestBody Role roleNew) {
        Role role;
        try {
            role = roleService.saveRole(roleNew);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        }
        return convert(role);
    }
// http://localhost:8080/role/delete/1 (GET)

    @DeleteMapping(value = "/{id}")
    @Override
    public void deleteRole(@PathVariable("id") Long id) {
        try {
            roleService.deleteRole(id);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        }
    }

    // http://localhost:8080/role/update (PATCH)
    @PutMapping("/{id}")
    @Override
    public RoleDTO updateRole(RoleDTO roleDTO) {
        Role role = convertToEntity(roleDTO);
        Role roleUpdated = null;
        try {
            roleUpdated = roleService.updateRole(role);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        }
        roleDTO = convert(roleUpdated);
        return roleDTO;
    }

    /*// http://localhost:8080/test (GET)
    @Override
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
    public String test() {
        return "Test done";
    }*/
}
