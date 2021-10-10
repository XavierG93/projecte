/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Gurrea
 */
@Entity
@Table(name = "residents")//Es desa a la taula user de la bbdd
public class Residents {

    @Id
    @Column(name = "id")//Es desa a la columna id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Es autoincrementat
    private Long id;
    @Column(nullable = false, unique = true, length = 32)
    @NotNull (message = "DNI és obligatori")
    private String dni;
    @NotNull (message = "Nom és obligatori")
    private String nom;
    @NotNull (message = "cognom1 és obligatori")
    private String cognom1;
    @NotNull (message = "cognom2 és obligatori")
    private String cognom2;
    @NotNull (message = "data_naixement és obligatori")
    private Date dataNaixement;
    @NotNull (message = "data_ingres és obligatori")
    private Date dataIngres;
    private Date dataBaixa;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom1() {
        return cognom1;
    }

    public void setCognom1(String cognom1) {
        this.cognom1 = cognom1;
    }

    public String getCognom2() {
        return cognom2;
    }

    public void setCognom2(String cognom2) {
        this.cognom2 = cognom2;
    }

    public Date getDataNaixement() {
        return dataNaixement;
    }

    public void setDataNaixement(Date dataNaixement) {
        this.dataNaixement = dataNaixement;
    }

    public Date getDataIngres() {
        return dataIngres;
    }

    public void setDataIngres(Date dataIngres) {
        this.dataIngres = dataIngres;
    }

    public Date getDataBaixa() {
        return dataBaixa;
    }

    public void setDataBaixa(Date dataBaixa) {
        this.dataBaixa = dataBaixa;
    }

}
