/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 *
 * @author Gurrea
 */
/*
Classe que fara d'intermitja entre la bbdd
i els objectes en codi java
 */
public class UserDTO {

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
    private Date dataNaixement;
    @NotBlank
    private String telefon;
    @NotBlank
    private String pais;
    @NotBlank
    private String ciutat;
    @NotBlank
    private String direccio;
    @NotBlank
    private String codiPostal;
    @NotBlank
    private String email;
    @JsonIgnoreProperties("user")
    private RoleDTO role;

    private String username;
    @JsonIgnore
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

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

    public void setDataNaixement(Date DataNaixement) {
        this.dataNaixement = DataNaixement;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String Telefon) {
        this.telefon = Telefon;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String Pais) {
        this.pais = Pais;
    }

    public String getCiutat() {
        return ciutat;
    }

    public void setCiutat(String Ciutat) {
        this.ciutat = Ciutat;
    }

    public String getDireccio() {
        return direccio;
    }

    public void setDireccio(String Direccio) {
        this.direccio = Direccio;
    }

    public String getCodiPostal() {
        return codiPostal;
    }

    public void setCodiPostal(String CodiPostal) {
        this.codiPostal = CodiPostal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
