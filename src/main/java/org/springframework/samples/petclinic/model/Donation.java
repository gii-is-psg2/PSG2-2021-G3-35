package org.springframework.samples.petclinic.model;

import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "donations")
public class Donation extends BaseEntity{
	
	@NotEmpty
	@Positive
	@Column(name = "amount")
	private Double amount;
	
	@NotEmpty
	@Column(name = "date")
	private LocalDate date;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
//  Descomentar cuando Cause sea creado
	@ManyToOne(optional=false)
	@JoinColumn(name = "cause_id")
	private Cause cause;
	
}
