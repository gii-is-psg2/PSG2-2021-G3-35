package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

	private BookingRepository bookingRepository;
	
	private OwnerService ownerService;
	
	private PetService petService;
	
	private UserService userService;
	
	@Autowired
	public BookingService(BookingRepository bookingRepository, OwnerService ownerService, PetService petService, UserService userService) {
		this.bookingRepository = bookingRepository;
		this.ownerService = ownerService;
		this.petService = petService;
		this.userService = userService;
	}	
	
	
	public Collection<Booking> findBookingsByOwnerId(int storyId){
		return bookingRepository.findBookingsByOwnerId(storyId);
	}
	
	
	@Transactional
	public void saveBooking(Booking booking){
		
		booking.setOwner(ownerService.getPrincipal());
		bookingRepository.save(booking);		
		
	}
	
	public Booking createBooking(Owner owner){
		Booking res = new Booking();
		return res;
	}
	
	
	
}
