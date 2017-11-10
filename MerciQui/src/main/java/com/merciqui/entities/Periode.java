package com.merciqui.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Periode implements Serializable{
	
	@Id @GeneratedValue
	private Long idPeriode;
	
	private String nomPeriode ;
	private Calendar dateDebut ;
	private Calendar dateFin ;
	

}
