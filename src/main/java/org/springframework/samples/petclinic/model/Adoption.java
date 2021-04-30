package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "adoptions")
public class Adoption extends BaseEntity {
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String description;
	

	private Boolean open;
	
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "publish_date")
	private LocalDate publishDate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "adoption")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Petition> petitions;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Pet pet;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Owner owner;
	
	
}
