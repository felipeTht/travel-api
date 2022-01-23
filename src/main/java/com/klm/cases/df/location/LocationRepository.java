package com.klm.cases.df.location;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location, String> {
	
	Page<Location> findByDescriptionLike(String description, Pageable pageable);

	Page<Location> findByDescriptionContainingIgnoreCase(String term, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM Location l WHERE l.description like '%location.airport%'")
    void cleanData();
	
}
