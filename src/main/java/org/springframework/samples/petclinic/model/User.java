package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "users")
public class User{
	@Id
	String username;
	
	String password;
	
	boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Authorities> authorities;
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(final String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(final String password) {
		this.password = password;
	}
	
	public Set<Authorities> getAuthorities() {
		return this.authorities;
	}
	

	public boolean getEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(final boolean value) {
		this.enabled=value;
	}
}
