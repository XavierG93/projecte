/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.service.impl;

import gerisoft.apirest.dto.ActivitiesResidentDTO;
import gerisoft.apirest.dto.AttentionsResidentDTO;
import gerisoft.apirest.dto.PaginateResponse;
import gerisoft.apirest.entity.*;
import gerisoft.apirest.exception.PrivilegesException;
import gerisoft.apirest.exception.ValidationServiceException;
import gerisoft.apirest.repository.ActivitiesResidentsRepository;
import gerisoft.apirest.repository.AttentionsResidentsRepository;
import gerisoft.apirest.repository.ResidentsRepository;
import gerisoft.apirest.service.ActivitiesService;
import gerisoft.apirest.service.AttentionsService;
import gerisoft.apirest.service.ResidentsService;
import gerisoft.apirest.service.UserService;
import gerisoft.apirest.specifications.ActivitiesResidentsSpecification;
import gerisoft.apirest.specifications.AttentionsResidentsSpecification;
import gerisoft.apirest.specifications.ResidentsSpecification;
import gerisoft.apirest.utils.Constants;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import gerisoft.apirest.utils.ConvertToDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import gerisoft.apirest.utils.PrivilegesChecker;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Gurrea
 */
@Service
public class ResidentsServiceImpl implements ResidentsService {

    @Autowired//En aquest cas espicifquem els generics introduits en ResidentsService
    ResidentsRepository residentsRepository;
    @Autowired
    ActivitiesResidentsRepository activitiesResidentsRepository;
    @Autowired
    AttentionsResidentsRepository attentionsResidentsRepository;
    @Autowired
    private PrivilegesChecker privilegesChecker;
    @Autowired
    private UserService userService;
    @Autowired
    private ActivitiesService activitiesService;
    @Autowired
    private AttentionsService attentionsService;
/*
    Canviem el tipus a page per a poder formatar els resultats obtinguts de la crida
    */
    @Override
    public Page<Residents> findAllResidents(Map<String, String> params, Pageable page) throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.READ_RESIDENTE_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.READ_RESIDENTE_PRIVILEGE);
        }
        Specification<Residents> spec = Specification.where(new ResidentsSpecification(params));
        return residentsRepository.findAll(spec, page);
    }

    @Override
    public Optional<Residents> findResidentsById(Long id) throws ValidationServiceException, PrivilegesException {
        //Cerquem usuari per la seva clau id
        if (id == null) {
            throw new ValidationServiceException("El id introduit es null.");
        }
        if (!privilegesChecker.hasPrivilege(Constants.READ_RESIDENTE_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.READ_RESIDENTE_PRIVILEGE);
        }
        Optional<Residents> residents = residentsRepository.findById(id);
        return residents;
    }

    @Override
    public Residents saveResidents(Residents residentsNew) throws ValidationServiceException, PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.CREATE_RESIDENTE_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.CREATE_RESIDENTE_PRIVILEGE);
        }
        if (residentsNew == null) {
            throw new ValidationServiceException("El resident introduit es inexistent.");

        }
        if (residentsRepository.findByDni(residentsNew.getDni()).isPresent()) {
            throw new ValidationServiceException("Aquest DNI ja és registrat.");
        }
        return residentsRepository.save(residentsNew);
    }

    @Override
    public String deleteResidents(Long id) throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.DELETE_RESIDENTE_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.DELETE_RESIDENTE_PRIVILEGE);
        }
        if (residentsRepository.findById(id).isPresent()) {
            //Si de veritat es a la bbdd l'esborra
            residentsRepository.deleteById(id);
            return "Resident eliminat correctament.";
        }
        return "Error! El resident no existeix!";
    }

    @Override
    public Residents updateResidents(Residents residentsUpdate) throws ValidationServiceException, PrivilegesException {
         if (!privilegesChecker.hasPrivilege(Constants.UPDATE_RESIDENTE_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.UPDATE_RESIDENTE_PRIVILEGE);
        }
        Long num = residentsUpdate.getId();
        Residents residentToUpdate = new Residents();
        if (num == null) {
            throw new ValidationServiceException("L'objecte es null.");
        }
        if (!residentsRepository.findById(num).isPresent()) {

            throw new ValidationServiceException("Aquest Id no existeix.");

        }
        if (residentsRepository.findByCifExcludingId(residentsUpdate.getDni(), residentsUpdate.getId()).isPresent()) {
            throw new ValidationServiceException("Aquest DNI ja és registrat.");
        }
        residentToUpdate.setId(residentsUpdate.getId());
        residentToUpdate.setDni(residentsUpdate.getDni());
        residentToUpdate.setNom(residentsUpdate.getNom());
        residentToUpdate.setCognom1(residentsUpdate.getCognom1());
        residentToUpdate.setCognom2(residentsUpdate.getCognom2());
        residentToUpdate.setDataNaixement(residentsUpdate.getDataNaixement());
        residentToUpdate.setDataBaixa(residentsUpdate.getDataBaixa());
        residentToUpdate.setDataIngres(residentsUpdate.getDataIngres());
        return residentsRepository.save(residentToUpdate);
    }

    @Override
    public Optional<Residents> findResidentsByDNI(String dni) throws ValidationServiceException, PrivilegesException {
         if (!privilegesChecker.hasPrivilege(Constants.READ_RESIDENTE_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.READ_RESIDENTE_PRIVILEGE);
        }
        Optional<Residents> resident = residentsRepository.findByDni(dni);
        return resident;
    }
/*
    Canviem el tipus a page per a poder formatar els resultats obtinguts de la crida
    permet cercar mitjatçant la entitat ActivitiesResidents les activitats que es fan sobre un resident i l'usuari que les fa
    */
    public PaginateResponse findActivitiesByResident(Long id, Map<String, String> params, Pageable page) throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.READ_ACTIVIDAD_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.READ_ACTIVIDAD_PRIVILEGE);
        }
        params.put("resident.id", id.toString());
        Specification<ActivitiesResidents> spec = Specification.where(new ActivitiesResidentsSpecification(params));
        Page<ActivitiesResidents> resultPage = activitiesResidentsRepository.findAll(spec, page);
        List<ActivitiesResidentDTO> resultDTO = resultPage.getContent().stream().map(ar -> ConvertToDTO.convert(ar)).collect(Collectors.toList());
        PaginateResponse p = new PaginateResponse(resultDTO);
        p.setPaging(resultPage);
        return p;
    }

    public ActivitiesResidentDTO saveActivitiesResidents(ActivitiesResidentDTO residentsNew) throws PrivilegesException, ValidationServiceException, NotFoundException {
        if (!privilegesChecker.hasPrivilege(Constants.CREATE_ACTIVIDAD_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.CREATE_ACTIVIDAD_PRIVILEGE);
        }
        ActivitiesResidents newActivitieResident = new ActivitiesResidents();
        Optional<Activities> activitie = activitiesService.findActivitiesById(residentsNew.getActivitieId());
        Optional<User> user = userService.findUserById(residentsNew.getUserId());
        Optional<Residents> resident = findResidentsById(residentsNew.getResidentId());

        if (activitie.isPresent() && user.isPresent() && resident.isPresent()) {
            newActivitieResident.setActivitie(activitie.get());
            newActivitieResident.setUser(user.get());
            newActivitieResident.setResident(resident.get());
            newActivitieResident.setObservacion(residentsNew.getObservacion());
            newActivitieResident.setDataActiviti(residentsNew.getDataActiviti());
            return ConvertToDTO.convert(activitiesResidentsRepository.save(newActivitieResident));
        }
        throw new NotFoundException("Entitat dependent no existeix");
    }

    public ActivitiesResidentDTO updateActivitiesResidents(ActivitiesResidentDTO residentsNew) throws PrivilegesException, ValidationServiceException, NotFoundException {
        if (!privilegesChecker.hasPrivilege(Constants.UPDATE_ACTIVIDAD_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.UPDATE_ACTIVIDAD_PRIVILEGE);
        }
        ActivitiesResidents activitieResident = activitiesResidentsRepository.findById(residentsNew.getId()).orElse(null);
        if (activitieResident == null) {
            throw new NotFoundException("El registre no existeix");
        }
        Optional<Activities> activitie = activitiesService.findActivitiesById(residentsNew.getActivitieId());
        Optional<User> user = userService.findUserById(residentsNew.getUserId());
        Optional<Residents> resident = findResidentsById(residentsNew.getResidentId());

        if (activitie.isPresent() && user.isPresent() && resident.isPresent()) {
            activitieResident.setActivitie(activitie.get());
            activitieResident.setUser(user.get());
            activitieResident.setResident(resident.get());
            activitieResident.setObservacion(residentsNew.getObservacion());
            activitieResident.setDataActiviti(residentsNew.getDataActiviti());
            return ConvertToDTO.convert(activitiesResidentsRepository.save(activitieResident));
        }
        throw new NotFoundException("Entitat dependent no existeix");
    }

    public void deleteActivitiesResidents(Long id, Long activitiResidentId) throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.DELETE_ACTIVIDAD_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.DELETE_ACTIVIDAD_PRIVILEGE);
        }
        if (activitiesResidentsRepository.findByIdAndResident_Id(activitiResidentId, id).isPresent()) {
            activitiesResidentsRepository.deleteById(activitiResidentId);
        }
    }

    public PaginateResponse findAttentionsByResident(Long id, Map<String, String> params, Pageable page) throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.READ_ATENCION_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.READ_ATENCION_PRIVILEGE);
        }
        params.put("resident.id", id.toString());
        Specification<AttentionsResidents> spec = Specification.where(new AttentionsResidentsSpecification(params));
        Page<AttentionsResidents> resultPage = attentionsResidentsRepository.findAll(spec, page);
        List<AttentionsResidentDTO> resultDTO = resultPage.getContent().stream().map(ar -> ConvertToDTO.convert(ar)).collect(Collectors.toList());
        PaginateResponse p = new PaginateResponse(resultDTO);
        p.setPaging(resultPage);
        return p;
    }

    public AttentionsResidentDTO saveAttentionsResidents(AttentionsResidentDTO residentsNew) throws PrivilegesException, ValidationServiceException, NotFoundException {
        if (!privilegesChecker.hasPrivilege(Constants.CREATE_ATENCION_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.CREATE_ATENCION_PRIVILEGE);
        }
        Optional<Residents> resident = findResidentsById(residentsNew.getResidentId());
        Optional<User> user = userService.findUserById(residentsNew.getUserId());
        Optional<Attentions> attention = attentionsService.findAttentionsById(residentsNew.getAttentionId());
        AttentionsResidents newAttentionResident = new AttentionsResidents();

        if (attention.isPresent() && user.isPresent() && resident.isPresent()) {
            newAttentionResident.setAttention(attention.get());
            newAttentionResident.setUser(user.get());
            newAttentionResident.setResident(resident.get());
            newAttentionResident.setObservacion(residentsNew.getObservacion());
            newAttentionResident.setDataAtencio(residentsNew.getDataAtencio());
            newAttentionResident.setComprovat(residentsNew.isComprovat());
            return ConvertToDTO.convert(attentionsResidentsRepository.save(newAttentionResident));
        }
        throw new NotFoundException("Entitat dependent no existeix");
    }

    public AttentionsResidentDTO updateAttentionsResidents(AttentionsResidentDTO residentsNew) throws PrivilegesException, ValidationServiceException, NotFoundException {
        if (!privilegesChecker.hasPrivilege(Constants.UPDATE_ATENCION_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.UPDATE_ATENCION_PRIVILEGE);
        }
        AttentionsResidents attentionsResidents = attentionsResidentsRepository.findById(residentsNew.getId()).orElse(null);
        if (attentionsResidents == null) {
            throw new NotFoundException("El registre no existeix");
        }
        Optional<Attentions> attentions = attentionsService.findAttentionsById(residentsNew.getAttentionId());
        Optional<User> user = userService.findUserById(residentsNew.getUserId());
        Optional<Residents> resident = findResidentsById(residentsNew.getResidentId());

        if (attentions.isPresent() && user.isPresent() && resident.isPresent()) {
            attentionsResidents.setAttention(attentions.get());
            attentionsResidents.setUser(user.get());
            attentionsResidents.setResident(resident.get());
            attentionsResidents.setObservacion(residentsNew.getObservacion());
            attentionsResidents.setDataAtencio(residentsNew.getDataAtencio());
            attentionsResidents.setComprovat(residentsNew.isComprovat());
            return ConvertToDTO.convert(attentionsResidentsRepository.save(attentionsResidents));
        }
        throw new NotFoundException("Entitat dependent no existeix");
    }

    public void deleteAttentionsResidents(Long id, Long attentionResidentId) throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.DELETE_ATENCION_PRIVILEGE,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            throw new PrivilegesException(Constants.DELETE_ATENCION_PRIVILEGE);
        }
        if (attentionsResidentsRepository.findByIdAndResident_Id(attentionResidentId, id).isPresent()) {
            attentionsResidentsRepository.deleteById(attentionResidentId);
        }
    }
}
