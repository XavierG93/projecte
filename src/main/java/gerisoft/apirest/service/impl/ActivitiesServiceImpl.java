/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.service.impl;

import gerisoft.apirest.entity.Activities;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.repository.ActivitiesRepository;
import gerisoft.apirest.service.ActivitiesService;
import gerisoft.apirest.specifications.ActivitiesSpecification;
import gerisoft.apirest.utils.Constants;
import gerisoft.apirest.utils.PrivilegesChecker;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gurrea
 */
@Service
public class ActivitiesServiceImpl implements ActivitiesService {

    @Autowired
    ActivitiesRepository activitiesRepository;
    @Autowired
    private PrivilegesChecker privilegesChecker;
/*
    Canviem el tipus a page per a poder formatar els resultats obtinguts de la crida
    */
    @Override
    public Page<Activities> findAllActivities(Map<String, String> params, Pageable page) throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.READ_ACTIVIDAD_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.READ_ACTIVIDAD_PRIVILEGE);
        }
        Specification<Activities> spec = Specification.where(new ActivitiesSpecification(params));
        return activitiesRepository.findAll(spec, page);//Agreguem la opcio de o bé, especificacio o ordenacio o els dos
    }

    @Override
    public Optional<Activities> findActivitiesById(Long id) throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.READ_ACTIVIDAD_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.READ_ACTIVIDAD_PRIVILEGE);
        }
        Optional<Activities> activities = activitiesRepository.findById(id);
        return activities;
    }

    @Override
    public Optional<Activities> findActivitiesByName(String name) throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.READ_ACTIVIDAD_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.READ_ACTIVIDAD_PRIVILEGE);
        }
        return activitiesRepository.findByName(name);
    }

    @Override
    public Activities saveActivities(Activities activitiesNew) throws ValidationServiceException, PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.CREATE_ACTIVIDAD_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.CREATE_ACTIVIDAD_PRIVILEGE);
        }
        if (activitiesNew == null) {
            throw new ValidationServiceException("L'activitat introduida es inexistent.");
        }
        
        return activitiesRepository.save(activitiesNew);
    }

    @Override
    public String deleteActivities(Long id) throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.DELETE_ACTIVIDAD_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.DELETE_ACTIVIDAD_PRIVILEGE);
        }
        if (activitiesRepository.findById(id).isPresent()) {
            activitiesRepository.deleteById(id);
            return "Activitat esborrada correctament.";
        }
        return "Error. La activitat no existeix!";
    }

    @Override
    public Activities updateActivities(Activities activitiesUpdate) throws ValidationServiceException, PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.UPDATE_ACTIVIDAD_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.UPDATE_ACTIVIDAD_PRIVILEGE);
        }
        Long num = activitiesUpdate.getId();
        Activities activitiesToUpdate = new Activities();
        if (num == null) {
            throw new ValidationServiceException("L'objecte es null.");
        }
        if (!activitiesRepository.findById(num).isPresent()) {
            throw new ValidationServiceException("Aquest Id no existeix.");
        }
        if (activitiesRepository.findByNomExcludingId(activitiesUpdate.getName(),activitiesUpdate.getId()).isPresent()) {
            throw new ValidationServiceException("Aquest nom ja és registrat.");
        }
        activitiesToUpdate.setId(activitiesUpdate.getId());
        activitiesToUpdate.setName(activitiesUpdate.getName());
        return activitiesRepository.save(activitiesToUpdate);
    }
}
