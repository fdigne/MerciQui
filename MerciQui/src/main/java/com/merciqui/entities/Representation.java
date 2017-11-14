package com.merciqui.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Representation implements Serializable {
	
@Id @GeneratedValue
private long idRepresentation ;

private Spectacle spectacle;
 

}
