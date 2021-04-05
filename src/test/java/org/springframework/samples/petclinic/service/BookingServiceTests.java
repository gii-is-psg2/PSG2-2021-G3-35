package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;

import org.assertj.core.api.Assertions;
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
		final Pet pet7 = this.petService.findPetById(7);
		final Booking booking = new Booking();
		booking.setPet(pet7);
		booking.setStartDate(LocalDate.now().plusDays(2));
		booking.setEndDate(LocalDate.now().plusDays(10));
		booking.setRoom(20);
		try {
			this.bookingService.saveBooking(booking);
		} catch (final AllRoomsBookedException e) {
			System.out.println("Error inesperado");
		}
		Assertions.assertThat(booking.getId()).isNotNull();
	}
	
	@Test
	void shouldFindUsedRooms() throws Exception {
		final Collection<Integer> bookings = this.bookingService.findUsedRooms(LocalDate.of(2021, 03, 21), LocalDate.of(2021, 03, 25));
		Assertions.assertThat(bookings.size()).isEqualTo(2);
	}
	
	@Test
	@Transactional
	void shouldDeleteBooking() {
		final Booking result = this.bookingService.deleteBooking(1);
		Assertions.assertThat(result).isNotNull();
	}
	
	@Test
	@Transactional
	void shouldNotDeleteBooking() {
		final Booking result = this.bookingService.deleteBooking(122);
		Assertions.assertThat(result).isNull();
	}
	
	
}
