/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.service;

import gerisoft.apirest.dto.PaginateResponse;
import gerisoft.apirest.entity.Activities;
import gerisoft.apirest.entity.Attentions;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.exception.ValidationServiceException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Gurrea
 */
@Service
public interface AttentionsService {

    /**
     * Classe on implementarem generics dels metodes que farem servir amb la
     * classe Attentions
     * Canviem el tipus a paginate per a poder donar format als resultats
     */
    Page<Attentions> findAllAttentions(@RequestParam Map<String, String> params, Pageable page) throws PrivilegesException;

    Optional<Attentions> findAttentionsById(Long id) throws PrivilegesException;

    Attentions saveAttentions(Attentions attentionsNew) throws ValidationServiceException, PrivilegesException;

    String deleteAttentions(Long id) throws ValidationServiceException, PrivilegesException;

    Attentions updateAttentions(Attentions attentionsUpdate) throws ValidationServiceException, PrivilegesException;

    Optional<Attentions> findAttentionsByName(String name) throws PrivilegesException, ValidationServiceException;
}
