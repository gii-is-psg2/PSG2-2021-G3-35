package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.transaction.Transactional;

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
	
	@Transactional
	public Adoption deleteAdoption(final int adoptionId) {
		final Adoption adopt = this.getById(adoptionId);
		if (adopt == null) {
			return null;
		}else {
		this.adoptionRepository.deleteAdoptionById(adoptionId);
		return adopt;
		}
	}
	
	public void createAdoption(final Adoption adoption) {
		this.adoptionRepository.save(adoption);
	}
	
}
