package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Petition;

public interface PetitionRepository extends CrudRepository<Petition, Integer>{
	@Modifying
	@Query("DELETE FROM Petition p where p.id =:id")
	void deleteById(@Param("id") int id);
	
	@Query("SELECT petition FROM Petition petition WHERE petition.id =:petitionID")
	public Petition findPetitionById(@Param("petitionID")int petitionID);

	@Query("SELECT petition FROM Petition petition WHERE petition.applicant.id =:id")
	public Iterable<Petition> findPetitionsByOwner(@Param("id")String id);
	

}
