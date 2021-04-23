package org.springframework.samples.petclinic.model;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;



@Entity
@Table(name = "Petitions")
public @Data class Petition extends BaseEntity{

	@NotEmpty
	@Column(name = "description")
	private String description;
	

	@ManyToOne(optional=false, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "applicant")
	private User applicant;	
	
//	@ManyToOne(optional=false, cascade = CascadeType.ALL)
//	@OnDelete(action = OnDeleteAction.CASCADE)
//	@JoinColumn(name = "adopcion")
//	private Adopcion adopcion;	
	
	@NotNull
	@Column(name = "status")
	private PetitionStatus status;
}
