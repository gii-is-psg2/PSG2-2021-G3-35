package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.User;


public interface UserRepository extends  CrudRepository<User, String>{
	
	@Modifying
	@Query("DELETE User user WHERE user.username=:name")
	void deleteByName(@Param("name")String name);
	
}
