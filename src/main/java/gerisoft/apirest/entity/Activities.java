/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Gurrea
 */
@Entity
@Table(name = "activities")//Es desa a la taula user de la bbdd
public class Activities {

    @Id
    @Column(name = "id")//Es desa a la columna id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Es autoincrementat
    private Long id;
    @Column(nullable = false, unique = true, length = 32)
    private String name;

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
