package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cause;

public interface CauseRepository extends CrudRepository<Cause, Integer> {
	
	@Query("SELECT SUM(d) FROM Donation d WHERE d.cause.id =:id ")
	public Double totalDonationsById(@Param("id")int id);
		
	
	
	
	
	
}
