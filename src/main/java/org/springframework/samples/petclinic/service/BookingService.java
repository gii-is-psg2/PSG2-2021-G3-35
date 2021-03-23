package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.samples.petclinic.service.exceptions.AllRoomsBookedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

	private BookingRepository bookingRepository;
	
	private OwnerService ownerService;
	
	private PetService petService;
	
	
	@Autowired
	public BookingService(BookingRepository bookingRepository, OwnerService ownerService, PetService petService) {
		this.bookingRepository = bookingRepository;
		this.ownerService = ownerService;
		this.petService = petService;
	}	
	
	
	@Transactional
	public void saveBooking(Booking booking) throws AllRoomsBookedException{
		Collection<Integer> usedRooms = bookingRepository.findUsedRooms(booking.getStartDate(), booking.getEndDate());
		if(usedRooms.size() < 20) {
			Boolean aux = false;
			int possibleRoom = 1;
			while(aux) {
				if(usedRooms.contains(possibleRoom))
					possibleRoom += 1;
				else 
					aux = true;
			}
			booking.setRoom(possibleRoom);
			bookingRepository.save(booking);
		} else 
			throw new AllRoomsBookedException();		
	}
	
	//No he conseguido que borre, no me da error aparentemente así que no entiendo bien el problema
	@Transactional
	public void deleteBooking(int bookingId) {
		bookingRepository.deleteById(bookingId);
	}
	
	
	public Booking findBookingById(int bookingId) {
		return bookingRepository.findBookingById(bookingId);
	}


	public Booking createBooking(Owner owner){
		Booking res = new Booking();
		return res;
	}
	
	public Collection<Integer> findUsedRooms(LocalDate startDate, LocalDate endDate) {
		
		return bookingRepository.findUsedRooms(startDate, endDate);
	}
	
}
