package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.exceptions.AllRoomsBookedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BookingServiceTests {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private PetService petService;
	
	@Test
	@Transactional
	public void shouldAddNewBookingForPet() {
		Pet pet7 = this.petService.findPetById(7);
		Booking booking = new Booking();
		booking.setPet(pet7);
		booking.setStartDate(LocalDate.of(2021, 03, 24));
		booking.setEndDate(LocalDate.of(2021, 03, 26));
		booking.setRoom(20);
		try {
			this.bookingService.saveBooking(booking);
		} catch (AllRoomsBookedException e) {
			System.out.println("Error inesperado");
		}
		assertThat(booking.getId()).isNotNull();
	}
	
	@Test
	void shouldFindUsedRooms() throws Exception {
		Collection<Integer> bookings = this.bookingService.findUsedRooms(LocalDate.of(2021, 03, 21), LocalDate.of(2021, 03, 25));
		assertThat(bookings.size()).isEqualTo(2);
	}
	
	
}
