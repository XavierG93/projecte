/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.service.impl;

import gerisoft.apirest.entity.Priviliege;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.repository.PriviliegeRepository;
import gerisoft.apirest.service.PriviliegeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gurrea
 */
@Service
public class PriviliegeServiceImpl implements PriviliegeService {

    @Autowired//En aquest cas espicifquem els generics introduits en UserService
    PriviliegeRepository priviliegeRepository;
/*
    Canviem el tipus a page per a poder formatar els resultats obtinguts de la crida
    */
    @Override
    public List<Priviliege> findAllPrivilieges() {
        return priviliegeRepository.findAll();
    }

    @Override
    public Optional<Priviliege> findPriviliegeById(Long id) {
        //Cerquem el privilegi per la seva clau id
        Optional<Priviliege> priviliege = priviliegeRepository.findById(id);
        return priviliege;
    }

    @Override
    public Priviliege savePriviliege(Priviliege priviliegeNew) throws ValidationServiceException {
        if (priviliegeNew == null) {
            throw new ValidationServiceException("Aquest privilegi no es correcte.");
        }
        
        return priviliegeRepository.save(priviliegeNew);
    }

    @Override
    public String deletePriviliege(Long id) {
        if (priviliegeRepository.findById(id).isPresent()) {
            //Si de veritat es a la bbdd l'esborra
            priviliegeRepository.deleteById(id);
            return "Privilegi eliminat correctament.";
        }
        return "Error! El privilegi introduit no existeix!";
    }

    @Override
    public Priviliege updatePriviliege(Priviliege priviliegeUpdated) throws ValidationServiceException {
        Long num = priviliegeUpdated.getId();
        Priviliege priviliegeToUpdate = new Priviliege(priviliegeUpdated.getName());
        if (num == null) {
            throw new ValidationServiceException("L'objecte es null.");
        }
        if (!priviliegeRepository.findById(num).isPresent()) {
            throw new ValidationServiceException("Aquest Id no existeix.");

        }
        if (priviliegeRepository.findByNameExcludingId(priviliegeUpdated.getName(),priviliegeUpdated.getId()).isPresent()) {
            throw new ValidationServiceException("Aquest Nom ja és registrat.");
        }
        priviliegeToUpdate.setId(priviliegeUpdated.getId());
        priviliegeToUpdate.setName(priviliegeUpdated.getName());
        priviliegeToUpdate.setRoles(priviliegeUpdated.getRoles());

        return priviliegeRepository.save(priviliegeToUpdate);
    }

}
