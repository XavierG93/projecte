/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.service;

import gerisoft.apirest.entity.Priviliege;
import gerisoft.apirest.exception.ValidationServiceException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gurrea
 */
@Service
public interface PriviliegeService {
    /**
     * Classe on implementarem generics dels metodes que farem servir amb la
     * classe Priviliege
     * 
     */
    public List<Priviliege> findAllPrivilieges()throws ValidationServiceException;

    public Optional<Priviliege> findPriviliegeById(Long id)throws ValidationServiceException;

    public Priviliege savePriviliege(Priviliege priviliegeNew)throws ValidationServiceException;

    public String deletePriviliege(Long id)throws ValidationServiceException;

    public Priviliege updatePriviliege(Priviliege priviliegeUpdate)throws ValidationServiceException;
    
}
