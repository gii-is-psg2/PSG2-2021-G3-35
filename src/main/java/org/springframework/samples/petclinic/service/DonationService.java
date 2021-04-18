package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DonationService {
	
	private DonationRepository donationRepository;	
	
	@Autowired
	public DonationService(DonationRepository donationRepository) {
		this.donationRepository = donationRepository;
	}
	
	@Transactional(readOnly = true)
	public Optional<Donation> findDonationById(int id) throws DataAccessException {
		return this.donationRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public List<Donation> findDonations() throws DataAccessException {
		List<Donation> res = new ArrayList<>();
		this.donationRepository.findAll().forEach(res::add);
		return res;
	}

	@Transactional
	public void saveDonation(Donation d) throws DataAccessException {
		this.donationRepository.save(d);		
	}
	
}
