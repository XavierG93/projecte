/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.controller.impl;

import gerisoft.apirest.controller.AttentionsController;
import gerisoft.apirest.dto.AttentionsDTO;
import gerisoft.apirest.dto.PaginateResponse;
import gerisoft.apirest.entity.Attentions;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.service.AttentionsService;
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

/**
 *
 * @author Gurrea
 */
@RestController
@RequestMapping("/attentions")
public class AttentionsControllerImpl implements AttentionsController {

    @Autowired
    private AttentionsService attentionsService;
    /*
    Canviem el tipus del metode getAttentions a paginate response, aixo ens permetra establir filtres paginacio
    */
    @GetMapping
    @Override
    public PaginateResponse getAttentions(@RequestParam Map<String, String> params, Pageable page) {
        try {
            //Fem un objecte page per a iterar sobre les cerques del repository de la entitat a la que truquem
            return new PaginateResponse(attentionsService.findAllAttentions(params, page));
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Override
    public AttentionsDTO findAttentionsById(@PathVariable Long id) {
        Optional<Attentions> attentions = null;
        try {
            attentions = attentionsService.findAttentionsById(id);
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!attentions.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return convert(attentions.get());
    }

    @PostMapping
    @Override
    public AttentionsDTO saveAttentions(@Valid @RequestBody Attentions attentionsNew) {
        Attentions attentions;
        try {
            Optional<Attentions> optionalAttentions = attentionsService.findAttentionsByName(attentionsNew.getName());
            if (optionalAttentions.isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
            attentions = attentionsService.saveAttentions(attentionsNew);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
        return convert(attentions);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public void deleteAttentions(@PathVariable("id") Long id) {
        try {
            attentionsService.deleteAttentions(id);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PutMapping
    @Override
    public AttentionsDTO updateAttentions(@RequestBody AttentionsDTO attentionsDTO) {
        Attentions attentions = convertToEntity(attentionsDTO,true);
        Attentions attentionsUpdated ;
        try {
            attentionsUpdated = attentionsService.updateAttentions(attentions);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
        attentionsDTO = convert(attentionsUpdated);
        return attentionsDTO;
    }

}
