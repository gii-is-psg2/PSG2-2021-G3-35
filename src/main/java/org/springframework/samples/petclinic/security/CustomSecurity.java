package org.springframework.samples.petclinic.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Configuration
public class CustomSecurity {
	
	@Autowired
	OwnerService ownerService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	VetService vetService;
	
	@Autowired
	PetService petService;
	
	@Autowired
	BookingService bookingService;
	
	@Component("isSameOwner")
	public class IsSameOwner {
		
	    public boolean hasPermission(final int id) {
	    	final int ownerIdLoggedIn = CustomSecurity.this.ownerService.findByUserUsername(SecurityContextHolder.getContext()
	    		.getAuthentication().getName()).get().getId();

	        return id==ownerIdLoggedIn;
	    }
	    
	    
	}
	
	
	@Component("isSameBookingOwner")
	public class IsSameBookingOwner {
		
	    public boolean hasPermission(final int id) {
	    	try {
	    	final String userOfDeletion= CustomSecurity.this.bookingService.findBookingById(id).getPet().getOwner().getUser().getUsername();
	    	final String userAuth = SecurityContextHolder.getContext().getAuthentication().getName();
	        return userOfDeletion.equals(userAuth);
	        
	    	}catch(final Exception e) {
	    		return false;
	    	}
	    }
	    
	    
	}
	
	@Component("isSamePetOwner")
	public class IsSamePetOwner {
		
	    public boolean hasPermission(final int id) {
	    	
		    	try {
		    	final String userOfDeletion= CustomSecurity.this.petService.findPetById(id).getOwner().getUser().getUsername();
		    	final String userAuth = SecurityContextHolder.getContext().getAuthentication().getName();
		        return userOfDeletion.equals(userAuth);
		        
		    	}catch(final Exception e) {
		    		return false;
		    	}
	    }
	}

}
