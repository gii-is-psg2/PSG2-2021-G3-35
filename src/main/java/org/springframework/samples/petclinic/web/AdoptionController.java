package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.PetitionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@Autowired
	OwnerService ownerService;
	
	@Autowired
	PetService petService;
	
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
	
	@PreAuthorize("hasAuthority('owner')")
	@GetMapping("/new")
	public String addAdoption(final ModelMap modelMap) {
		final Owner ownerIdLoggedIn = this.ownerService.findByUserUsername(SecurityContextHolder.getContext()
    		.getAuthentication().getName()).get();
		final List<Pet> pets = this.petService.findPetsWithNoOpenAdoptionByOwnerId(ownerIdLoggedIn.getId());
		final Adoption adoption = new Adoption();
		adoption.setOwner(ownerIdLoggedIn);
		adoption.setOpen(true);
		modelMap.put("petsList",pets);
		modelMap.put("adoption", adoption);
		
	
		return "adoptions/createOrUpdateAdoptionForm";
	}
	
	@PreAuthorize("hasAuthority('owner') && @isSameOwner.hasPermission(#adoption.pet.owner.id) && @isSameOwner.hasPermission(#adoption.owner.id)")
	@PostMapping("/new")
	public String addAdoption(final @Valid Adoption adoption, final BindingResult result,
			final ModelMap modelMap, final RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			final Owner ownerIdLoggedIn = this.ownerService.findByUserUsername(SecurityContextHolder.getContext()
	    		.getAuthentication().getName()).get();
			final List<Pet> pets = this.petService.findPetsWithNoOpenAdoptionByOwnerId(ownerIdLoggedIn.getId());
			modelMap.put("petsList", pets);
			modelMap.put("adoption", adoption);
			modelMap.put("message", result.getAllErrors());
			return "adoptions/createOrUpdateAdoptionForm";
		}
		adoption.setPublishDate(LocalDate.now());
		adoption.setOpen(true);
		this.adoptionService.createAdoption(adoption);
		redirectAttributes.addFlashAttribute("message", "adoptioncreatesuccess");
		return "redirect:/";
		
	}
	

}
