/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.controller;

import gerisoft.apirest.dto.PaginateResponse;
import gerisoft.apirest.dto.ResidentsDTO;
import gerisoft.apirest.entity.Residents;
import org.springframework.data.domain.Pageable;
import java.util.Map;

/**
 *
 * @author Gurrea
 */
public interface ResidentsController {

    /**
     * Repetim el fet a ResidentsController, un generic dels metodes que el
     * controlador fará servir
     *canviem el tipus de getResidents per a incloure les característiques de paginateresponse
     */
    PaginateResponse getResidents(Map<String, String> params, Pageable page);

    ResidentsDTO findResidentsById(Long id);

    ResidentsDTO saveResidents(Residents residentsNew);

    void deleteResidents(Long id);

    ResidentsDTO updateResidents(Residents residentsDTO);

}
