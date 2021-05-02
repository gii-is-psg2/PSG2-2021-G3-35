package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Donation;

public interface DonationRepository extends CrudRepository<Donation, Integer>{
	
}
