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
package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided
 * by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning that we
 * don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * ClinicServiceTests#clinicService clinicService}</code> instance variable, which uses
 * autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in
 * its own transaction, which is automatically rolled back by default. Thus, even if tests
 * insert or otherwise change database state, there is no need for a teardown or cleanup
 * script.
 * <li>An {@link org.springframework.context.ApplicationContext ApplicationContext} is
 * also inherited and can be used for explicit bean lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class VetServiceTests {

	@Autowired
	protected VetService vetService;	

	@Test
	void shouldFindVets() {
		final Collection<Vet> vets = this.vetService.findVets();

		final Vet vet = EntityUtils.getById(vets, Vet.class, 3);
		Assertions.assertThat(vet.getLastName()).isEqualTo("Douglas");
		Assertions.assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
		Assertions.assertThat(vet.getSpecialties().get(0).getName()).isEqualTo("dentistry");
		Assertions.assertThat(vet.getSpecialties().get(1).getName()).isEqualTo("surgery");
	}
	
	@Test
	@Transactional
	void shouldFindVetById() {
		Optional<Vet> vet = this.vetService.findVetById(1);
		
		Assertions.assertThat(vet).isPresent();
		Assertions.assertThat(vet.get().getFirstName()).isEqualTo("James");
		Assertions.assertThat(vet.get().getLastName()).isEqualTo("Carter");
	}
	
	@Test
	@Transactional
	void shouldSaveVet() {
		Vet vet = this.vetService.findVetById(1).get();
		vet.setLastName("Rogers");
		
		this.vetService.saveVet(vet);
		
		Vet saveVet = this.vetService.findVetById(1).get();
		Assertions.assertThat(saveVet.getFirstName()).isEqualTo("James");
		Assertions.assertThat(saveVet.getLastName()).isEqualTo("Rogers");
	}
	
	@Test
	@Transactional
	void shouldDeleteVet() {
		final Vet result = this.vetService.deleteVetById(1);
		Assertions.assertThat(result).isNotNull();
	}
	
	@Test
	@Transactional
	void shouldNotDeleteVet() {
		final Vet result = this.vetService.deleteVetById(122);
		Assertions.assertThat(result).isNull();
	}
	
	@Test
	@Transactional
	void shouldFindSpecialties() {
		Set<Specialty> specialties = this.vetService.findSpecialties();
		
		Assertions.assertThat(specialties).isNotEmpty();
		Assertions.assertThat(specialties.size()).isEqualTo(3);
	}
	
	@Test
	@Transactional
	void shouldFindSpecialtyByName() {
		Specialty specialty = this.vetService.findSpecialtyByName("radiology");
		
		Assertions.assertThat(specialty.getName()).isEqualTo("radiology");
	}

	@Test
	@Transactional
	void shouldAddSpecialties() {
		Set<Specialty> specialties = this.vetService.findSpecialties();
		
		Assertions.assertThat(specialties).isNotEmpty();
		Assertions.assertThat(specialties.size()).isEqualTo(3);
	}

}
