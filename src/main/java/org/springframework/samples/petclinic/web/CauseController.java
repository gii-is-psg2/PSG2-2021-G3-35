package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/causes")
public class CauseController {
	private CauseService causeService;
	
	@Autowired
	public CauseController(CauseService causeService) {
		this.causeService = causeService;
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
		model.put("causes", listCauses);
		return "/causes/causesList";			
	}
	
	
}
