/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gerisoft.apirest.dto.RoleDTO;
import java.util.List;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author Gurrea
 */
public class PriviliegesDTO {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   

   
   
    private Long id;
    @NotBlank
    private String name;

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }
    
    @JsonIgnoreProperties("privilieges")    
    private List<RoleDTO> roles;
    
    
}
