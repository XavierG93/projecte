/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.utils;

import gerisoft.apirest.dto.ActivitiesDTO;
import gerisoft.apirest.dto.AttentionsDTO;
import gerisoft.apirest.dto.PriviliegesDTO;
import gerisoft.apirest.dto.ResidentsDTO;
import gerisoft.apirest.dto.RoleDTO;
import gerisoft.apirest.dto.UserDTO;
import gerisoft.apirest.entity.Activities;
import gerisoft.apirest.entity.Attentions;
import gerisoft.apirest.entity.Priviliege;
import gerisoft.apirest.entity.Residents;
import gerisoft.apirest.entity.Role;
import gerisoft.apirest.entity.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gurrea
 */
public class ConvertToEntity {

    public static User convertToEntity(UserDTO userDTO) {
        return convertToEntity(userDTO, false);

    }

    public static User convertToEntity(UserDTO userDTO, boolean withID) {
        User user = new User();
        if (withID) {
            user.setId(userDTO.getId());
        }
        user.setDni(userDTO.getDni());
        user.setNom(userDTO.getNom());
        user.setCognom1(userDTO.getCognom1());
        user.setCognom2(userDTO.getCognom2());
        user.setDataNaixement(userDTO.getDataNaixement());
        user.setTelefon(userDTO.getTelefon());
        user.setPais(userDTO.getPais());
        user.setCiutat(userDTO.getCiutat());
        user.setDireccio(userDTO.getDireccio());
        user.setCodiPostal(userDTO.getCodiPostal());
        user.setEmail(userDTO.getEmail());
        user.setRole(convertToEntity(userDTO.getRole(), true));
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        
        return user;

    }

    public static Role convertToEntity(RoleDTO roleDTO) {
        return convertToEntity(roleDTO, false);

    }

    public static Role convertToEntity(RoleDTO roleDTO, boolean withID) {
        Role role = new Role(roleDTO.getName());
        if (withID) {
            role.setId(roleDTO.getId());
        }
        List<Priviliege> privileges = new ArrayList<Priviliege>();
        if (roleDTO.getPrivilieges() != null) {
            for (PriviliegesDTO p : roleDTO.getPrivilieges()) {
                privileges.add(convertToEntity(p));
            }
        }
        role.setPriviliege(privileges);

        List<User> user = new ArrayList<User>();
        if (roleDTO.getUsers() != null) {
            for (UserDTO u : roleDTO.getUsers()) {
                user.add(convertToEntity(u));
            }
        }

        role.setUsers(user);
        return role;

    }

    public static Priviliege convertToEntity(PriviliegesDTO priviliegeDTO) {
        Priviliege priviliege = new Priviliege(priviliegeDTO.getName());

        List<Role> role = new ArrayList<Role>();
        if (priviliegeDTO.getRoles() != null) {
            for (RoleDTO r : priviliegeDTO.getRoles()) {
                role.add(convertToEntity(r));
            }
        }
        priviliege.setRoles(role);
        return priviliege;
    }

    public static Attentions convertToEntity(AttentionsDTO attentionsDTO) {
        return convertToEntity(attentionsDTO, false);
    }

    public static Attentions convertToEntity(AttentionsDTO attentionsDTO, boolean withID) {

        Attentions attentions = new Attentions();
        if (withID) {
            attentions.setId(attentionsDTO.getId());
        }
        attentions.setName(attentionsDTO.getName());
        return attentions;
    }

    public static Residents convertToEntity(ResidentsDTO residentDTO) {
        return convertToEntity(residentDTO, false);
    }

    public static Residents convertToEntity(ResidentsDTO residentDTO, boolean withID) {
        Residents resident = new Residents();
        if (withID) {
            resident.setId(residentDTO.getId());
        }
        resident.setDni(residentDTO.getDni());
        resident.setNom(residentDTO.getNom());
        resident.setCognom1(residentDTO.getCognom1());
        resident.setCognom2(residentDTO.getCognom2());
        resident.setDataIngres(residentDTO.getDataIngres());
        resident.setDataBaixa(residentDTO.getDataBaixa());
        resident.setDataNaixement(residentDTO.getDataNaixement());

        List<Activities> activities = new ArrayList<Activities>();
        if (residentDTO.getActivities() != null) {
            for (ActivitiesDTO a : residentDTO.getActivities()) {
                activities.add(convertToEntity(a));
            }
        }

        //resident.setActivities(activities);

        return resident;
    }

    public static Activities convertToEntity(ActivitiesDTO activitiesDTO) {
        return convertToEntity(activitiesDTO, false);
    }

    public static Activities convertToEntity(ActivitiesDTO activitiesDTO, boolean withID) {
        Activities activities = new Activities();
        if (withID) {
            activities.setId(activitiesDTO.getId());
        }
        activities.setName(activitiesDTO.getName());

        return activities;
    }

}
