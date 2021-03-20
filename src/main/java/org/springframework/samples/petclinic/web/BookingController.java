package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
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
	public BookingController(BookingService bookingService, PetService petService, OwnerService ownerService) {

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
	// Listar bookings.
	
	@GetMapping(value = { "/bookings" })
	public String listBookingOfOwner(@PathVariable("ownerId") int ownerId, ModelMap modelMap) {
		Iterable<Booking> bookings = this.bookingService.findBookingsByOwnerId(ownerId);
		modelMap.put("bookings", bookings);
		return VIEW_LIST_BOOKING;
	}
	
	
	
	
//	@GetMapping(value = { "/chapters/{chapterId}" })
//	public String showChapter(@PathVariable("chapterId") int chapterId, ModelMap modelMap) {
//		Chapter chapter = this.chapterService.findChapterById(chapterId);
//		modelMap.put("chapter", chapter);
//		return VIEW_SHOW_CHAPTER;
//	}
	
	// Crear reserva
	
	@GetMapping("/bookings/new")
	public String initAddReview(@PathVariable("ownerId") int ownerId, ModelMap modelMap) {
		Owner owner = this.ownerService.findOwnerById(ownerId);
		Booking booking = this.bookingService.createBooking(owner);
		
		
		modelMap.put("owner", owner);
		modelMap.put("booking", booking);
		
		return VISTA_EDICION_BOOKING;
	}
	
	@PostMapping("/bookings/new")
	public String processNewReview(@PathVariable("ownerId") int ownerId, @Valid Booking booking,  BindingResult result,
			ModelMap modelMap, RedirectAttributes redirectAttributes) {
		
		modelMap.put("buttonCreate", true);
		
		Owner owner = this.ownerService.findOwnerById(ownerId);
		// Si al validarlo, encontramos errores:
		if(result.hasErrors()) {
			modelMap.put("booking", booking);
			modelMap.put("owner", owner);
			System.out.println("====================================================================================");
			System.out.println(result.toString());
			return VISTA_EDICION_BOOKING;
		}
		
		// Si al validarlo, no hallamos ningún error:
		
		else { 
			bookingService.saveBooking(booking);
			modelMap.addAttribute("messageSuccess", "¡La reserva fue un exito!");
			redirectAttributes.addFlashAttribute("message", String.format("The booking for %s was created.", owner.getFirstName()));
			redirectAttributes.addFlashAttribute("messageType", "success");
			return "redirect:/bookings";
		}
		
		}
}
