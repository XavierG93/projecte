/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.service.impl;

import gerisoft.apirest.dto.PaginateResponse;
import gerisoft.apirest.entity.Activities;
import gerisoft.apirest.entity.Attentions;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.repository.AttentionsRepository;
import gerisoft.apirest.service.AttentionsService;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import gerisoft.apirest.specifications.ActivitiesSpecification;
import gerisoft.apirest.specifications.AttentionsSpecification;
import gerisoft.apirest.utils.Constants;
import gerisoft.apirest.utils.PrivilegesChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gurrea
 */
@Service
public class AttentionsServiceImpl implements AttentionsService {

    @Autowired//En aquest cas espicifquem els generics introduits en UserService
    AttentionsRepository attentionsRepository;
    @Autowired
    private PrivilegesChecker privilegesChecker;
/*
    Canviem el tipus a page per a poder formatar els resultats obtinguts de la crida
    */
    @Override
    public Page<Attentions> findAllAttentions(Map<String, String> params, Pageable page) throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.READ_ATENCION_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.READ_ATENCION_PRIVILEGE);
        }
        Specification<Attentions> spec = Specification.where(new AttentionsSpecification(params));
        return attentionsRepository.findAll(spec, page);
    }

    @Override
    public Optional<Attentions> findAttentionsById(Long id) throws PrivilegesException {

        if (!privilegesChecker.hasPrivilege(Constants.READ_ATENCION_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.READ_ATENCION_PRIVILEGE);
        }
        Optional<Attentions> attention = attentionsRepository.findById(id);
        return attention;
    }

    @Override
    public Attentions saveAttentions(Attentions attentionsNew) throws ValidationServiceException, PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.CREATE_ATENCION_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.CREATE_ATENCION_PRIVILEGE);
        }
        if (attentionsNew == null) {
            throw new ValidationServiceException("L'activitat introduida es inexistent.");
        }
        return attentionsRepository.save(attentionsNew);
    }

    @Override
    public String deleteAttentions(Long id) throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.DELETE_ATENCION_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.DELETE_ATENCION_PRIVILEGE);
        }
        if (attentionsRepository.findById(id).isPresent()) {
            attentionsRepository.deleteById(id);
            return "Attention esborrada correctament.";

        }
        return "Error. La attention no existeix!";
    }

    @Override
    public Attentions updateAttentions(Attentions attentionsUpdate) throws ValidationServiceException, PrivilegesException {

        if (!privilegesChecker.hasPrivilege(Constants.UPDATE_ATENCION_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.UPDATE_ATENCION_PRIVILEGE);
        }
        Long num = attentionsUpdate.getId();
        Activities activitiesToUpdate = new Activities();
        if (num == null) {
            throw new ValidationServiceException("L'objecte es null.");
        }
        if (!attentionsRepository.findById(num).isPresent()) {
            throw new ValidationServiceException("Aquest Id no existeix.");

        }
        if (attentionsRepository.findByNomExcludingId(attentionsUpdate.getName(),attentionsUpdate.getId()).isPresent()) {
            throw new ValidationServiceException("Aquest nom ja és registrat.");
        }
        attentionsUpdate.setId(attentionsUpdate.getId());
        attentionsUpdate.setName(attentionsUpdate.getName());

        return attentionsRepository.save(attentionsUpdate);
    }

    @Override
    public Optional<Attentions> findAttentionsByName(String name) throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.READ_ATENCION_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.READ_ATENCION_PRIVILEGE);
        }
        return attentionsRepository.findByName(name);
    }
}
