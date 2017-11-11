package com.merciqui.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;

@Entity
public class Distribution implements Serializable{
	
	@Id @GeneratedValue
	private Long idDistribution;
	
	@ManyToOne
	@JoinColumn(name="CODE_SPECTACLE")
	private Spectacle spectacle ;
	
	@ElementCollection
	private Map<Role, Comedien> distribution;

	public Distribution() {
		super();
	}

	public Distribution(Spectacle spectacle, Map<Role,Comedien> distribution) {
		super();
		this.spectacle = spectacle;
		this.distribution = distribution;
	}

	public Long getIdDistribution() {
		return idDistribution;
	}

	public void setIdDistribution(Long idDistribution) {
		this.idDistribution = idDistribution;
	}

	public Spectacle getSpectacle() {
		return spectacle;
	}

	public void setSpectacle(Spectacle spectacle) {
		this.spectacle = spectacle;
	}

	public Map<Role,Comedien> getDistribution() {
		return distribution;
	}

	public void setDistribution(Map<Role,Comedien> distribution) {
		this.distribution = distribution;
	}
	
	
	
	

}
