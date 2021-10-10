/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.controller;

import gerisoft.apirest.dto.ActivitiesDTO;
import gerisoft.apirest.dto.PaginateResponse;
import gerisoft.apirest.entity.Activities;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 *
 * @author Gurrea
 */
public interface ActivitiesController {
    /*
    canviem el tipus de getActivities per a incloure les característiques de paginateresponse
    */
    PaginateResponse getActivities(Map<String, String> params, Pageable page);

    ActivitiesDTO findActivitiesById(Long id);

    ActivitiesDTO saveActivities(Activities activitiesNew);

    void deleteActivities(Long id);

    ActivitiesDTO updateActivities(ActivitiesDTO activitiesDTO);
    
}
