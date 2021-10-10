/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.repository;

import gerisoft.apirest.entity.Priviliege;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gurrea
 */
@Repository
public interface PriviliegeRepository extends JpaRepository<Priviliege, Long>{
     //Repositori de la classe Priviliege
    void save(Optional<Priviliege> priviliegeToUpdate);

    Optional<Priviliege> findById(@Param("id") String id);
    
    Optional<Priviliege> findByName(@Param("name") String name);
    
    @Query("SELECT p FROM Priviliege p WHERE p.name = :name AND p.id <> :id")
    Optional<Priviliege> findByNameExcludingId(@Param("name") String name,@Param("id") Long id);
    
}
