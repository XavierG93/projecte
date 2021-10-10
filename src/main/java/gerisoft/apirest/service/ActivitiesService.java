/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.service;

import gerisoft.apirest.entity.Activities;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.exception.ValidationServiceException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gurrea
 */
@Service
public interface ActivitiesService {
/*
    Canviem el tipus a paginate per a poder donar format als resultats
    */
    Page<Activities> findAllActivities(Map<String, String> params, Pageable page) throws PrivilegesException;

    Optional<Activities> findActivitiesById(Long id) throws PrivilegesException, ValidationServiceException;

    Optional<Activities> findActivitiesByName(String name) throws PrivilegesException, ValidationServiceException;

    Activities saveActivities(Activities activitiesNew) throws PrivilegesException, ValidationServiceException;

    String deleteActivities(Long id) throws PrivilegesException, ValidationServiceException;

    Activities updateActivities(Activities activitiesUpdate) throws PrivilegesException, ValidationServiceException;
}
