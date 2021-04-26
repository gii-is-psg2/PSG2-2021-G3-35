package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Petition;
import org.springframework.samples.petclinic.model.PetitionStatus;
import org.springframework.samples.petclinic.repository.PetitionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetitionService {

	private final PetitionRepository petitionRepository;


	@Autowired
	public PetitionService(final PetitionRepository petitionRepository) {
		this.petitionRepository = petitionRepository;
	
	}


	
	@Transactional
	public Petition deletePetition(final int petitionID) {
		final Petition petition = this.findPetitionById(petitionID);
		if (petition == null) {
			return null;
		} else {
			this.petitionRepository.deleteById(petitionID);
			return petition;
		}

	}
	
	public Petition createPetition() {
		Petition petition = new Petition();
		petition.setStatus(PetitionStatus.PENDIENTE);
		return petition;
	}
	
	@Transactional
	public Petition savePetition(Petition petition) {
		petition.setStatus(PetitionStatus.PENDIENTE);
		return this.petitionRepository.save(petition);
	}

	public Petition findPetitionById(final int petitionID) {
		return this.petitionRepository.findPetitionById(petitionID);
	}
	
	public Iterable<Petition> findAll() {
		return this.petitionRepository.findAll();
	}

	public Iterable<Petition> findMyPetitions(final int ownerId) {
		return this.petitionRepository.findPetitionsByOwner(ownerId);

	}
	
	public Collection<Petition> findPetitionsByAdoptionId(final int adoptionId) {
		return this.petitionRepository.findPetitionsByAdoptionId(adoptionId);
	}
	

}
