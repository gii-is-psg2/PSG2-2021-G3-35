package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.stereotype.Controller;

@Controller
public class DonationController {
	
	private DonationService donationService;

	@Autowired
	public DonationController(DonationService donationService) {
		this.donationService = donationService;
	}
	
}
