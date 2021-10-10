/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.utils;

import gerisoft.apirest.entity.Priviliege;
import gerisoft.apirest.entity.Role;
import gerisoft.apirest.entity.User;
import gerisoft.apirest.repository.PriviliegeRepository;
import gerisoft.apirest.repository.RoleRepository;
import gerisoft.apirest.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gurrea
 */
@Component
public class MyDatabaseSeeder {
    @Autowired
    private UserRepository usersRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PriviliegeRepository priviliegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Transactional
    public void seedDatabase() {
        this.seedPrivileges();
        this.seedRoles();
        this.seedRolesPrivileges();
        this.seedUsers();
    }

    private void seedPrivileges() {
        Set<Priviliege> privileges = Constants.PRIVILEGES_LIST.stream()
            .map(privilegeName -> new Priviliege(privilegeName))
                .collect(Collectors.toSet());

        for(Priviliege privilege : privileges) {
            Optional<Priviliege> p = priviliegeRepository.findByName(privilege.getName());
            if (!p.isPresent()) {
                priviliegeRepository.save(privilege);
            }
        }
    }
    
    private void seedRoles() {
        Set<Role> roles = Constants.ROLES_LIST.stream()
            .map(roleName -> new Role(roleName))
            .collect(Collectors.toSet());

        for(Role role : roles) {
            Optional<Role> r = roleRepository.findByName(role.getName());
            if (!r.isPresent()) {
                roleRepository.save(role);
            }
        }
    }

    private void seedRolesPrivileges() {

        Set<String> roleNames = Constants.PRIVILEGES_BY_ROLE.keySet();
        for(String roleName : roleNames) {
            List<String> privilegesListOfThisRole = Constants.PRIVILEGES_BY_ROLE.get(roleName);

            Optional<Role> role = roleRepository.findByName(roleName);
            if (role.isPresent()) {
                Set<Priviliege> privilegesToSave = new HashSet<>();
                for(String privilegeName : privilegesListOfThisRole) {
                    Optional<Priviliege> privilege = priviliegeRepository.findByName(privilegeName);
                    if (privilege.isPresent()) {
                        privilegesToSave.add(privilege.get());
                    }
                }
                Role r = role.get();
                r.setPriviliege(new ArrayList(privilegesToSave));
                roleRepository.save(r);
            }
        }
    }
   
    private void seedUsers() {

        Optional<User> optUser = usersRepository.findByUsername(Constants.ADMIN_USERNAME);
        User user;
        if (optUser.isPresent()) {
            user = optUser.get();
        }
        else {
            user = new User();
        }

        user.setUsername(Constants.ADMIN_USERNAME);
        user.setPassword(passwordEncoder.encode("1234"));
        user.setEmail("no-reply@localhost");

        user.setCiutat("aaa");
        user.setCodiPostal("28888");
        user.setDataNaixement(new Date(1111));
        user.setDireccio("assaga");
        user.setPais("es");
        user.setTelefon("1241414");
        user.setCognom1("asffsa");
        user.setCognom2("aksfka");
        user.setDni("414141");
        user.setEmail("a@a.es");
        user.setNom("pepe");

        Optional<Role> adminRole = roleRepository.findByName(Constants.ADMIN_ROLE_NAME);

        if (adminRole.isPresent()) {
            user.setRole(adminRole.get());
        }

        usersRepository.save(user);
    }
}
