package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CauseService {
	private CauseRepository causeRepository;
	
	@Autowired
	public CauseService(CauseRepository causeRepository) {
		this.causeRepository = causeRepository;
	}
	
	@Transactional(readOnly = true)
	public Optional<Cause> findCauseById(Integer id) throws DataAccessException{
		return causeRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public List<Cause> findCauses() throws DataAccessException{
		List<Cause> salida = new ArrayList<>();
		causeRepository.findAll().forEach(salida::add);
		return salida;
	}
	
	@Transactional
	public void saveCause(Cause cause) throws DataAccessException{
		causeRepository.save(cause);
	}
	
	
	@Transactional
	public Boolean getState(Cause c){
		Boolean estado = true;
		if(c.getTotalAcumulado() >= c.getObjetive()) {
			estado = false;
		}
		return estado;
	}
	
	@Transactional
	public Cause deleteCause(final Integer causeId) {
		final Optional<Cause> cause = this.findCauseById(causeId);
		if(cause.isEmpty()) {
			return null;
		}else {
			this.causeRepository.deleteById(causeId);
			return cause.get();
		}
	}
	
	
	
}
