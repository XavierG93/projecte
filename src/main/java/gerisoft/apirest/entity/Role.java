/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Gurrea
 */
@Entity
@Table(name = "role")//Es desa a la taula user de la bbdd
public class Role {

    public Role() {}
    
    public Role(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;
   
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "roles_privilieges",
        joinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_roles_privilieges_role_id")),
        inverseJoinColumns = @JoinColumn(name = "privilieges_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_roles_privilieges_priviliege_id"))
    )
    private List<Priviliege> privilieges;

    public List<Priviliege> getPrivilieges() {
        return privilieges;
    }

    public void setPriviliege(List<Priviliege> privilieges) {
        this.privilieges = privilieges;
    }
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

}
