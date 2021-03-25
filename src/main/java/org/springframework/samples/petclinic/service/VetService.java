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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class VetService {

	private final VetRepository vetRepository;


	@Autowired
	public VetService(final VetRepository vetRepository) {
		this.vetRepository = vetRepository;
	}		

	@Transactional(readOnly = true)	
	public Collection<Vet> findVets() throws DataAccessException {
		return this.vetRepository.findAll();
	}	
	
	@Transactional(readOnly = true)	
	public Optional<Vet> findVetById(int id) throws DataAccessException {
		return vetRepository.findById(id);
	}
	
	@Transactional	
	public void saveVet(Vet vet) throws DataAccessException {
		vetRepository.save(vet);
	}
  
  @Transactional
	public Vet deleteVetById(final int id) throws DataAccessException {
		final Vets vets = new Vets();
		vets.getVetList().addAll(this.findVets());
		final Optional<Vet> optionalVet = vets.getVetList().stream().filter(x->x.getId().equals(id)).findFirst();
		
		if(optionalVet.isPresent()) {
			this.vetRepository.deleteById(id);
			return optionalVet.get();
		}
		else {
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public Set<Specialty> findSpecialties() throws DataAccessException {
		return vetRepository.findSpecialties();
	}
	
	@Transactional(readOnly = true)	
	public Specialty findSpecialtyByName(String name) throws DataAccessException {
		return vetRepository.findSpecialtyByName(name);
	}
	
	// Con esta funcion añadimos todas las especialidades de la request en el vet
	public void addSpecialties(Vet vet, String[] specialtiesArray) {
		if(specialtiesArray==null) {
			Vet newVet = new Vet();
			newVet.setId(vet.getId());
			newVet.setFirstName(vet.getFirstName());
			newVet.setLastName(vet.getLastName());
			vet = newVet;
		}else {
			List<String> findSpe = findSpecialties().stream().map(x->x.getName()).collect(Collectors.toList());
			for(String s: specialtiesArray) {
				if(findSpe.contains(s)) {
					Specialty specialty = findSpecialtyByName(s);
					vet.addSpecialty(specialty);
				}
			}
		}
	}
}
