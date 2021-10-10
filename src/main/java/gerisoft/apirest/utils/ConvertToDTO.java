/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.utils;

import gerisoft.apirest.dto.*;
import gerisoft.apirest.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gurrea
 */
public class ConvertToDTO {

    public static UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        
        userDTO.setId(user.getId());
        userDTO.setDni(user.getDni());
        userDTO.setNom(user.getNom());
        userDTO.setCognom1(user.getCognom1());
        userDTO.setCognom2(user.getCognom2());
        userDTO.setDataNaixement(user.getDataNaixement());
        userDTO.setTelefon(user.getTelefon());
        userDTO.setPais(user.getPais());
        userDTO.setCiutat(user.getCiutat());
        userDTO.setDireccio(user.getDireccio());
        userDTO.setCodiPostal(user.getCodiPostal());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(convert(user.getRole()));
        userDTO.setUsername(user.getUsername());
        
        return userDTO;
    }

    public static RoleDTO convert(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        
        List<PriviliegesDTO> privilegesDTO = new ArrayList<PriviliegesDTO>();
        if (role.getPrivilieges() != null) {
            for(Priviliege p : role.getPrivilieges()) {
                privilegesDTO.add(convert(p));
            }
        }
        
        roleDTO.setPrivilieges(privilegesDTO);
        
        return roleDTO;
    }
    
    public static PriviliegesDTO convert(Priviliege priviliege){
        PriviliegesDTO priviliegeDTO = new PriviliegesDTO();
        
        priviliegeDTO.setId(priviliege.getId());
        priviliegeDTO.setName(priviliege.getName());
        /*
        List<RoleDTO> rolesDTO=new ArrayList<RoleDTO>();
        
        if(priviliege.getRoles()!=null){
            for(Role r : priviliege.getRoles()){
                rolesDTO.add(convert(r));
            }
        }
        priviliegeDTO.setRoles(rolesDTO);
        */
        return priviliegeDTO;
    }
    
    public static AttentionsDTO convert(Attentions attentions){
        AttentionsDTO attentionsDTO = new AttentionsDTO();
        attentionsDTO.setId(attentions.getId());
        attentionsDTO.setName(attentions.getName());

        return attentionsDTO;
    }
    public static ResidentsDTO convert(Residents resident){
        ResidentsDTO residentDTO = new ResidentsDTO();
        residentDTO.setId(resident.getId());
        residentDTO.setDni(resident.getDni());
        residentDTO.setNom(resident.getNom());
        residentDTO.setCognom1(resident.getCognom1());
        residentDTO.setCognom2(resident.getCognom2());
        residentDTO.setDataIngres(resident.getDataIngres());
        residentDTO.setDataBaixa(resident.getDataBaixa());
        residentDTO.setDataNaixement(resident.getDataNaixement());
        List<ActivitiesDTO> activitiesDTO= new ArrayList<ActivitiesDTO>();
        /*if(resident.getActivities()!=null){
            for(Activities a: resident.getActivities()){
                activitiesDTO.add(convert(a));
            }
        }*/
        residentDTO.setActivities(activitiesDTO);
        return residentDTO;
        
    }
    public static ActivitiesDTO convert(Activities activities){
        ActivitiesDTO activitiesDTO = new ActivitiesDTO();
        activitiesDTO.setId(activities.getId());
        activitiesDTO.setName(activities.getName());
        return activitiesDTO;
    }

    public static ActivitiesResidentDTO convert(ActivitiesResidents activities){
        ActivitiesResidentDTO activitiesResidentDTO = new ActivitiesResidentDTO();
        activitiesResidentDTO.setId(activities.getId());
        activitiesResidentDTO.setResidentId(activities.getResident().getId());
        activitiesResidentDTO.setUserId(activities.getUser().getId());
        activitiesResidentDTO.setActivitieId(activities.getActivitie().getId());
        activitiesResidentDTO.setDataActiviti(activities.getDataActiviti());
        activitiesResidentDTO.setObservacion(activities.getObservacion());
        return activitiesResidentDTO;
    }

    public static AttentionsResidentDTO convert(AttentionsResidents attention){
        AttentionsResidentDTO attentionsResidentDTO = new AttentionsResidentDTO();
        attentionsResidentDTO.setId(attention.getId());
        attentionsResidentDTO.setResidentId(attention.getResident().getId());
        attentionsResidentDTO.setUserId(attention.getUser().getId());
        attentionsResidentDTO.setAttentionId(attention.getAttention().getId());
        attentionsResidentDTO.setDataAtencio(attention.getDataAtencio());
        attentionsResidentDTO.setObservacion(attention.getObservacion());
        attentionsResidentDTO.setComprovat(attention.isComprovat());
        return attentionsResidentDTO;
    }

}
