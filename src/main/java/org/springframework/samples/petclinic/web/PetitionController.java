package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Petition;
import org.springframework.samples.petclinic.model.PetitionStatus;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetitionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.AllRoomsBookedException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class PetitionController {

	
	private final PetitionService petitionService;
	private final UserService userService;
	private final AdoptionService adoptionService;
	private final OwnerService ownerService;

	
	@Autowired
	public PetitionController(final PetitionService petitionService, final UserService userService, final AdoptionService adoptionService, final OwnerService ownerService) {

		this.petitionService = petitionService;
		this.userService = userService;
		this.adoptionService = adoptionService;
		this.ownerService = ownerService;
	}
	
	@GetMapping(value = "/petitions")
//	@PreAuthorize("authenticated()")
	public String showPetitions(final Map<String, Object> model) {
		model.put("petitions", this.petitionService.findAll());
		return "petitions/petitionsList";
	}
	
	@GetMapping(value = "/petitions/mypetitions")
//	@PreAuthorize("authenticated()")
	public String showMyPetitions(final Map<String, Object> model) {
		model.put("petitions", this.petitionService.findMyPetitions(this.ownerService.getPrincipal().getId()));
		return "petitions/myPetitionsList";
	}
	
	@GetMapping(value = "/petitions/mypetitions/{petitionId}/delete")
	@PreAuthorize("authenticated() && @isSamePetitionOwner.hasPermission(#petitionId)")
	public String deletePetition(@PathVariable("petitionId") final int petitionId, final ModelMap model, final RedirectAttributes redirectAttributes) {
			final Petition res = this.petitionService.deletePetition(petitionId);
			if (res==null) {
				redirectAttributes.addFlashAttribute("message", "deletepetitionerror");
			}
			else {
				redirectAttributes.addFlashAttribute("message", "deletepetitionsuccess");
			}
			return "redirect:/petitions/mypetitions";
	}
	

	@GetMapping("/adoptions/{adoptionId}/petitions/new")
	public String initAddPetition(@PathVariable("adoptionId") final int adoptionId, ModelMap modelMap) {
		
		final Petition petition = new Petition();
		petition.setApplicant(this.ownerService.getPrincipal());
		petition.setStatus(PetitionStatus.PENDIENTE);
		final Adoption adoption = this.adoptionService.getById(adoptionId);
		
		modelMap.put("petition", petition);
		modelMap.put("adoption", adoption);
		
		return "petitions/createOrUpdatePetitionForm";
	}
	
	@PostMapping("/adoptions/{adoptionId}/petitions/new")
	public String processNewPetition(@PathVariable("adoptionId") final int adoptionId, @Valid final Petition petition, final BindingResult result,
			final ModelMap modelMap, final RedirectAttributes redirectAttributes) {

		modelMap.put("buttonCreate", true);
		Adoption adoption = this.adoptionService.getById(adoptionId);
		// Si al validarlo, encontramos errores:
		if (result.hasErrors()) {
			modelMap.put("adoption", adoption);
			modelMap.put("petition", petition);
			return "petitions/createOrUpdatePetitionForm";
		}
		// Si al validarlo, no hallamos ningún error:
		else {
			
			adoption.addPetition(petition);
			petition.setAdoption(adoption);
			petition.setApplicant(this.ownerService.getPrincipal());
			
			this.petitionService.savePetition(petition);
			return "redirect:/adoptions/all";
		}
	}
	
	@GetMapping(value = "/petitions/mypetitions/{petitionId}/edit")
	public String initUpdateForm(@PathVariable("petitionId") final int petitionId, final ModelMap model) {
		final Petition petition = this.petitionService.findPetitionById(petitionId);
		model.put("petition", petition);
		model.put("adoption", petition.getAdoption());
		return "petitions/createOrUpdatePetitionForm";
	}

    
        @PostMapping(value = "/petitions/mypetitions/{petitionId}/edit")
	public String processUpdateForm(@Valid final Petition petition, final BindingResult result, final Owner owner,@PathVariable("petitionId") final int petitionId, final ModelMap model
		,final RedirectAttributes redirectAttributes) {
        
        final Adoption adoption = this.petitionService.findPetitionById(petitionId).getAdoption();
        
		if (result.hasErrors()) {
			
			model.put("petition", petition);
			model.put("adoption", adoption);
			redirectAttributes.addFlashAttribute("message", "editpetitionerror");

			return "petitions/createOrUpdatePetitionForm";
		}
		else {
			petition.setAdoption(adoption);
			petition.setApplicant(this.petitionService.findPetitionById(petitionId).getApplicant());
			this.petitionService.savePetition(petition);
			redirectAttributes.addFlashAttribute("message", "editpetitionsuccess");
			return "redirect:/petitions/mypetitions";
		}
	}
	
}
