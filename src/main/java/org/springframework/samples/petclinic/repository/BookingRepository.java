package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Booking;

public interface BookingRepository extends CrudRepository<Booking, Integer>{

	
	@Query("SELECT booking FROM Booking booking WHERE booking.owner.id  =:ownerId")
	public Collection<Booking> findBookingsByOwnerId(@Param("ownerId") int ownerId);
}
