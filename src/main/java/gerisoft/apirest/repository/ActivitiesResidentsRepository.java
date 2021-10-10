package gerisoft.apirest.repository;

import gerisoft.apirest.entity.ActivitiesResidents;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ActivitiesResidentsRepository extends PagingAndSortingRepository<ActivitiesResidents, Long>, JpaSpecificationExecutor<ActivitiesResidents> {

    Optional<ActivitiesResidents> findByIdAndResident_Id(@Param("id") Long id, @Param("resident_id") Long residentId);
}
