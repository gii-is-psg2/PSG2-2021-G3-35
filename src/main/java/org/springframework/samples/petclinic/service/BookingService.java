package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.samples.petclinic.service.exceptions.AllRoomsBookedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

	private final BookingRepository bookingRepository;

	private final OwnerService ownerService;

	private final PetService petService;

	@Autowired
	public BookingService(final BookingRepository bookingRepository, final OwnerService ownerService,
			final PetService petService) {
		this.bookingRepository = bookingRepository;
		this.ownerService = ownerService;
		this.petService = petService;
	}

	@Transactional
	public void saveBooking(final Booking booking) throws AllRoomsBookedException {
		Collection<Integer> usedRooms = this.bookingRepository.findUsedRooms(booking.getStartDate(),
				booking.getEndDate());
		if (usedRooms.size() < 20) {
			Boolean aux = true;
			int possibleRoom = 1;
			while (aux) {
				if (usedRooms.contains(possibleRoom))
					possibleRoom += 1;
				else
					aux = false;
			}
			booking.setRoom(possibleRoom);
			this.bookingRepository.save(booking);
		} else
			throw new AllRoomsBookedException();
	}

	// No he conseguido que borre, no me da error aparentemente así que no entiendo
	// bien el problema
	@Transactional
	public Booking deleteBooking(final int bookingId) {
		final Booking booking = this.findBookingById(bookingId);
		if (booking == null) {
			return null;
		} else {
			this.bookingRepository.deleteById(bookingId);
			return booking;
		}

	}

	public Booking findBookingById(final int bookingId) {
		return this.bookingRepository.findBookingById(bookingId);
	}

	public Booking createBooking() {
		final Booking res = new Booking();
		return res;
	}

	public Collection<Integer> findUsedRooms(final LocalDate startDate, final LocalDate endDate) {

		return this.bookingRepository.findUsedRooms(startDate, endDate);
	}

}
