package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;



@Entity
@Table(name = "bookings")
public @Data class Booking extends BaseEntity{

	@NotNull
	@FutureOrPresent
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "start_date")
	private LocalDate startDate;
	
	@NotNull
	@Future
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "end_date")
	private LocalDate endDate;
	
	@NotNull
	@Max(20)
	private Integer room;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
}
