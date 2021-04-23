package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Petition;
import org.springframework.samples.petclinic.service.PetitionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class PetitionController {

	
	private final PetitionService petitionService;
	private final UserService userService;


	
	@Autowired
	public PetitionController(final PetitionService petitionService, final UserService userService) {

		this.petitionService = petitionService;
		this.userService = userService;
	}
	
	@GetMapping(value = "/petitions")
	@PreAuthorize("authenticated()")
	public String showPetitions(final Map<String, Object> model) {
		model.put("petitions", this.petitionService.findAll());
		return "petitions/petitionsList";
	}
	
	@GetMapping(value = "/petitions/mypetitions")
	@PreAuthorize("authenticated()")
	public String showMyPetitions(final Map<String, Object> model) {
		model.put("petitions", this.petitionService.findMyPetitions(this.userService.getPrincipal().getUsername()));
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
	


	
}
