package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VisitDeleteController {
	
	@Autowired
	PetService petService;
	
	@GetMapping("/owners/{ownerId}/visits/{visitId}/delete")
	public String deleteVisit(@PathVariable("ownerId") final int ownerId,@PathVariable("visitId") final int visitId, final RedirectAttributes redirectAttributes) {
		final Visit result = this.petService.deleteVisitById(visitId);
		if (result==null) {
			redirectAttributes.addFlashAttribute("message", "deletevisiterror");
		}
		else {
			
			redirectAttributes.addFlashAttribute("message", "deletevisitsuccess");
		}
		return "redirect:/owners/"+ownerId;
	}

}
