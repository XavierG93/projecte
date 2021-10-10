/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.controller.impl;

import gerisoft.apirest.controller.ActivitiesController;
import gerisoft.apirest.dto.ActivitiesDTO;
import gerisoft.apirest.dto.PaginateResponse;
import gerisoft.apirest.entity.Activities;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.service.ActivitiesService;
import static gerisoft.apirest.utils.ConvertToDTO.convert;
import static gerisoft.apirest.utils.ConvertToEntity.convertToEntity;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import org.springframework.data.domain.Page;

/**
 *
 * @author Gurrea
 */
@RestController
@RequestMapping("/activities")
public class ActivitiesControllerImpl implements ActivitiesController {

    @Autowired
    private ActivitiesService activitiesService;
    /*
    Canviem el tipus del metode getActivities a paginate response, aixo ens permetra establir filtres paginacio
    */
    @GetMapping
    @Override
    public PaginateResponse getActivities(@RequestParam Map<String, String> params, Pageable page) {
        /*
        El map conte el nom del camp i el seu contingut a cercar, pageable permet capturar parales clau dintre la url per veure si ha paginarles, ordenarles mitjatçant els seus atributs d'entitat, etc 
        */
        try {
            //Fem un objecte page per a iterar sobre les cerques del repository de la entitat a la que truquem
            Page<Activities> activities=activitiesService.findAllActivities(params, page);
            return new PaginateResponse(activities);
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Override
    public ActivitiesDTO findActivitiesById(@PathVariable Long id) {
        Optional<Activities> activities = null;
        try {
            activities = activitiesService.findActivitiesById(id);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!activities.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return convert(activities.get());
    }

    @PostMapping
    @Override
    public ActivitiesDTO saveActivities(@Valid @RequestBody Activities activitiesNew) {
        Activities activities ;
        try {
            Optional<Activities> optionalActivities = activitiesService.findActivitiesByName(activitiesNew.getName());
            if (optionalActivities.isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
            activities = activitiesService.saveActivities(activitiesNew);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
        return convert(activities);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public void deleteActivities(@PathVariable("id") Long id) {
        try {
            activitiesService.deleteActivities(id);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PutMapping
    @Override
    public ActivitiesDTO updateActivities(@RequestBody ActivitiesDTO activitiesDTO) {
        Activities activities = convertToEntity(activitiesDTO,true);
        Activities activitiesUpdated ;
        try {
            activitiesUpdated = activitiesService.updateActivities(activities);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
        activitiesDTO = convert(activitiesUpdated);
        return activitiesDTO;
    }

}
