/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.repository;

import gerisoft.apirest.entity.Residents;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gurrea
 */
@Repository
public interface ResidentsRepository extends PagingAndSortingRepository<Residents, Long>, JpaSpecificationExecutor<Residents> {
    //Repositori de la classe Residents 
    void save(Optional<Residents> residentsToUpdate);

    Optional<Residents> findByDni(@Param("dni") String dni);
    
    @Query("SELECT r FROM Residents r WHERE r.dni = :dni AND r.id <> :id")
    Optional<Residents> findByCifExcludingId(@Param("dni") String dni,@Param("id") Long id);
    
}
