/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.controller;

import gerisoft.apirest.dto.RoleDTO;
import gerisoft.apirest.entity.Role;
import java.util.List;

/**
 *
 * @author Gurrea
 */
public interface RoleController {
    /**
     * Repetim el fet a RolService, un generic dels metodes que el controlador
     * fará servir
     * 
     */
     public List<RoleDTO> getRoles();

    public RoleDTO findRoleById(Long id);

    public RoleDTO saveRole(Role roleNew);

    public void deleteRole(Long id);

    public RoleDTO updateRole(RoleDTO roleDTO);
}
