/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.controller;

import gerisoft.apirest.dto.PriviliegesDTO;
import gerisoft.apirest.entity.Priviliege;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Gurrea
 */

public interface PriviliegeController {

    /**
     * Repetim el fet a ControllerService, un generic dels metodes que el
     * controlador fará servir
     *
     * @return 
     */
    public List<PriviliegesDTO> findAllPrivilieges();

    public PriviliegesDTO findPriviliegeById(Long id);

    public PriviliegesDTO savePriviliege(Priviliege priviliegeNew);

    public void deletePriviliege(Long id);

    public PriviliegesDTO updatePriviliege(PriviliegesDTO priviliegesDTO);
}
