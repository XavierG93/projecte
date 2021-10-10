/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.service;

import gerisoft.apirest.entity.Role;
import gerisoft.apirest.exception.ValidationServiceException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gurrea
 */
@Service
public interface RoleService {

    /**
     * Classe on implementarem generics dels metodes que farem servir amb la
     * classe Role
     */
    public List<Role> findAllRoles()throws ValidationServiceException;

    public Optional<Role> findRoleById(Long id)throws ValidationServiceException;

    public Role saveRole(Role roleNew)throws ValidationServiceException;

    public String deleteRole(Long id)throws ValidationServiceException;

    public Role updateRole(Role roleUpdate)throws ValidationServiceException;
}
