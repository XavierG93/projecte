/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerisoft.apirest.repository;

import gerisoft.apirest.entity.User;
import java.util.List;
import java.util.Optional;
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
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u WHERE u.role.name = :name")
    List<User> findAllByRole(@Param("name") String name);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByTelefon(@Param("telefon") String telefon);

    Optional<User> findByDni(String dni);

    @Query("SELECT u FROM User u WHERE u.dni = :dni AND  u.id <> :id")
    Optional<User> findByCifExcludingId(@Param("dni") String dni, @Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.id <> :id")
    Optional<User> findByEmailExcludingId(@Param("email") String email, @Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.telefon = :telefon AND  u.id <> :id")
    Optional<User> findByTelefonExcludingId(@Param("telefon") String phone, @Param("id") Long id);

}
