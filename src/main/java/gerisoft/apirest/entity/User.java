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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 *
 * @author Gurrea
 */
@Entity
@Table(name = "user")//Es desa a la taula user de la bbdd
public class User {

    @Id
    @Column(name = "id")//Es desa a la columna id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Es autoincrementat
    private Long id;
    @Column(nullable = false, unique = true, length = 32)
    private String dni;
    @Column(name = "Nom", nullable = false)
    private String nom;

    @Column(name = "Username", nullable = false)
    private String username;
    @Column(name = "Password", nullable = false)
    private String password;
    @Column(name = "Cognom1", nullable = false)
    private String cognom1;
    @Column(name = "Cognom2", nullable = false)
    private String cognom2;
    @Column(name = "DataNaixement", nullable = false)
    private Date dataNaixement;
    @Column(name = "telefon", nullable = false, unique = true)
    private String telefon;
    @Column(name = "Pais", nullable = false)
    private String pais;
    @Column(name = "Ciutat", nullable = false)
    private String ciutat;
    @Column(name = "Direccio", nullable = false)
    private String direccio;
    @Column(name = "CodiPostal", nullable = false)
    private String codiPostal;
    @Column(name = "Email", nullable = false, unique = true)
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiutat() {
        return ciutat;
    }

    public void setCiutat(String ciutat) {
        this.ciutat = ciutat;
    }

    public String getDireccio() {
        return direccio;
    }

    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    public String getCodiPostal() {
        return codiPostal;
    }

    public void setCodiPostal(String codiPostal) {
        this.codiPostal = codiPostal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
