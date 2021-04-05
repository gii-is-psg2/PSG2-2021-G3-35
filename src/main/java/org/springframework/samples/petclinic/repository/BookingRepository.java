package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Booking;

public interface BookingRepository extends CrudRepository<Booking, Integer>{
	
	
	@Query("SELECT booking.room FROM Booking booking WHERE (booking.startDate <=:startDate and booking.endDate >=:startDate) "
															+ "or (booking.startDate <=:endDate and booking.endDate >=:endDate)"
															+ "or (booking.startDate >=:startDate and booking.endDate <=:endDate)"
															+ "or (booking.startDate <=:startDate and booking.endDate >=:endDate)")
	public Collection<Integer> findUsedRooms(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	
	@Query("SELECT booking FROM Booking booking WHERE booking.id =:bookingId")
	public Booking findBookingById(@Param("bookingId")int bookingId);
	
	@Modifying
	@Query("DELETE FROM Booking booking WHERE booking.id =:bookingId")
	public void deleteById(@Param("bookingId")int bookingId);
}
