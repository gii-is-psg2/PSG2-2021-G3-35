package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.AllRoomsBookedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("owners/{ownerId}")
public class BookingController {

	
	private final BookingService bookingService;
	private final PetService petService;
	private final OwnerService ownerService;
	private static final String VISTA_EDICION_BOOKING= "bookings/createOrUpdateBookingForm";
	private static final String VIEW_LIST_BOOKING="bookings/bookingsList";
	private static final String VIEW_SHOW_BOOKING="bookings/editChapter";
	
	@Autowired
	public BookingController(final BookingService bookingService, final PetService petService, final OwnerService ownerService) {

		this.bookingService = bookingService;
		this.petService = petService;
		this.ownerService = ownerService;
	}
//	
//	@InitBinder
// 	public void setAllowedFields(WebDataBinder dataBinder) {
// 		dataBinder.setDisallowedFields("id");
// 	}
//			
	
	// Crear reserva
	

	@GetMapping("/bookings/new")
	public String initAddReview(@PathVariable("ownerId") final int ownerId, final ModelMap modelMap) {
		final Owner owner = this.ownerService.findOwnerById(ownerId);
		final Booking booking = this.bookingService.createBooking();
		
		final List<String> petsNames = owner.getPets().stream().map(p->p.getName()).collect(Collectors.toList());
		modelMap.put("owner", owner);
		modelMap.put("petsNames", petsNames);
		modelMap.put("booking", booking);
		
//		modelMap.put("usedRooms", this.bookingService.findUsedRooms(LocalDate.of(2021, 03, 21), LocalDate.of(2021, 03, 25)));
		
		return BookingController.VISTA_EDICION_BOOKING;
	}
	
	@PostMapping("/bookings/new")
	public String processNewReview(@PathVariable("ownerId") final int ownerId, @Valid final Booking booking, final BindingResult result,
			final ModelMap modelMap, final RedirectAttributes redirectAttributes) {
		
		modelMap.put("buttonCreate", true);
		final Owner owner = this.ownerService.findOwnerById(ownerId);
		// Si al validarlo, encontramos errores:
		if(result.hasErrors()) {
			final List<String> petsNames = owner.getPets().stream().map(p->p.getName()).collect(Collectors.toList());
			modelMap.put("booking", booking);
			modelMap.put("owner", owner);
			modelMap.put("petsNames", petsNames);
			return BookingController.VISTA_EDICION_BOOKING;
		}
		
		// Si al validarlo, no hallamos ningún error:
		
		else { 
			try {
				int i = 0;
				final List<Pet> pets = owner.getPets().stream().collect(Collectors.toList());
				Pet optPet = null;
				while(i < pets.size()) {
					if(pets.get(i).getName().equals(booking.getPet().getName())) {
						optPet = pets.get(i);
						break;
					}
					i++;
				}
				booking.setPet(optPet);
				System.out.println(booking);
				this.bookingService.saveBooking(booking);
				modelMap.addAttribute("messageSuccess", "¡La reserva fue un exito!");
				redirectAttributes.addFlashAttribute("message", "addbookingsuccess");
				redirectAttributes.addFlashAttribute("messageType", "success");
				return "redirect:/owners/{ownerId}";
			} catch (final AllRoomsBookedException e) {
				result.rejectValue("text", "AllRoomsBooked" ,"All our rooms are booked. Please try to book later.");
				return BookingController.VISTA_EDICION_BOOKING;
			}
			
		}
		
		}
	

	@GetMapping(value = "/bookings/{bookingId}/delete")
	@PreAuthorize("hasAuthority('admin') || hasAuthority('owner') && @isSameBookingOwner.hasPermission(#bookingId)")
	public String deleteBooking(@PathVariable("bookingId") final int bookingId, final ModelMap model, final RedirectAttributes redirectAttributes) {
			final Booking res = this.bookingService.deleteBooking(bookingId);
			if (res==null) {
				redirectAttributes.addFlashAttribute("message", "deletebookingerror");
			}
			else {
				redirectAttributes.addFlashAttribute("message", "deletebookingsuccess");
			}
			return "redirect:/owners/{ownerId}";
	}
	
}
