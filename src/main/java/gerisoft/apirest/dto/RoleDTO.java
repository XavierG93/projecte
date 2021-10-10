/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author Gurrea
 */
public class RoleDTO {

    private Long id;
    @NotBlank
    private String name;
    @JsonIgnoreProperties("role")
    private List<UserDTO> users;
    @JsonIgnoreProperties("role")
    private List<PriviliegesDTO> privilieges;

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

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public List<PriviliegesDTO> getPrivilieges() {
        return privilieges;
    }

    public void setPrivilieges(List<PriviliegesDTO> privilieges) {
        this.privilieges = privilieges;
    }

}
