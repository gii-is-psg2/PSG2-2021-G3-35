package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.PetitionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/adoptions")

public class AdoptionController {
	
	@Autowired
	AdoptionService adoptionService;
	
	@Autowired
	PetitionService petitionService;
	
	@GetMapping("/all")
	public String showAllAdoptions(final Map<String, Object> model) {
		model.put("adoptions", this.adoptionService.getAllAdoptions());
		return "adoptions/list";
	}
	
	@GetMapping("/{adoptionid}")
	public ModelAndView showAdoptionDetails(@PathVariable("adoptionid") final int adoptionId) {
		final ModelAndView mav = new ModelAndView("adoptions/adoptionDetails");
		mav.addObject("adoption", this.adoptionService.getById(adoptionId));
		mav.addObject("petitions", this.petitionService.findPetitionsByAdoptionId(adoptionId));
		return mav;
	}
	
	@GetMapping("/{adoptionId}/delete")
	@PreAuthorize("hasAuthority('admin') || hasAuthority('owner') && @isSameAdoptionOwner.hasPermission(#adoptionId)")
	public String deleteAdoption(@PathVariable("adoptionId") final int adoptionId, final ModelMap model, final RedirectAttributes redirectAttributes) {
		final Adoption res = this.adoptionService.deleteAdoption(adoptionId);
		
		if(res==null) {
			redirectAttributes.addFlashAttribute("message","deleteadoptionerror");
		
		}else {
			redirectAttributes.addFlashAttribute("message", "deleteadoptionsuccess");
			
		}
		return "redirect:/";
		
		
	}
	

}
