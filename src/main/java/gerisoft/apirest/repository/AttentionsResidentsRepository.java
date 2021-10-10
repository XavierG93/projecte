package gerisoft.apirest.repository;

import gerisoft.apirest.entity.AttentionsResidents;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AttentionsResidentsRepository  extends PagingAndSortingRepository<AttentionsResidents, Long>, JpaSpecificationExecutor<AttentionsResidents> {

    Optional<AttentionsResidents> findByIdAndResident_Id(@Param("id") Long id, @Param("resident_id") Long residentId);

}
