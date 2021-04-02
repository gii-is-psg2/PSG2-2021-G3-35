package org.springframework.samples.petclinic.web;

import static org.hamcrest.xml.HasXPath.hasXPath;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for the {@link VetController}
 */
@WebMvcTest(controllers=VetController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class VetControllerTests {

	@Autowired
	private VetController vetController;

	@MockBean
	private VetService clinicService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {

		Vet james = new Vet();
		james.setFirstName("James");
		james.setLastName("Carter");
		james.setId(1);
		Vet helen = new Vet();
		helen.setFirstName("Helen");
		helen.setLastName("Leary");
		helen.setId(2);
		Specialty radiology = new Specialty();
		radiology.setId(1);
		radiology.setName("radiology");
		helen.addSpecialty(radiology);
		given(this.clinicService.findVets()).willReturn(Lists.newArrayList(james, helen));
		given(this.clinicService.deleteVetById(1)).willReturn(james);
		given(this.clinicService.findVetById(1)).willReturn(Optional.of(james));
	}
        
    @WithMockUser(value = "spring")
		@Test
	void testShowVetListHtml() throws Exception {
		mockMvc.perform(get("/vets")).andExpect(status().isOk()).andExpect(model().attributeExists("vets"))
				.andExpect(view().name("vets/vetList"));
	}	

	@WithMockUser(value = "spring")
        @Test
	void testShowVetListXml() throws Exception {
		mockMvc.perform(get("/vets.xml").accept(MediaType.APPLICATION_XML)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_XML_VALUE))
				.andExpect(content().node(hasXPath("/vets/vetList[id=1]/id")));
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/vets/new")).andExpect(status().isOk())
				.andExpect(model().attributeExists("vet"))
				.andExpect(view().name("vets/createOrUpdateVetForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationForm() throws Exception {
		mockMvc.perform(post("/vets/new")
					.with(csrf())
					.param("firstName", "Ale")
					.param("lastName", "Ruiz"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/vets"));
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationFormError() throws Exception {
		mockMvc.perform(post("/vets/new")
				.with(csrf())
				.param("firstName", "")
				.param("lastName", "  "))
			.andExpect(model().attributeHasErrors("vet"))
			.andExpect(status().isOk())
			.andExpect(view().name("vets/createOrUpdateVetForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testInitUpdateVetForm() throws Exception {
		mockMvc.perform(get("/vets/1/edit")).andExpect(status().isOk())
				.andExpect(model().attributeExists("vet"))
				.andExpect(view().name("vets/createOrUpdateVetForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testProcessUpdateVetForm() throws Exception {
		mockMvc.perform(post("/vets/1/edit")
				.with(csrf())
				.param("firstName", "James")
				.param("lastName", "Rogers"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/vets"));
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testProcessUpdateVetFormError() throws Exception {
		mockMvc.perform(post("/vets/1/edit")
				.with(csrf())
				.param("firstName", "James")
				.param("lastName", ""))
			.andExpect(model().attributeHasErrors("vet"))
			.andExpect(status().isOk())
			.andExpect(view().name("vets/createOrUpdateVetForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testDeleteVets() throws Exception {
		mockMvc.perform(get("/vets/1/delete")).andExpect(status().is3xxRedirection())
				.andExpect(flash().attribute("message", "Vet James Carter deleted."))
				.andExpect(view().name("redirect:/vets"));
			
	}

}
