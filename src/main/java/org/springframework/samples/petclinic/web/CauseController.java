package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/causes")
public class CauseController {
	private CauseService causeService;
	private OwnerService ownerService;
	private static final String views_cause_create_or_update = "causes/createOrUpdateCauseForm";
	private static final String viewsCausesList = "redirect:/causes";
	
	@Autowired
	public CauseController(CauseService causeService, OwnerService ownerService) {
		this.causeService = causeService;
		this.ownerService = ownerService;
	}
	
	@ModelAttribute("owner")
	public Owner findOwner() {
		return this.ownerService.getPrincipal();
	}
	
	@GetMapping(value = "/{causeId}/delete")
	//@PreAuthorize("hasAuthority('admin') || hasAuthority('owner') && ")
	public String deleteCause(@PathVariable("causeId") final Integer causeId, final ModelMap model, RedirectAttributes redirectAttributes ) {
		final Cause salida = this.causeService.deleteCause(causeId);
		if(salida == null) {
			redirectAttributes.addFlashAttribute("message", "deletecauseerror");
		}else {
			redirectAttributes.addFlashAttribute("message", "deletecausesucces");
		}
		return "redirect:/causes/";
	}
	
	@GetMapping
	public String listCause(final Map<String, Object> model) {
		List<Cause> listCauses = causeService.findCauses();
		//model.put("totalAcum", totalAcum);
		model.put("causes", listCauses);
		return "/causes/causesList";			
	}
	
	@GetMapping(value = "/new")
	public String initCreationForm(final Map<String,Object> model) {
		final Cause cause = new Cause();
		model.put("cause", cause);
		return CauseController.views_cause_create_or_update;
	}
	
	@PostMapping(value = "/new")
	public String processCreationForm(@Valid final Cause cause, final BindingResult result, final HttpServletRequest request, final RedirectAttributes redirecAttributes) {
		if(result.hasErrors()) {
			redirecAttributes.addFlashAttribute("message", "addcauseerror");
			return CauseController.views_cause_create_or_update;
		}else {
			redirecAttributes.addFlashAttribute("message", "adcausesucces");
			this.causeService.saveCause(cause);
			return viewsCausesList;
		}
	}
	
	@GetMapping(value = "/{causeId}/edit")
	public String initUpdateCauseForm(@PathVariable("causeId") final int causeId, final Model model) {
		final Optional<Cause> oCause = this.causeService.findCauseById(causeId);
		if(oCause.isPresent()) {
			model.addAttribute(oCause.get());
			return CauseController.views_cause_create_or_update;
		}else {
			model.addAttribute("message", "Cause not present!");
			return this.listCause(model.asMap());
		}
	}
	
	@PostMapping(value = "/{causeId}/edit")
	public String processUpdateVetForm(@Valid final Cause cause, final BindingResult result,
			@PathVariable("causeId") final int causeId, final HttpServletRequest request, final RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "editcauseerror");

			return CauseController.views_cause_create_or_update;
		}else {
			cause.setId(causeId);
			this.causeService.saveCause(cause);
			redirectAttributes.addFlashAttribute("message", "editcausesuccess");

			return viewsCausesList;
		}
	}
	
	@GetMapping(value = "/{causeId}")
	public String causeDetails(@PathVariable("causeId") final int causeId, final Model model, final RedirectAttributes redirectAttributes) {
		Optional<Cause> oCause = causeService.findCauseById(causeId);
		if(!oCause.isPresent()) {
			redirectAttributes.addFlashAttribute("message", "causenotfound");
			return viewsCausesList;
		}else {
			Cause c = oCause.get();
			Double totalAcum = c.getTotalAcumulado();
			model.addAttribute("totalAcum", totalAcum);
			model.addAttribute("cause", c);
			return "/causes/causeDetails";
		}
	}
}
