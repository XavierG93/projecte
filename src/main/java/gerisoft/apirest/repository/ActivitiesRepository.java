/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.repository;

import gerisoft.apirest.entity.Activities;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Gurrea
 */
/*
Implementem la clase de java spring JpaSpecificationExecutor per a fer la paginacio i PagingAndSortingRepository per a fer la ordenacio
*/
public interface ActivitiesRepository extends PagingAndSortingRepository<Activities, Long>, JpaSpecificationExecutor<Activities>{
    /*
    PagingAndSortingRepository permet especificar els elements a cercar i l'ordenacio
    */
    //Repositori de la classe Activities 
    void save(Optional<Activities> attentionsToUpdate);

    Optional<Activities> findByName(@Param("name") String name);

    @Query("SELECT a FROM Activities a WHERE a.name = :name AND a.id <> :id")
    Optional<Activities> findByNomExcludingId(@Param("name") String name,@Param("id") Long id);
    
}
