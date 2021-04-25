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

import lombok.Getter;
import lombok.Setter;



@Entity
@Getter
@Setter
@Table(name = "Petitions")
public class Petition extends BaseEntity{

	@NotEmpty
	@Column(name = "description")
	private String description;
	

	@ManyToOne(optional=false, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "applicant")
	private Owner applicant;	
	
	@ManyToOne(optional=false, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "adoption")
	private Adoption adoption;	
	
	@NotNull
	@Column(name = "status")
	private PetitionStatus status;
}
