/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author Gurrea
 */
public class ResidentsDTO {

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
        return DataNaixement;
    }

    public void setDataNaixement(Date DataNaixement) {
        this.DataNaixement = DataNaixement;
    }

    public Date getDataIngres() {
        return DataIngres;
    }

    public void setDataIngres(Date DataIngres) {
        this.DataIngres = DataIngres;
    }

    public Date getDataBaixa() {
        return DataBaixa;
    }

    public void setDataBaixa(Date DataBaixa) {
        this.DataBaixa = DataBaixa;
    }

   
    private Long id;
    @NotBlank
    private String dni;
    @NotBlank
    private String nom;
    @NotBlank
    private String cognom1;
    @NotBlank
    private String cognom2;
    @NotBlank
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date DataNaixement;
    @NotBlank
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date DataIngres;
    @NotBlank
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date DataBaixa;

    public List<ActivitiesDTO> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivitiesDTO> activities) {
        this.activities = activities;
    }
    @JsonIgnoreProperties("residents")
    private List<ActivitiesDTO> activities;

}
