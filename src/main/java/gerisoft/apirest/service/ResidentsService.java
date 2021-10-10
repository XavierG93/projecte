/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.service;

import gerisoft.apirest.dto.ActivitiesResidentDTO;
import gerisoft.apirest.dto.AttentionsResidentDTO;
import gerisoft.apirest.dto.PaginateResponse;
import gerisoft.apirest.entity.ActivitiesResidents;
import gerisoft.apirest.entity.Residents;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.exception.ValidationServiceException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gurrea
 */
@Service
public interface ResidentsService {

    /**
     * Classe on implementarem generics dels metodes que farem servir amb la
     * classe Residents
     *Canviem el tipus a paginate per a poder donar format als resultats
     * @return
     */
    Page<Residents> findAllResidents(Map<String, String> params, Pageable page) throws PrivilegesException;
    //Implementem aques metode per interactuar amb la entitat que fa de connexio i poder establir les dades
    PaginateResponse findActivitiesByResident(Long id, Map<String, String> params, Pageable page) throws PrivilegesException;

    Optional<Residents> findResidentsById(Long id) throws PrivilegesException, ValidationServiceException;

    Optional<Residents> findResidentsByDNI(String dni) throws PrivilegesException, ValidationServiceException;

    Residents saveResidents(Residents residentsNew) throws PrivilegesException, ValidationServiceException;
    //Implementem aques metode per interactuar amb la entitat que fa de connexio i poder establir les dades
    ActivitiesResidentDTO saveActivitiesResidents(ActivitiesResidentDTO residentsNew) throws PrivilegesException, ValidationServiceException, NotFoundException;

    String deleteResidents(Long id) throws PrivilegesException, ValidationServiceException;

    Residents updateResidents(Residents residentsUpdate) throws PrivilegesException, ValidationServiceException;
    //Implementem aques metode per interactuar amb la entitat que fa de connexio i poder establir les dades
    ActivitiesResidentDTO updateActivitiesResidents(ActivitiesResidentDTO residentsNew) throws PrivilegesException, ValidationServiceException, NotFoundException;
    //Implementem aques metode per interactuar amb la entitat que fa de connexio i poder establir les dades
    void deleteActivitiesResidents(Long id, Long activitiResidentId) throws PrivilegesException, ValidationServiceException;
    //Implementem aques metode per interactuar amb la entitat que fa de connexio i poder establir les dades, en aquest cas establim que sigui page per ademes donar format
    PaginateResponse findAttentionsByResident(Long id, Map<String, String> params, Pageable page) throws PrivilegesException;
    //Implementem aques metode per interactuar amb la entitat que fa de connexio i poder establir les dades
    AttentionsResidentDTO saveAttentionsResidents(AttentionsResidentDTO residentsNew) throws PrivilegesException, ValidationServiceException, NotFoundException ;
    //Implementem aques metode per interactuar amb la entitat que fa de connexio i poder establir les dades
    AttentionsResidentDTO updateAttentionsResidents(AttentionsResidentDTO residentsNew) throws PrivilegesException, ValidationServiceException, NotFoundException;
    //Implementem aques metode per interactuar amb la entitat que fa de connexio i poder establir les dades
    void deleteAttentionsResidents(Long id, Long attentionsResidentId) throws PrivilegesException, ValidationServiceException;

}
