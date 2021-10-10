/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.service.impl;

import gerisoft.apirest.entity.Role;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.repository.RoleRepository;
import gerisoft.apirest.service.RoleService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gurrea Classe on tractem les dades introduides i establim un control
 * sobre si son presents o no a la bbdd i si son correctes.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired//En aquest cas espicifquem els generics introduits en UserService
    RoleRepository roleRepository;

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findRoleById(Long id) {
        //Cerquem usuari per la seva clau id
        Optional<Role> role = roleRepository.findById(id);
        return role;
    }

    @Override
    public Role saveRole(Role roleNew) throws ValidationServiceException {

        if (roleNew == null) {
            throw new ValidationServiceException("El rol introduit es inexistent.");
        }
        

        return roleRepository.save(roleNew);
    }

    @Override
    public String deleteRole(Long id) {
        if (roleRepository.findById(id).isPresent()) {
            //Si de veritat es a la bbdd l'esborra
            roleRepository.deleteById(id);
            return "Rol eliminat correctament.";
        }
        return "Error! El rol introduit no existeix!";
    }

    @Override
    public Role updateRole(Role roleUpdated) throws ValidationServiceException {
        Long num = roleUpdated.getId();
        Role roleToUpdate = new Role(roleUpdated.getName());
        if (num == null) {
            throw new ValidationServiceException("L'objecte es null.");
        }
        if (!roleRepository.findById(num).isPresent()) {
            throw new ValidationServiceException("Aquest Id no existeix.");
        }
        if (roleRepository.findByNomExcludingId(roleUpdated.getName(), roleUpdated.getId()).isPresent()) {
            throw new ValidationServiceException("Aquest nom ja és registrat.");
        }
        roleToUpdate.setId(roleUpdated.getId());
        roleToUpdate.setName(roleUpdated.getName());
        roleToUpdate.setPriviliege(roleUpdated.getPrivilieges());

        return roleRepository.save(roleToUpdate);

    }
}
