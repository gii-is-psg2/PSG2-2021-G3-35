package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.samples.petclinic.service.exceptions.AllRoomsBookedException;
import org.springframework.samples.petclinic.service.exceptions.RoomAlreadyBookedForPet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

	private final BookingRepository bookingRepository;



	@Autowired
	public BookingService(final BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	@Transactional(rollbackFor = RoomAlreadyBookedForPet.class)
	public void saveBooking(final Booking booking) throws AllRoomsBookedException, RoomAlreadyBookedForPet {
		Collection<Booking> usedRoomsBookings = this.bookingRepository.findUsedRooms(booking.getStartDate(), booking.getEndDate());
		Collection<Integer> usedRooms = usedRoomsBookings.stream().map(b->b.getRoom()).collect(Collectors.toList());
		if (usedRooms.size() < 20) {
			System.out.println("=========================================================");
			System.out.println(usedRoomsBookings.stream().anyMatch(b->b.getPet().getId().equals(booking.getPet().getId())));
			if (!usedRoomsBookings.stream().anyMatch(b->b.getPet().getId().equals(booking.getPet().getId()))) {
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
				throw new RoomAlreadyBookedForPet();
		} else
			throw new AllRoomsBookedException();
	}

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
		return new Booking();
	}

	public Collection<Booking> findUsedRooms(final LocalDate startDate, final LocalDate endDate) {

		return this.bookingRepository.findUsedRooms(startDate, endDate);
	}

}
