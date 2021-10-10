/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.controller.impl;

import gerisoft.apirest.controller.ResidentsController;
import gerisoft.apirest.dto.ActivitiesResidentDTO;
import gerisoft.apirest.dto.AttentionsResidentDTO;
import gerisoft.apirest.dto.PaginateResponse;
import gerisoft.apirest.dto.ResidentsDTO;
import gerisoft.apirest.entity.Residents;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.service.ResidentsService;
import static gerisoft.apirest.utils.ConvertToDTO.convert;
import java.util.Map;
import java.util.Optional;

import javassist.NotFoundException;
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
@RequestMapping("/residents")
public class ResidentsControllerImpl implements ResidentsController {

    @Autowired
    private ResidentsService residentsService;

    @GetMapping
    @Override
     /*
    Canviem el tipus del metode getActivities a paginate response, aixo ens permetra establir filtres paginacio
    */
    public PaginateResponse getResidents(@RequestParam Map<String, String> params, Pageable page) {
        try {
            return new PaginateResponse(residentsService.findAllResidents(params, page));
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }
    //Cridem a l'anidacio entre activitie i resident
    @GetMapping("/{id}/activities")
    public PaginateResponse getResidentsActivities(@PathVariable("id") Long id, @RequestParam Map<String, String> params, Pageable page) {
        try {
            return residentsService.findActivitiesByResident(id, params, page);
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PostMapping("/{id}/activities")
    public ActivitiesResidentDTO saveActivitieResident(@PathVariable("id") Long id, @Valid @RequestBody ActivitiesResidentDTO activitiesResidents) {
        try {
            activitiesResidents.setResidentId(id);
            return residentsService.saveActivitiesResidents(activitiesResidents);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PutMapping("/{id}/activities/{activitieId}")
    public ActivitiesResidentDTO updateActivitieResident(@PathVariable("id") Long id, @PathVariable("activitieId") Long activitieResidentId,
                                                         @Valid @RequestBody ActivitiesResidentDTO activitiesResidents) {
        try {
            activitiesResidents.setResidentId(id);
            activitiesResidents.setId(activitieResidentId);
            return residentsService.updateActivitiesResidents(activitiesResidents);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @DeleteMapping("/{id}/activities/{activitieId}")
    public void deleteActivitieResident(@PathVariable("id") Long id, @PathVariable("activitieId") Long activitieResidentId) {
        try {
            residentsService.deleteActivitiesResidents(id, activitieResidentId);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }


    @GetMapping("/{id}/attentions")
    public PaginateResponse getResidentsAttentions(@PathVariable("id") Long id, @RequestParam Map<String, String> params, Pageable page) {
        try {
            return residentsService.findAttentionsByResident(id, params, page);
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PostMapping("/{id}/attentions")
    public AttentionsResidentDTO saveActivitieResident(@PathVariable("id") Long id, @Valid @RequestBody AttentionsResidentDTO attentionsResidentDTO) {
        try {
            attentionsResidentDTO.setResidentId(id);
            return residentsService.saveAttentionsResidents(attentionsResidentDTO);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PutMapping("/{id}/attentions/{attentionId}")
    public AttentionsResidentDTO updateActivitieResident(@PathVariable("id") Long id, @PathVariable("attentionId") Long attentionResidentId,
                                                         @Valid @RequestBody AttentionsResidentDTO attentionResidents) {
        try {
            attentionResidents.setResidentId(id);
            attentionResidents.setId(attentionResidentId);
            return residentsService.updateAttentionsResidents(attentionResidents);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @DeleteMapping("/{id}/attentions/{attentionId}")
    public void deleteAttentionResident(@PathVariable("id") Long id, @PathVariable("attentionId") Long attentionResidentId) {
        try {
            residentsService.deleteAttentionsResidents(id, attentionResidentId);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Override
    public ResidentsDTO findResidentsById(@PathVariable Long id) {
        Optional<Residents> residents = null;
        try {
            residents = residentsService.findResidentsById(id);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
        return convert(residents.get());
    }

    @PostMapping
    @Override
    public ResidentsDTO saveResidents(@Valid @RequestBody Residents residentsNew) {
        Residents residents;
        try {
            Optional<Residents> optionalResidents = residentsService.findResidentsByDNI(residentsNew.getDni());
            if (optionalResidents.isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
            residents = residentsService.saveResidents(residentsNew);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
        return convert(residents);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public void deleteResidents(@PathVariable("id") Long id) {
        try {
            residentsService.deleteResidents(id);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    @Override
    public ResidentsDTO updateResidents(@Valid @RequestBody Residents residents) {
        Residents residentsUpdated;
        try {
            residentsUpdated = residentsService.updateResidents(residents);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (PrivilegesException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
        ResidentsDTO residentsDTO = convert(residentsUpdated);
        return residentsDTO;
    }

}
