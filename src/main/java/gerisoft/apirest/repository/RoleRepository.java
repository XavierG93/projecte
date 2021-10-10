/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.repository;

import gerisoft.apirest.entity.Role;
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
public interface RoleRepository extends JpaRepository<Role, Long>{
    //Repositori de la classe Role 
    void save(Optional<Role> roleToUpdate);

    Optional<Role> findByName(@Param("name") String name);
    
    @Query("SELECT r FROM Role r WHERE r.name = :name AND  r.id <> :id")
    Optional<Role> findByNomExcludingId(@Param("name") String name,@Param("id") Long id);
}
