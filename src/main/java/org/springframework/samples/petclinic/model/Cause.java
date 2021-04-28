package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import lombok.Data;

@Entity
@Table(name = "causes")
public @Data class Cause extends BaseEntity {
	
	@NotEmpty
	@Column(name = "name")
	private String name;
	
	@NotEmpty
	@Column(name = "description")
	private String description;
	
	@NotEmpty
	@Positive
	@Column(name = "objetive")
	private Double objetive;
	
	@NotEmpty
	@Column(name = "organization")
	private String organization;

	@NotEmpty
	@Column(name = "state")
	private Boolean state;
	
	@ManyToOne
	@JoinColumn(name = "ownerId")
	private Owner owner;
	
//	@OneToMany
//	@JoinColumn(name = "donationId")
//	private List<Donation> donations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getObjetive() {
		return objetive;
	}

	public void setObjetive(Double objetive) {
		this.objetive = objetive;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Boolean getState() {
		return state;
	}
	
	public Owner getOwner() {
		return owner;
	}

//	public List<Donation> getDonations() {
//		return donations;
//	}
}
