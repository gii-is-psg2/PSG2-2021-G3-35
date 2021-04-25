package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adoptions")

public class AdoptionController {
	
	@Autowired
	AdoptionService adoptionService;
	
	@GetMapping("/all")
	public String showAllAdoptions(final Map<String, Object> model) {
		model.put("adoptions", this.adoptionService.getAllAdoptions());
		return "adoptions/list";
	}
	
	@GetMapping("/{adoptionid}")
	public String showAdoptionDetails(@PathVariable("adoptionid") final int adoptionId, final Map<String, Object> model) {
		model.put("adoption", this.adoptionService.getById(adoptionId));
		return "adoptions/adoptionDetails";
	}
	

}
