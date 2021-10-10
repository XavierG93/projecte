/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.controller.impl;

import gerisoft.apirest.controller.PriviliegeController;
import gerisoft.apirest.dto.PriviliegesDTO;
import gerisoft.apirest.entity.Priviliege;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.service.PriviliegeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import static gerisoft.apirest.utils.ConvertToDTO.convert;
import static gerisoft.apirest.utils.ConvertToEntity.convertToEntity;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Gurrea
 */
@RestController
@RequestMapping("/privilieges")
public class PriviliegeControllerImpl implements PriviliegeController {

    @Autowired
    PriviliegeService priviliegeService;

    //Serveix per a fer la crida al servidor localhost:8080/paraulaClau
    // http://localhost:8080/priviliege (GET)
    @GetMapping
    @Override
    public List<PriviliegesDTO> findAllPrivilieges() {
        try {
            List<Priviliege> privilieges = priviliegeService.findAllPrivilieges();
            List<PriviliegesDTO> priviliegesDTO = new ArrayList<PriviliegesDTO>();
            if (privilieges != null) {
                for (Priviliege p : privilieges) {
                    priviliegesDTO.add(convert(p));
                }
            }

            return priviliegesDTO;
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        }
    }

    // http://localhost:8080/priviliege/1 (GET)
    @GetMapping("/{id}")
    @Override
    public PriviliegesDTO findPriviliegeById(@PathVariable Long id) {
         Optional<Priviliege> priviliege = null;
        try {
            priviliege= priviliegeService.findPriviliegeById(id);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        }
        return convert(priviliege.get());
    }
@PostMapping
    @Override
    public PriviliegesDTO savePriviliege(Priviliege priviliegeNew) {
        Priviliege priviliege;
        try {
            priviliege= priviliegeService.savePriviliege(priviliegeNew);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        }
        return convert(priviliege);
    }
// http://localhost:8080/priviliege/delete/1 (GET)
@DeleteMapping(value = "/{id}")
    @Override
    public void deletePriviliege(@PathVariable("id") Long id) {
        try {
            priviliegeService.deletePriviliege(id);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        }
    }

    @Override
    @PutMapping("/{id}")
    //@RequestMapping(value = "/priviliege/update", method = RequestMethod.PATCH, produces = "application/json")

    public PriviliegesDTO updatePriviliege(PriviliegesDTO priviliegesDTO) {
        Priviliege priviliege=convertToEntity(priviliegesDTO);
        Priviliege priviliegeUpdated=null;
        try {
            priviliegeUpdated= priviliegeService.updatePriviliege(priviliege);
        } catch (ValidationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        }
        priviliegesDTO=convert(priviliegeUpdated);
        return priviliegesDTO;
    }

    /*@Override
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")

    public String test() {
        return "Test done";
    }*/
}
