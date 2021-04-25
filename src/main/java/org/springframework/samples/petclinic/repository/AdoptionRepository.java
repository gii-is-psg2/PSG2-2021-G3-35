package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Adoption;


public interface AdoptionRepository extends Repository<Adoption, Integer> {
	
	@Query("SELECT a FROM Adoption a WHERE a.owner.id =:id ORDER BY a.open DESC,a.publishDate DESC")
	public Collection<Adoption> findAllByOwnerId(@Param ("id") int id);
	
	@Query("SELECT a FROM Adoption a WHERE a.id =:id")
	public Adoption findById(@Param ("id") int id);
	
	@Query("SELECT a FROM Adoption a ORDER BY a.open DESC,a.publishDate DESC")
	public Collection<Adoption> findAll();
	
	@Modifying
	@Query("DELETE FROM Adoption a WHERE a.id =:id")
	public void deleteAdoptionById(@Param ("id") int id);

}
