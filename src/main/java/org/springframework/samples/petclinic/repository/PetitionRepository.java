package org.springframework.samples.petclinic.repository;

import java.util.Collection;

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
	public Iterable<Petition> findPetitionsByOwner(@Param("id")int id);
	
	@Query("SELECT petition FROM Petition petition WHERE petition.adoption.id =:adoptionId")
	public Collection<Petition> findPetitionsByAdoptionId(@Param("adoptionId")int adoptionId);

	@Query("SELECT petition  FROM Petition petition WHERE petition.adoption.id =:adoptionId and petition.id <>:petitionId")
	public Collection<Petition> findAllExcept(@Param("adoptionId")int adoptionId, @Param("petitionId")int petitionId);

//	@Query("UPDATE Petition p SET p.status = 'DENEGADA' WHERE p.id = :petitionId")
//	public Petition declinePetition(@Param("petitionId")int petitionId);
}
