package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

@Entity
@Table(name = "donations")
public @Data class Donation extends BaseEntity{
	
	@NotNull(message="holi")
	@Positive()
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "date")
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	@ManyToOne
	@JoinColumn(name = "cause_id")
	private Cause cause;
	
}
