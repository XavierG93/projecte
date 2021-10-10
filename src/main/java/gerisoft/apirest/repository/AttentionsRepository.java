/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.repository;
import gerisoft.apirest.entity.Activities;
import gerisoft.apirest.entity.Attentions;
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
public interface AttentionsRepository extends PagingAndSortingRepository<Attentions, Long>, JpaSpecificationExecutor<Attentions> {
    //Repositori de la classe Attentions
    @Query("SELECT a FROM Attentions a WHERE a.name = :name AND a.id <> :id")
    Optional<Attentions> findByNomExcludingId(@Param("name") String name,@Param("id") Long id);
    Optional<Attentions> findByName(@Param("name") String name);
}
