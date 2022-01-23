package com.klm.cases.df.location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {
	
	Page<Location> findByDescriptionLike(String description, Pageable pageable);

	Page<Location> findByDescriptionContaining(String term, Pageable pageable);
	
}
