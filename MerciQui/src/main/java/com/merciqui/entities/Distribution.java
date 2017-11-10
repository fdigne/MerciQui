package com.merciqui.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Distribution implements Serializable{
	
	@Id @GeneratedValue
	private Long idDistribution;
	
	
	private HashMap<String, Comedien> distribTitulaires ;
	private HashMap<String, Collection<Comedien>> distribRemplacante ;
	
	@OneToMany(mappedBy="nomRole")
	private Collection<Role> listeRoles ;

}
