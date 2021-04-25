package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.repository.AdoptionRepository;
import org.springframework.stereotype.Service;

@Service
public class AdoptionService {
	
	@Autowired
	AdoptionRepository adoptionRepository;
	
	public Collection<Adoption> getAllAdoptions(){
		return this.adoptionRepository.findAll();
	}
	
	public Collection<Adoption> getAllAdoptionsByOwnerId(final int id){
		return this.adoptionRepository.findAllByOwnerId(id);
	}
	
	public Adoption getById(final int id){
		return this.adoptionRepository.findById(id);
	}
	
}
