/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.service.impl;

import gerisoft.apirest.dto.PaginateResponse;
import gerisoft.apirest.dto.UserDTO;
import gerisoft.apirest.entity.Role;
import gerisoft.apirest.entity.User;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.repository.RoleRepository;
import gerisoft.apirest.repository.UserRepository;
import gerisoft.apirest.service.UserService;
import gerisoft.apirest.specifications.UserSpecification;
import gerisoft.apirest.utils.Constants;
import static gerisoft.apirest.utils.Constants.AUXILIAR_ROLE_NAME;
import static gerisoft.apirest.utils.Constants.TRABAJADOR_ROLE_NAME;

import gerisoft.apirest.utils.ConvertToDTO;
import gerisoft.apirest.utils.PrivilegesChecker;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gurrea Classe on tractem les dades introduides i establim un control
 * sobre si son presents o no a la bbdd i si son correctes.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired//En aquest cas espicifquem els generics introduits en UserService
    UserRepository userRepository;
    @Autowired//En aquest cas espicifquem els generics introduits en UserService
    RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PrivilegesChecker privilegesChecker;

    @Override
    public PaginateResponse findAllUsers(Map<String, String> params, Pageable page) throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.READ_ACTIVIDAD_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.READ_ACTIVIDAD_PRIVILEGE);
        }
        Specification<User> spec = Specification.where(new UserSpecification(params));
        Page<User> resultPage = userRepository.findAll(spec, page);
        List<UserDTO> resultDTO = resultPage.getContent().stream().map(u -> ConvertToDTO.convert(u)).collect(Collectors.toList());
        PaginateResponse p = new PaginateResponse(resultDTO);
        p.setPaging(resultPage);
        return p;
    }

    @Override
    public Optional<User> findUserById(Long id) throws PrivilegesException, ValidationServiceException {
        //Cerquem usuari per la seva clau id
        if (id == null) {
            throw new ValidationServiceException("El id introduit es null.");
        }

        if (!privilegesChecker.hasPrivilege(Constants.READ_TRABAJADOR_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.READ_TRABAJADOR_PRIVILEGE);
        }
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    @Override
    public Optional<User> findUserByPhone(String phone) throws PrivilegesException, ValidationServiceException {

        if (phone == null) {
            throw new ValidationServiceException("El telefon introduit es null.");
        }

        if (!privilegesChecker.hasPrivilege(Constants.READ_TRABAJADOR_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.READ_TRABAJADOR_PRIVILEGE);
        }
        Optional<User> user = userRepository.findByTelefon(phone);
        return user;
    }
    @Override
    public Optional<User> findUserByEmail(String email) throws PrivilegesException, ValidationServiceException {
        if (email == null) {
            throw new ValidationServiceException("El email introduit es null.");
        }

        if (!privilegesChecker.hasPrivilege(Constants.READ_TRABAJADOR_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.READ_TRABAJADOR_PRIVILEGE);
        }
        Optional<User> user = userRepository.findByEmail(email);
        return user;
    }
    @Override
    public User saveUser(User userNew) throws PrivilegesException, ValidationServiceException {
        Role role;
        if(userNew.getRole()!=null && userNew.getRole().getId() != null){
            role=roleRepository.findById(userNew.getRole().getId())
                               .orElseThrow(() -> new ValidationServiceException("El rol no existeix"));
        } else {
           throw new ValidationServiceException("Rol invàlid");
        }
        if (TRABAJADOR_ROLE_NAME.equals(role.getName())) {
            if (!privilegesChecker.hasPrivilege(Constants.CREATE_TRABAJADOR_PRIVILEGE,
                    SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
                throw new PrivilegesException(Constants.CREATE_TRABAJADOR_PRIVILEGE);
            }
        } else if (AUXILIAR_ROLE_NAME.equals(role.getName())) {
            if (!privilegesChecker.hasPrivilege(Constants.CREATE_AUXILIAR_PRIVILEGE,
                    SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
                throw new PrivilegesException(Constants.CREATE_AUXILIAR_PRIVILEGE);
            }
        }
        if (userNew == null) {
            throw new ValidationServiceException("L'usuari introduit es inexistent.");
        }

        if (userRepository.findByEmail(userNew.getEmail()).isPresent()) {
            throw new ValidationServiceException("Aquest email ja és registrat.");
        }
        if (userRepository.findByTelefon(userNew.getTelefon()).isPresent()) {
            throw new ValidationServiceException("Aquest telefon ja és registrat.");
        }
        if (userRepository.findByDni(userNew.getDni()).isPresent()) {
            throw new ValidationServiceException("Aquest dni ja és registrat.");
        }
        userNew.setPassword(passwordEncoder.encode(userNew.getPassword()));
        return userRepository.save(userNew);
    }

    @Override
    public void deleteUser(Long id) throws PrivilegesException, ValidationServiceException {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            User user = optUser.get();
            if (TRABAJADOR_ROLE_NAME.equals(user.getRole().getName())) {
                if (!privilegesChecker.hasPrivilege(Constants.CREATE_TRABAJADOR_PRIVILEGE,
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
                    throw new PrivilegesException(Constants.CREATE_TRABAJADOR_PRIVILEGE);
                }
            } else if (AUXILIAR_ROLE_NAME.equals(user.getRole().getName())) {
                if (!privilegesChecker.hasPrivilege(Constants.CREATE_AUXILIAR_PRIVILEGE,
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
                    throw new PrivilegesException(Constants.CREATE_AUXILIAR_PRIVILEGE);
                }
            } else {
                throw new PrivilegesException("L'usuari no es modificable.");
            }
            //Si de veritat es a la bbdd l'esborra
            userRepository.deleteById(id);
        } else {
            throw new ValidationServiceException("Usuari no existent");
        }
    }

    @Override
    public User updateUser(User userUpdated) throws ValidationServiceException, PrivilegesException {
        Role role = userUpdated.getRole();
        if (TRABAJADOR_ROLE_NAME.equals(role.getName())) {
            if (!privilegesChecker.hasPrivilege(Constants.UPDATE_TRABAJADOR_PRIVILEGE,
                    SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
                throw new PrivilegesException(Constants.UPDATE_TRABAJADOR_PRIVILEGE);
            }
        } else if (AUXILIAR_ROLE_NAME.equals(role.getName())) {
            if (!privilegesChecker.hasPrivilege(Constants.UPDATE_AUXILIAR_PRIVILEGE,
                    SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
                throw new PrivilegesException(Constants.UPDATE_AUXILIAR_PRIVILEGE);
            }
        } else {
            throw new PrivilegesException("L'usuari no es modificable.");
        }
        Long num = userUpdated.getId();
        User userToUpdate = new User();
        if (num == null) {
            throw new ValidationServiceException("L'objecte es null.");
        }
        //Si el id es troba present al repositori
        if (!userRepository.findById(num).isPresent()) {
            throw new ValidationServiceException("Aquest Id no existeix.");
        }
        //Comprovem que els camps unics no es repiteixin
        if (userRepository.findByCifExcludingId(userUpdated.getDni(), userUpdated.getId()).isPresent()) {
            throw new ValidationServiceException("Aquest DNI ja és registrat.");
        }
        if (userRepository.findByEmailExcludingId(userUpdated.getEmail(), userUpdated.getId()).isPresent()) {
            throw new ValidationServiceException("Aquest email ja és registrat.");
        }
        if (userRepository.findByTelefonExcludingId(userUpdated.getTelefon(), userUpdated.getId()).isPresent()) {
            throw new ValidationServiceException("Aquest telefon ja és registrat.");
        }
        userToUpdate.setId(userUpdated.getId());
        userToUpdate.setDni(userUpdated.getDni());
        userToUpdate.setNom(userUpdated.getNom());
        userToUpdate.setCognom1(userUpdated.getCognom1());
        userToUpdate.setCognom2(userUpdated.getCognom2());
        userToUpdate.setDataNaixement(userUpdated.getDataNaixement());
        userToUpdate.setTelefon(userUpdated.getTelefon());
        userToUpdate.setCiutat(userUpdated.getCiutat());
        userToUpdate.setPais(userUpdated.getPais());
        userToUpdate.setDireccio(userUpdated.getDireccio());
        userToUpdate.setCodiPostal(userUpdated.getCodiPostal());
        userToUpdate.setRole(userUpdated.getRole());
        return this.userRepository.save(userToUpdate);
    }

    @Override
    public Optional<User> findByUsername(String username) throws PrivilegesException, ValidationServiceException {
        return findByUsername(username, false);
    }

    @Override
    public Optional<User> findByUsername(String username, boolean saltarSeguridad) throws PrivilegesException, ValidationServiceException {
        if (username == null) {
            throw new ValidationServiceException("El nom introduit es null.");
        }
        if (saltarSeguridad == false) {
            if (!privilegesChecker.hasPrivilege(Constants.READ_TRABAJADOR_PRIVILEGE,
                    SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
                throw new PrivilegesException(Constants.READ_TRABAJADOR_PRIVILEGE);
            }
        }
        Optional<User> user = userRepository.findByUsername(username);
        return user;
    }
}
