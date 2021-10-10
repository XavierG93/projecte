/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.controller;

import gerisoft.apirest.dto.AttentionsDTO;
import gerisoft.apirest.dto.PaginateResponse;
import gerisoft.apirest.entity.Attentions;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 *
 * @author Gurrea
 */
public interface AttentionsController {

    /**
     * Repetim el fet a AttentionsService, un generic dels metodes que el
     * controlador fará servir
     * canviem el tipus de getAttentions per a incloure les característiques de paginateresponse
     *
     */
    PaginateResponse getAttentions(@RequestParam Map<String, String> params, Pageable page);

    AttentionsDTO findAttentionsById(Long id);

    AttentionsDTO saveAttentions(Attentions attentionsNew);

    void deleteAttentions(Long id);

    AttentionsDTO updateAttentions(AttentionsDTO attentionsDTO);

}
