package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/causes/{causeId}")
public class DonationController {
	
	private DonationService donationService;
	private OwnerService ownerService;
	private CauseService causeService;

	private static final String viewDonationCreate = "causes/createDonationForm";
	private static final String viewCausesList = "redirect:/causes";
	
	@Autowired
	public DonationController(DonationService donationService, OwnerService ownerService, CauseService causeService) {
		this.donationService = donationService;
		this.ownerService = ownerService;
		this.causeService = causeService;
	}
	
	@ModelAttribute("owner")
	public Owner findOwner() {
		return this.ownerService.getPrincipal();
	}
	
	@GetMapping(value = "/donate")
	public String initCreationForm(@PathVariable("causeId") final int causeId, final Map<String,Object> model, final RedirectAttributes redirecAttributes) {
		final Optional<Cause> oCause = this.causeService.findCauseById(causeId);
		if(oCause.isEmpty()) {
			redirecAttributes.addFlashAttribute("message", "causenotfound");
			return DonationController.viewCausesList;
		}else if(!oCause.get().getState()){
			redirecAttributes.addFlashAttribute("message", "causecloseerror");
			return DonationController.viewCausesList;
		}else {
			final Donation donation = new Donation();
			model.put("donation", donation);
			return DonationController.viewDonationCreate;
		}
	}
	
	@PostMapping(value = "/donate")
	public String processCreationForm(@Valid final Donation donation, final BindingResult result, final RedirectAttributes redirecAttributes) {
		if(result.hasErrors()) {
			redirecAttributes.addFlashAttribute("message", "adddonationerror");
			return DonationController.viewDonationCreate;
		}else {
			redirecAttributes.addFlashAttribute("message", "adddonationsuccess");
			donation.setDate(LocalDate.now());
			this.donationService.saveDonation(donation);
			Cause c = donation.getCause();
			if(!causeService.getState(c)){
				c.setState(false);
				causeService.saveCause(c);
			}
			return DonationController.viewCausesList;
		}
	}
	
}
