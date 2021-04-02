/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class VetController {

	private static final String VIEWS_VET_CREATE_OR_UPDATE_FORM = "vets/createOrUpdateVetForm";
	
	private final VetService vetService;
	
//	@InitBinder("vet")
//	public void initVetBinder(WebDataBinder dataBinder) {
//		dataBinder.addCustomFormatter(new SpecialtyFormatter(vetService), "specialties");
//	}
	
	@Autowired
	public VetController(final VetService clinicService) {
		this.vetService = clinicService;
	}
	
	@ModelAttribute("listSpecialties")
	public List<Specialty> populateSpecialties() {
		return this.vetService.findSpecialties().stream().collect(Collectors.toList());
	}

	@GetMapping(value = { "/vets" })
	public String showVetList(final Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		final Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}
	
	@GetMapping(value = "/vets/new")
	public String initCreationForm(final Map<String, Object> model) {
		final Vet vet = new Vet();
		model.put("vet", vet);
		return VetController.VIEWS_VET_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/vets/new")
	public String processCreationForm(@Valid final Vet vet, final BindingResult result, final HttpServletRequest request) {
		if (result.hasErrors()) {
			return VetController.VIEWS_VET_CREATE_OR_UPDATE_FORM;
		}else {
			final String[] array = request.getParameterValues("specialties");
			this.vetService.addSpecialties(vet, array);
			this.vetService.saveVet(vet);
			return "redirect:/vets";
		}
	}
	
	@GetMapping(value = "/vets/{vetId}/edit")
	public String initUpdateVetForm(@PathVariable("vetId") final int vetId, final Model model) {
		final Optional<Vet> oVet = this.vetService.findVetById(vetId);
		if(oVet.isPresent()) {
			model.addAttribute(oVet.get());
			return VetController.VIEWS_VET_CREATE_OR_UPDATE_FORM;
		}else {
			model.addAttribute("message", "Vet not present!");
			return this.showVetList(model.asMap());
		}
	}
	
	@PostMapping(value = "/vets/{vetId}/edit")
	public String processUpdateVetForm(@Valid final Vet vet, final BindingResult result,
			@PathVariable("vetId") final int vetId, final HttpServletRequest request) {
		if (result.hasErrors()) {
			return VetController.VIEWS_VET_CREATE_OR_UPDATE_FORM;
		}else {
			final String[] array = request.getParameterValues("specialties");
			this.vetService.addSpecialties(vet, array);
			vet.setId(vetId);
			this.vetService.saveVet(vet);
			return "redirect:/vets";
		}
	}
	
	@GetMapping(value = { "/vets.xml"})
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for JSon/Object mapping
		final Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		return vets;
	}
	
	@GetMapping(value = "/vets/{id}/delete")
	public String deleteVet(@PathVariable("id") final int id, final RedirectAttributes redirectAttributes) {
		final Vet vet = this.vetService.deleteVetById(id);
		if(vet!=null) {
		
			redirectAttributes.addFlashAttribute("message","deletevetsuccess");
		}
		else {
			redirectAttributes.addFlashAttribute("message","deleteveterror");
		}
		
		return "redirect:/vets";
	}

}
