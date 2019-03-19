package com.merciqui.entities;

import static org.assertj.core.api.Assertions.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.merciqui.dao.EvenementRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EvenementRepositoryIntegrationTest {
	
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private  EvenementRepository evenementRepository;
    
    @Test
    public void whenFindAll_thenReturnListEvenement() {
        
    	// given
    	Comedien comedien1 = new Comedien(1L, "Digne", "Sarah", null, null, null, "fdigne@me.com", null, null);
        
        Comedien comedien2 = new Comedien(2L, "Digne", "Florian", null, null, null, "fdigne2@me.com", null, null);
        
        Comedien comedien3 = new Comedien(3L, "Vilardell", "Julie", null, null, null, "fdigne3@me.com", null, null);
        
        Comedien comedien4 = new Comedien(4L, "Andrieu", "Patrick", null, null, null, "fdigne4@me.com", null, null);
        
       Spectacle spectacle1 = new Spectacle("Les Clotildes");
       Spectacle spectacle2 = new Spectacle("En toutes amitiés");
       Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
     
       Collection<Comedien> listeRemplacants1 = new ArrayList<Comedien>() ;
       listeRemplacants1.add(comedien3);
       listeRemplacants1.add(comedien4);
       
       Set<Comedien> listeComediens1 = new HashSet<Comedien>() ;
       listeComediens1.add(comedien1);
       listeComediens1.add(comedien2);
       listeComediens1.add(comedien3);       
       Set<Comedien> listeComediens2 = new HashSet<Comedien>() ;
       listeComediens2.add(comedien4);
       listeComediens2.add(comedien1);
       Set<Comedien> listeComediens3 = new HashSet<Comedien>() ;
       listeComediens3.add(comedien3);
       listeComediens3.add(comedien1);
       
       Role role1 = new Role("Clotilde", spectacle1);
       role1.setComedienTitulaire(comedien1);
       role1.setListeRemplas(listeRemplacants1);
       Role role2 = new Role("Stagiaire", spectacle1);
       role2.setComedienTitulaire(comedien2);
       Role role3 = new Role("Clotilde Admin", spectacle1);
       role3.setComedienTitulaire(comedien3);
       Role role4 = new Role("Griberg", spectacle2);
       role4.setComedienTitulaire(comedien4);
       Role role5 = new Role("majordome", spectacle2);
       role5.setComedienTitulaire(comedien1);
       Role role6 = new Role("Femme", spectacle3);
       role6.setComedienTitulaire(comedien1);
       Role role7 = new Role("Homme", spectacle3);
       role7.setComedienTitulaire(comedien3);
       
       entityManager.persist(comedien1);
       entityManager.persist(comedien2);
       entityManager.persist(comedien3);
       entityManager.persist(comedien4);
       entityManager.persist(spectacle1);
       entityManager.persist(spectacle2);
       entityManager.persist(spectacle3);
       
       entityManager.persist(role1);
       entityManager.persist(role2);
       entityManager.persist(role3);
       entityManager.persist(role4);
       entityManager.persist(role5);
       entityManager.persist(role6);
       entityManager.persist(role7);
       
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.HOUR_OF_DAY,  0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0); 
		
		Calendar calfin = Calendar.getInstance();
		calfin.set(Calendar.YEAR, 2019);
		calfin.set(Calendar.MONTH, Calendar.JANUARY);
		calfin.set(Calendar.DAY_OF_MONTH,15);
		calfin.set(Calendar.HOUR_OF_DAY,  0);
		calfin.set(Calendar.MINUTE, 0);
		calfin.set(Calendar.SECOND, 0);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, 2019);
		cal2.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal2.set(Calendar.DAY_OF_MONTH,1);
		cal2.set(Calendar.HOUR_OF_DAY,  0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0); 
		
		Calendar calfin2 = Calendar.getInstance();
		calfin2.set(Calendar.YEAR, 2019);
		calfin2.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin2.set(Calendar.DAY_OF_MONTH,12);
		calfin2.set(Calendar.HOUR_OF_DAY,  0);
		calfin2.set(Calendar.MINUTE, 0);
		calfin2.set(Calendar.SECOND, 0);
		
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.YEAR, 2019);
		cal3.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal3.set(Calendar.DAY_OF_MONTH,1);
		cal3.set(Calendar.HOUR_OF_DAY,  0);
		cal3.set(Calendar.MINUTE, 0);
		cal3.set(Calendar.SECOND, 0); 
		
		Calendar calfin3 = Calendar.getInstance();
		calfin3.set(Calendar.YEAR, 2019);
		calfin3.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin3.set(Calendar.DAY_OF_MONTH,12);
		calfin3.set(Calendar.HOUR_OF_DAY,  0);
		calfin3.set(Calendar.MINUTE, 0);
		calfin3.set(Calendar.SECOND, 0);
    	
    	Periode p1 = new Periode(cal.getTime(), calfin.getTime());
    	Periode p2 = new Periode(cal2.getTime(), calfin2.getTime());
    	Periode p3 = new Periode(cal3.getTime(), calfin3.getTime()); 
    	entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        
        Evenement ev1 = new Evenement("123456789", p1.getDateDebut(), spectacle1, "3T",listeComediens1);
        Evenement ev2 = new Evenement("09878765", p2.getDateDebut(), spectacle2, "3T D'A COTE",listeComediens2);
        Evenement ev3 = new Evenement("7777777777", p3.getDateDebut(), spectacle3, "3T D'A COTE",listeComediens3);
        entityManager.persist(ev1);
        entityManager.persist(ev2);
        entityManager.persist(ev3);
        entityManager.flush();
     
        Collection<Evenement> listeEvenements = new ArrayList<Evenement>();
        listeEvenements.add(ev1);
        listeEvenements.add(ev2);
        listeEvenements.add(ev3);
        
        // when
        List<Evenement> found = evenementRepository.findAll();
     
        // then
        assertThat(found)
          .isEqualTo(listeEvenements);
    }
    
    @Test 
    public void whenDelete_thenReturnNoEvenement() {
    	// given
    	Comedien comedien1 = new Comedien(1L, "Digne", "Sarah", null, null, null, "fdigne@me.com", null, null);
        
        Comedien comedien2 = new Comedien(2L, "Digne", "Florian", null, null, null, "fdigne2@me.com", null, null);
        
        Comedien comedien3 = new Comedien(3L, "Vilardell", "Julie", null, null, null, "fdigne3@me.com", null, null);
        
        Comedien comedien4 = new Comedien(4L, "Andrieu", "Patrick", null, null, null, "fdigne4@me.com", null, null);
        
       Spectacle spectacle1 = new Spectacle("Les Clotildes");
       Spectacle spectacle2 = new Spectacle("En toutes amitiés");
       Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
     
       Collection<Comedien> listeRemplacants1 = new ArrayList<Comedien>() ;
       listeRemplacants1.add(comedien3);
       listeRemplacants1.add(comedien4);
       
       Set<Comedien> listeComediens1 = new HashSet<Comedien>() ;
       listeComediens1.add(comedien1);
       listeComediens1.add(comedien2);
       listeComediens1.add(comedien3);       
       Set<Comedien> listeComediens2 = new HashSet<Comedien>() ;
       listeComediens2.add(comedien4);
       listeComediens2.add(comedien1);
       Set<Comedien> listeComediens3 = new HashSet<Comedien>() ;
       listeComediens3.add(comedien3);
       listeComediens3.add(comedien1);
       
       Role role1 = new Role("Clotilde", spectacle1);
       role1.setComedienTitulaire(comedien1);
       role1.setListeRemplas(listeRemplacants1);
       Role role2 = new Role("Stagiaire", spectacle1);
       role2.setComedienTitulaire(comedien2);
       Role role3 = new Role("Clotilde Admin", spectacle1);
       role3.setComedienTitulaire(comedien3);
       Role role4 = new Role("Griberg", spectacle2);
       role4.setComedienTitulaire(comedien4);
       Role role5 = new Role("majordome", spectacle2);
       role5.setComedienTitulaire(comedien1);
       Role role6 = new Role("Femme", spectacle3);
       role6.setComedienTitulaire(comedien1);
       Role role7 = new Role("Homme", spectacle3);
       role7.setComedienTitulaire(comedien3);
       
       entityManager.persist(comedien1);
       entityManager.persist(comedien2);
       entityManager.persist(comedien3);
       entityManager.persist(comedien4);
       entityManager.persist(spectacle1);
       entityManager.persist(spectacle2);
       entityManager.persist(spectacle3);
       
       entityManager.persist(role1);
       entityManager.persist(role2);
       entityManager.persist(role3);
       entityManager.persist(role4);
       entityManager.persist(role5);
       entityManager.persist(role6);
       entityManager.persist(role7);
       
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.HOUR_OF_DAY,  0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0); 
		
		Calendar calfin = Calendar.getInstance();
		calfin.set(Calendar.YEAR, 2019);
		calfin.set(Calendar.MONTH, Calendar.JANUARY);
		calfin.set(Calendar.DAY_OF_MONTH,15);
		calfin.set(Calendar.HOUR_OF_DAY,  0);
		calfin.set(Calendar.MINUTE, 0);
		calfin.set(Calendar.SECOND, 0);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, 2019);
		cal2.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal2.set(Calendar.DAY_OF_MONTH,1);
		cal2.set(Calendar.HOUR_OF_DAY,  0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0); 
		
		Calendar calfin2 = Calendar.getInstance();
		calfin2.set(Calendar.YEAR, 2019);
		calfin2.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin2.set(Calendar.DAY_OF_MONTH,12);
		calfin2.set(Calendar.HOUR_OF_DAY,  0);
		calfin2.set(Calendar.MINUTE, 0);
		calfin2.set(Calendar.SECOND, 0);
		
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.YEAR, 2019);
		cal3.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal3.set(Calendar.DAY_OF_MONTH,1);
		cal3.set(Calendar.HOUR_OF_DAY,  0);
		cal3.set(Calendar.MINUTE, 0);
		cal3.set(Calendar.SECOND, 0); 
		
		Calendar calfin3 = Calendar.getInstance();
		calfin3.set(Calendar.YEAR, 2019);
		calfin3.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin3.set(Calendar.DAY_OF_MONTH,12);
		calfin3.set(Calendar.HOUR_OF_DAY,  0);
		calfin3.set(Calendar.MINUTE, 0);
		calfin3.set(Calendar.SECOND, 0);
    	
    	Periode p1 = new Periode(cal.getTime(), calfin.getTime());
    	Periode p2 = new Periode(cal2.getTime(), calfin2.getTime());
    	Periode p3 = new Periode(cal3.getTime(), calfin3.getTime()); 
    	entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        
        Evenement ev1 = new Evenement("123456789", p1.getDateDebut(), spectacle1, "3T",listeComediens1);
        Evenement ev2 = new Evenement("09878765", p2.getDateDebut(), spectacle2, "3T D'A COTE",listeComediens2);
        Evenement ev3 = new Evenement("7777777777", p3.getDateDebut(), spectacle3, "3T D'A COTE",listeComediens3);
        entityManager.persist(ev1);
        entityManager.persist(ev2);
        entityManager.persist(ev3);
        entityManager.flush();
     
        Collection<Evenement> listeEvenements = new ArrayList<Evenement>();
        listeEvenements.add(ev1);
        listeEvenements.add(ev2);
        
        // when
        evenementRepository.delete(ev3.getIdEvenement());
        Evenement found = evenementRepository.findOne(ev3.getIdEvenement());
        List<Evenement> found2 = evenementRepository.findAll();
        
        
        // then
        assertThat(found).isNull();
        assertThat(found2).isEqualTo(listeEvenements);
        assertThat(found2).doesNotContain(ev3);
    }
 
    @Test
    public void whenFindById_thenReturnEvenement() {
    	// given
    	Comedien comedien1 = new Comedien("123456789", "Digne", "Sarah", null, null, null, "fdigne@me.com", null);
        
        Comedien comedien2 = new Comedien("987654321", "Digne", "Florian", null, null, null, "fdigne2@me.com", null);
        
        Comedien comedien3 = new Comedien("0000000", "Vilardell", "Julie", null, null, null, "fdigne3@me.com", null);
        
        Comedien comedien4 = new Comedien("111111", "Andrieu", "Patrick", null, null, null, "fdigne4@me.com", null);
        
       Spectacle spectacle1 = new Spectacle("Les Clotildes");
       Spectacle spectacle2 = new Spectacle("En toutes amitiés");
       Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
     
       Collection<Comedien> listeRemplacants1 = new ArrayList<Comedien>() ;
       listeRemplacants1.add(comedien3);
       listeRemplacants1.add(comedien4);
       
       Set<Comedien> listeComediens1 = new HashSet<Comedien>() ;
       listeComediens1.add(comedien1);
       listeComediens1.add(comedien2);
       listeComediens1.add(comedien3);       
       Set<Comedien> listeComediens2 = new HashSet<Comedien>() ;
       listeComediens2.add(comedien4);
       listeComediens2.add(comedien1);
       Set<Comedien> listeComediens3 = new HashSet<Comedien>() ;
       listeComediens3.add(comedien3);
       listeComediens3.add(comedien1);
       
       Role role1 = new Role("Clotilde", spectacle1);
       role1.setComedienTitulaire(comedien1);
       role1.setListeRemplas(listeRemplacants1);
       Role role2 = new Role("Stagiaire", spectacle1);
       role2.setComedienTitulaire(comedien2);
       Role role3 = new Role("Clotilde Admin", spectacle1);
       role3.setComedienTitulaire(comedien3);
       Role role4 = new Role("Griberg", spectacle2);
       role4.setComedienTitulaire(comedien4);
       Role role5 = new Role("majordome", spectacle2);
       role5.setComedienTitulaire(comedien1);
       Role role6 = new Role("Femme", spectacle3);
       role6.setComedienTitulaire(comedien1);
       Role role7 = new Role("Homme", spectacle3);
       role7.setComedienTitulaire(comedien3);
       
       entityManager.persist(comedien1);
       entityManager.persist(comedien2);
       entityManager.persist(comedien3);
       entityManager.persist(comedien4);
       entityManager.persist(spectacle1);
       entityManager.persist(spectacle2);
       entityManager.persist(spectacle3);
       
       entityManager.persist(role1);
       entityManager.persist(role2);
       entityManager.persist(role3);
       entityManager.persist(role4);
       entityManager.persist(role5);
       entityManager.persist(role6);
       entityManager.persist(role7);
       
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.HOUR_OF_DAY,  0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0); 
		
		Calendar calfin = Calendar.getInstance();
		calfin.set(Calendar.YEAR, 2019);
		calfin.set(Calendar.MONTH, Calendar.JANUARY);
		calfin.set(Calendar.DAY_OF_MONTH,15);
		calfin.set(Calendar.HOUR_OF_DAY,  0);
		calfin.set(Calendar.MINUTE, 0);
		calfin.set(Calendar.SECOND, 0);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, 2019);
		cal2.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal2.set(Calendar.DAY_OF_MONTH,1);
		cal2.set(Calendar.HOUR_OF_DAY,  0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0); 
		
		Calendar calfin2 = Calendar.getInstance();
		calfin2.set(Calendar.YEAR, 2019);
		calfin2.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin2.set(Calendar.DAY_OF_MONTH,12);
		calfin2.set(Calendar.HOUR_OF_DAY,  0);
		calfin2.set(Calendar.MINUTE, 0);
		calfin2.set(Calendar.SECOND, 0);
		
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.YEAR, 2019);
		cal3.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal3.set(Calendar.DAY_OF_MONTH,1);
		cal3.set(Calendar.HOUR_OF_DAY,  0);
		cal3.set(Calendar.MINUTE, 0);
		cal3.set(Calendar.SECOND, 0); 
		
		Calendar calfin3 = Calendar.getInstance();
		calfin3.set(Calendar.YEAR, 2019);
		calfin3.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin3.set(Calendar.DAY_OF_MONTH,12);
		calfin3.set(Calendar.HOUR_OF_DAY,  0);
		calfin3.set(Calendar.MINUTE, 0);
		calfin3.set(Calendar.SECOND, 0);
    	
    	Periode p1 = new Periode(cal.getTime(), calfin.getTime());
    	Periode p2 = new Periode(cal2.getTime(), calfin2.getTime());
    	Periode p3 = new Periode(cal3.getTime(), calfin3.getTime()); 
    	entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        
        Evenement ev1 = new Evenement("123456789", p1.getDateDebut(), spectacle1, "3T",listeComediens1);
        Evenement ev2 = new Evenement("09878765", p2.getDateDebut(), spectacle2, "3T D'A COTE",listeComediens2);
        Evenement ev3 = new Evenement("7777777777", p3.getDateDebut(), spectacle3, "3T D'A COTE",listeComediens3);
        entityManager.persist(ev1);
        entityManager.persist(ev2);
        entityManager.persist(ev3);
        entityManager.flush();
     
        // when
        Evenement found = evenementRepository.findOne(ev1.getIdEvenement());
     
        // then
        assertThat(found)
          .isEqualTo(ev1);
    }
    
    @Test
    public void whengetNbreDatesByComedien_thenReturnNbEvenement() {
    	// given
    	Comedien comedien1 = new Comedien("123456789", "Digne", "Sarah", null, null, null, "fdigne@me.com", null);    
        Comedien comedien2 = new Comedien("987654321", "Digne", "Florian", null, null, null, "fdigne2@me.com", null);
        Comedien comedien3 = new Comedien("0000000", "Vilardell", "Julie", null, null, null, "fdigne3@me.com", null);
        Comedien comedien4 = new Comedien("111111", "Andrieu", "Patrick", null, null, null, "fdigne4@me.com", null);
        
       Spectacle spectacle1 = new Spectacle("Les Clotildes");
       Spectacle spectacle2 = new Spectacle("En toutes amitiés");
       Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
     
       Collection<Comedien> listeRemplacants1 = new ArrayList<Comedien>() ;
       listeRemplacants1.add(comedien3);
       listeRemplacants1.add(comedien4);
       
       Set<Comedien> listeComediens1 = new HashSet<Comedien>() ;
       listeComediens1.add(comedien1);
       listeComediens1.add(comedien2);
       listeComediens1.add(comedien3);       
       Set<Comedien> listeComediens2 = new HashSet<Comedien>() ;
       listeComediens2.add(comedien4);
       listeComediens2.add(comedien1);
       Set<Comedien> listeComediens3 = new HashSet<Comedien>() ;
       listeComediens3.add(comedien3);
       listeComediens3.add(comedien1);
       
       Role role1 = new Role("Clotilde", spectacle1);
       role1.setComedienTitulaire(comedien1);
       role1.setListeRemplas(listeRemplacants1);
       Role role2 = new Role("Stagiaire", spectacle1);
       role2.setComedienTitulaire(comedien2);
       Role role3 = new Role("Clotilde Admin", spectacle1);
       role3.setComedienTitulaire(comedien3);
       Role role4 = new Role("Griberg", spectacle2);
       role4.setComedienTitulaire(comedien4);
       Role role5 = new Role("majordome", spectacle2);
       role5.setComedienTitulaire(comedien1);
       Role role6 = new Role("Femme", spectacle3);
       role6.setComedienTitulaire(comedien1);
       Role role7 = new Role("Homme", spectacle3);
       role7.setComedienTitulaire(comedien3);
       
       entityManager.persist(comedien1);
       entityManager.persist(comedien2);
       entityManager.persist(comedien3);
       entityManager.persist(comedien4);
       entityManager.persist(spectacle1);
       entityManager.persist(spectacle2);
       entityManager.persist(spectacle3);
       
       entityManager.persist(role1);
       entityManager.persist(role2);
       entityManager.persist(role3);
       entityManager.persist(role4);
       entityManager.persist(role5);
       entityManager.persist(role6);
       entityManager.persist(role7);
       
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.HOUR_OF_DAY,  0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0); 
		
		Calendar calfin = Calendar.getInstance();
		calfin.set(Calendar.YEAR, 2019);
		calfin.set(Calendar.MONTH, Calendar.JANUARY);
		calfin.set(Calendar.DAY_OF_MONTH,15);
		calfin.set(Calendar.HOUR_OF_DAY,  0);
		calfin.set(Calendar.MINUTE, 0);
		calfin.set(Calendar.SECOND, 0);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, 2019);
		cal2.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal2.set(Calendar.DAY_OF_MONTH,1);
		cal2.set(Calendar.HOUR_OF_DAY,  0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0); 
		
		Calendar calfin2 = Calendar.getInstance();
		calfin2.set(Calendar.YEAR, 2019);
		calfin2.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin2.set(Calendar.DAY_OF_MONTH,12);
		calfin2.set(Calendar.HOUR_OF_DAY,  0);
		calfin2.set(Calendar.MINUTE, 0);
		calfin2.set(Calendar.SECOND, 0);
		
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.YEAR, 2019);
		cal3.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal3.set(Calendar.DAY_OF_MONTH,1);
		cal3.set(Calendar.HOUR_OF_DAY,  0);
		cal3.set(Calendar.MINUTE, 0);
		cal3.set(Calendar.SECOND, 0); 
		
		Calendar calfin3 = Calendar.getInstance();
		calfin3.set(Calendar.YEAR, 2019);
		calfin3.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin3.set(Calendar.DAY_OF_MONTH,12);
		calfin3.set(Calendar.HOUR_OF_DAY,  0);
		calfin3.set(Calendar.MINUTE, 0);
		calfin3.set(Calendar.SECOND, 0);
    	
    	Periode p1 = new Periode(cal.getTime(), calfin.getTime());
    	Periode p2 = new Periode(cal2.getTime(), calfin2.getTime());
    	Periode p3 = new Periode(cal3.getTime(), calfin3.getTime()); 
    	entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        
        Evenement ev1 = new Evenement("123456789", p1.getDateDebut(), spectacle1, "3T",listeComediens1);
        Evenement ev2 = new Evenement("09878765", p2.getDateDebut(), spectacle2, "3T D'A COTE",listeComediens2);
        Evenement ev3 = new Evenement("7777777777", p3.getDateDebut(), spectacle3, "3T D'A COTE",listeComediens3);
        entityManager.persist(ev1);
        entityManager.persist(ev2);
        entityManager.persist(ev3);
        entityManager.flush();
        
        // when
        int found1 = evenementRepository.getNbreDatesByComedien(comedien1.getId3T());
        int found2 = evenementRepository.getNbreDatesByComedien(comedien2.getId3T());
        int found3 = evenementRepository.getNbreDatesByComedien(comedien3.getId3T());
        int found4 = evenementRepository.getNbreDatesByComedien(comedien4.getId3T());
     
        // then
        assertThat(found1)
          .isEqualTo(3);    
        assertThat(found2)
        .isEqualTo(1);
        assertThat(found3)
        .isEqualTo(2);
        assertThat(found4)
        .isEqualTo(1);
        
    }
    
    @Test
    public void whengetListEvenementsParSpectacle_thenReturnListeEvenement() {
    	// given
    	Comedien comedien1 = new Comedien("123456789", "Digne", "Sarah", null, null, null, "fdigne@me.com", null);    
        Comedien comedien2 = new Comedien("987654321", "Digne", "Florian", null, null, null, "fdigne2@me.com", null);
        Comedien comedien3 = new Comedien("0000000", "Vilardell", "Julie", null, null, null, "fdigne3@me.com", null);
        Comedien comedien4 = new Comedien("111111", "Andrieu", "Patrick", null, null, null, "fdigne4@me.com", null);
        
       Spectacle spectacle1 = new Spectacle("Les Clotildes");
       Spectacle spectacle2 = new Spectacle("En toutes amitiés");
       Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
     
       Collection<Comedien> listeRemplacants1 = new ArrayList<Comedien>() ;
       listeRemplacants1.add(comedien3);
       listeRemplacants1.add(comedien4);
       
       Set<Comedien> listeComediens1 = new HashSet<Comedien>() ;
       listeComediens1.add(comedien1);
       listeComediens1.add(comedien2);
       listeComediens1.add(comedien3);       
       Set<Comedien> listeComediens2 = new HashSet<Comedien>() ;
       listeComediens2.add(comedien4);
       listeComediens2.add(comedien1);
       Set<Comedien> listeComediens3 = new HashSet<Comedien>() ;
       listeComediens3.add(comedien3);
       listeComediens3.add(comedien1);
       
       Role role1 = new Role("Clotilde", spectacle1);
       role1.setComedienTitulaire(comedien1);
       role1.setListeRemplas(listeRemplacants1);
       Role role2 = new Role("Stagiaire", spectacle1);
       role2.setComedienTitulaire(comedien2);
       Role role3 = new Role("Clotilde Admin", spectacle1);
       role3.setComedienTitulaire(comedien3);
       Role role4 = new Role("Griberg", spectacle2);
       role4.setComedienTitulaire(comedien4);
       Role role5 = new Role("majordome", spectacle2);
       role5.setComedienTitulaire(comedien1);
       Role role6 = new Role("Femme", spectacle3);
       role6.setComedienTitulaire(comedien1);
       Role role7 = new Role("Homme", spectacle3);
       role7.setComedienTitulaire(comedien3);
       
       entityManager.persist(comedien1);
       entityManager.persist(comedien2);
       entityManager.persist(comedien3);
       entityManager.persist(comedien4);
       entityManager.persist(spectacle1);
       entityManager.persist(spectacle2);
       entityManager.persist(spectacle3);
       
       entityManager.persist(role1);
       entityManager.persist(role2);
       entityManager.persist(role3);
       entityManager.persist(role4);
       entityManager.persist(role5);
       entityManager.persist(role6);
       entityManager.persist(role7);
       
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.HOUR_OF_DAY,  0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0); 
		
		Calendar calfin = Calendar.getInstance();
		calfin.set(Calendar.YEAR, 2019);
		calfin.set(Calendar.MONTH, Calendar.JANUARY);
		calfin.set(Calendar.DAY_OF_MONTH,15);
		calfin.set(Calendar.HOUR_OF_DAY,  0);
		calfin.set(Calendar.MINUTE, 0);
		calfin.set(Calendar.SECOND, 0);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, 2019);
		cal2.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal2.set(Calendar.DAY_OF_MONTH,1);
		cal2.set(Calendar.HOUR_OF_DAY,  0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0); 
		
		Calendar calfin2 = Calendar.getInstance();
		calfin2.set(Calendar.YEAR, 2019);
		calfin2.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin2.set(Calendar.DAY_OF_MONTH,12);
		calfin2.set(Calendar.HOUR_OF_DAY,  0);
		calfin2.set(Calendar.MINUTE, 0);
		calfin2.set(Calendar.SECOND, 0);
		
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.YEAR, 2019);
		cal3.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal3.set(Calendar.DAY_OF_MONTH,1);
		cal3.set(Calendar.HOUR_OF_DAY,  0);
		cal3.set(Calendar.MINUTE, 0);
		cal3.set(Calendar.SECOND, 0); 
		
		Calendar calfin3 = Calendar.getInstance();
		calfin3.set(Calendar.YEAR, 2019);
		calfin3.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin3.set(Calendar.DAY_OF_MONTH,12);
		calfin3.set(Calendar.HOUR_OF_DAY,  0);
		calfin3.set(Calendar.MINUTE, 0);
		calfin3.set(Calendar.SECOND, 0);
    	
    	Periode p1 = new Periode(cal.getTime(), calfin.getTime());
    	Periode p2 = new Periode(cal2.getTime(), calfin2.getTime());
    	Periode p3 = new Periode(cal3.getTime(), calfin3.getTime()); 
    	entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        
        Evenement ev1 = new Evenement("123456789", p1.getDateDebut(), spectacle1, "3T",listeComediens1);
        Evenement ev2 = new Evenement("09878765", p2.getDateDebut(), spectacle2, "3T D'A COTE",listeComediens2);
        Evenement ev3 = new Evenement("7777777777", p3.getDateDebut(), spectacle3, "3T D'A COTE",listeComediens3);
        Evenement ev4 = new Evenement("444444444", p3.getDateDebut(), spectacle1, "3T D'A COTE",listeComediens1);
        entityManager.persist(ev1);
        entityManager.persist(ev2);
        entityManager.persist(ev3);
        entityManager.persist(ev4);
        entityManager.flush();
        
        ArrayList<Evenement> listeEvent1 = new ArrayList<Evenement>();
        listeEvent1.add(ev1);
        listeEvent1.add(ev4);
        
        Collection<Evenement> listeEvent2 = new ArrayList<Evenement>();
        listeEvent2.add(ev2);
        Collection<Evenement> listeEvent3 = new ArrayList<Evenement>();
        listeEvent3.add(ev3);
        
        
        // when
        Collection<Evenement> found1 = evenementRepository.getListEvenementsParSpectacle(spectacle1.getIdSpectacle());
        Collection<Evenement> found2 = evenementRepository.getListEvenementsParSpectacle(spectacle2.getIdSpectacle());
        Collection<Evenement> found3 = evenementRepository.getListEvenementsParSpectacle(spectacle3.getIdSpectacle());

        // then
        assertThat(found1)
          .isEqualTo(listeEvent1);    
        assertThat(found2)
        .isEqualTo(listeEvent2);
        assertThat(found3)
        .isEqualTo(listeEvent3);
    }
    
    @Test
    public void whengetListEvenementsParComedien_thenReturnListeEvent() {
    	// given
    	Comedien comedien1 = new Comedien("123456789", "Digne", "Sarah", null, null, null, "fdigne@me.com", null);    
        Comedien comedien2 = new Comedien("987654321", "Digne", "Florian", null, null, null, "fdigne2@me.com", null);
        Comedien comedien3 = new Comedien("0000000", "Vilardell", "Julie", null, null, null, "fdigne3@me.com", null);
        Comedien comedien4 = new Comedien("111111", "Andrieu", "Patrick", null, null, null, "fdigne4@me.com", null);
        
       Spectacle spectacle1 = new Spectacle("Les Clotildes");
       Spectacle spectacle2 = new Spectacle("En toutes amitiés");
       Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
     
       Collection<Comedien> listeRemplacants1 = new ArrayList<Comedien>() ;
       listeRemplacants1.add(comedien3);
       listeRemplacants1.add(comedien4);
       
       Set<Comedien> listeComediens1 = new HashSet<Comedien>() ;
       listeComediens1.add(comedien1);
       listeComediens1.add(comedien2);
       listeComediens1.add(comedien3);       
       Set<Comedien> listeComediens2 = new HashSet<Comedien>() ;
       listeComediens2.add(comedien4);
       listeComediens2.add(comedien1);
       Set<Comedien> listeComediens3 = new HashSet<Comedien>() ;
       listeComediens3.add(comedien3);
       listeComediens3.add(comedien1);
       
       Role role1 = new Role("Clotilde", spectacle1);
       role1.setComedienTitulaire(comedien1);
       role1.setListeRemplas(listeRemplacants1);
       Role role2 = new Role("Stagiaire", spectacle1);
       role2.setComedienTitulaire(comedien2);
       Role role3 = new Role("Clotilde Admin", spectacle1);
       role3.setComedienTitulaire(comedien3);
       Role role4 = new Role("Griberg", spectacle2);
       role4.setComedienTitulaire(comedien4);
       Role role5 = new Role("majordome", spectacle2);
       role5.setComedienTitulaire(comedien1);
       Role role6 = new Role("Femme", spectacle3);
       role6.setComedienTitulaire(comedien1);
       Role role7 = new Role("Homme", spectacle3);
       role7.setComedienTitulaire(comedien3);
       
       entityManager.persist(comedien1);
       entityManager.persist(comedien2);
       entityManager.persist(comedien3);
       entityManager.persist(comedien4);
       entityManager.persist(spectacle1);
       entityManager.persist(spectacle2);
       entityManager.persist(spectacle3);
       
       entityManager.persist(role1);
       entityManager.persist(role2);
       entityManager.persist(role3);
       entityManager.persist(role4);
       entityManager.persist(role5);
       entityManager.persist(role6);
       entityManager.persist(role7);
       
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.HOUR_OF_DAY,  0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0); 
		
		Calendar calfin = Calendar.getInstance();
		calfin.set(Calendar.YEAR, 2019);
		calfin.set(Calendar.MONTH, Calendar.JANUARY);
		calfin.set(Calendar.DAY_OF_MONTH,15);
		calfin.set(Calendar.HOUR_OF_DAY,  0);
		calfin.set(Calendar.MINUTE, 0);
		calfin.set(Calendar.SECOND, 0);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, 2019);
		cal2.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal2.set(Calendar.DAY_OF_MONTH,1);
		cal2.set(Calendar.HOUR_OF_DAY,  0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0); 
		
		Calendar calfin2 = Calendar.getInstance();
		calfin2.set(Calendar.YEAR, 2019);
		calfin2.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin2.set(Calendar.DAY_OF_MONTH,12);
		calfin2.set(Calendar.HOUR_OF_DAY,  0);
		calfin2.set(Calendar.MINUTE, 0);
		calfin2.set(Calendar.SECOND, 0);
		
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.YEAR, 2019);
		cal3.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal3.set(Calendar.DAY_OF_MONTH,1);
		cal3.set(Calendar.HOUR_OF_DAY,  0);
		cal3.set(Calendar.MINUTE, 0);
		cal3.set(Calendar.SECOND, 0); 
		
		Calendar calfin3 = Calendar.getInstance();
		calfin3.set(Calendar.YEAR, 2019);
		calfin3.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin3.set(Calendar.DAY_OF_MONTH,12);
		calfin3.set(Calendar.HOUR_OF_DAY,  0);
		calfin3.set(Calendar.MINUTE, 0);
		calfin3.set(Calendar.SECOND, 0);
    	
    	Periode p1 = new Periode(cal.getTime(), calfin.getTime());
    	Periode p2 = new Periode(cal2.getTime(), calfin2.getTime());
    	Periode p3 = new Periode(cal3.getTime(), calfin3.getTime()); 
    	entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        
        Evenement ev1 = new Evenement("123456789", p1.getDateDebut(), spectacle1, "3T",listeComediens1);
        Evenement ev2 = new Evenement("09878765", p2.getDateDebut(), spectacle2, "3T D'A COTE",listeComediens2);
        Evenement ev3 = new Evenement("7777777777", p3.getDateDebut(), spectacle3, "3T D'A COTE",listeComediens3);
        Evenement ev4 = new Evenement("444444444", p3.getDateDebut(), spectacle1, "3T D'A COTE",listeComediens1);
        entityManager.persist(ev1);
        entityManager.persist(ev2);
        entityManager.persist(ev3);
        entityManager.persist(ev4);
        entityManager.flush();
        
        ArrayList<String> listeEvent1 = new ArrayList<String>();
        listeEvent1.add(ev1.getIdEvenement());
        listeEvent1.add(ev2.getIdEvenement());
        listeEvent1.add(ev3.getIdEvenement());
        listeEvent1.add(ev4.getIdEvenement());
        
        Collection<String> listeEvent2 = new ArrayList<String>();
        listeEvent2.add(ev1.getIdEvenement());
        listeEvent2.add(ev4.getIdEvenement());

        Collection<String> listeEvent3 = new ArrayList<String>();
        listeEvent3.add(ev1.getIdEvenement());
        listeEvent3.add(ev3.getIdEvenement());
        listeEvent3.add(ev4.getIdEvenement());
        
        Collection<String> listeEvent4 = new ArrayList<String>();
        listeEvent4.add(ev2.getIdEvenement());
        
        
        // when
        Collection<String> found1 = evenementRepository.getListEvenementsParComedien(comedien1.getId3T());
        Collection<String> found2 = evenementRepository.getListEvenementsParComedien(comedien2.getId3T());
        Collection<String> found3 = evenementRepository.getListEvenementsParComedien(comedien3.getId3T());
        Collection<String> found4 = evenementRepository.getListEvenementsParComedien(comedien4.getId3T());

        // then
        assertThat(found1)
          .isEqualTo(listeEvent1);    
        assertThat(found2)
        .isEqualTo(listeEvent2);
        assertThat(found3)
        .isEqualTo(listeEvent3);
        assertThat(found4)
        .isEqualTo(listeEvent4);
    }
    
    @Test
    public void whengetListEvenementsParComedienParPeriode_thenReturnListeEvent() {
    	// given
    	Comedien comedien1 = new Comedien("123456789", "Digne", "Sarah", null, null, null, "fdigne@me.com", null);    
        Comedien comedien2 = new Comedien("987654321", "Digne", "Florian", null, null, null, "fdigne2@me.com", null);
        Comedien comedien3 = new Comedien("0000000", "Vilardell", "Julie", null, null, null, "fdigne3@me.com", null);
        Comedien comedien4 = new Comedien("111111", "Andrieu", "Patrick", null, null, null, "fdigne4@me.com", null);
        
       Spectacle spectacle1 = new Spectacle("Les Clotildes");
       Spectacle spectacle2 = new Spectacle("En toutes amitiés");
       Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
     
       Collection<Comedien> listeRemplacants1 = new ArrayList<Comedien>() ;
       listeRemplacants1.add(comedien3);
       listeRemplacants1.add(comedien4);
       
       Set<Comedien> listeComediens1 = new HashSet<Comedien>() ;
       listeComediens1.add(comedien1);
       listeComediens1.add(comedien2);
       listeComediens1.add(comedien3);       
       Set<Comedien> listeComediens2 = new HashSet<Comedien>() ;
       listeComediens2.add(comedien4);
       listeComediens2.add(comedien1);
       Set<Comedien> listeComediens3 = new HashSet<Comedien>() ;
       listeComediens3.add(comedien3);
       listeComediens3.add(comedien1);
       
       Role role1 = new Role("Clotilde", spectacle1);
       role1.setComedienTitulaire(comedien1);
       role1.setListeRemplas(listeRemplacants1);
       Role role2 = new Role("Stagiaire", spectacle1);
       role2.setComedienTitulaire(comedien2);
       Role role3 = new Role("Clotilde Admin", spectacle1);
       role3.setComedienTitulaire(comedien3);
       Role role4 = new Role("Griberg", spectacle2);
       role4.setComedienTitulaire(comedien4);
       Role role5 = new Role("majordome", spectacle2);
       role5.setComedienTitulaire(comedien1);
       Role role6 = new Role("Femme", spectacle3);
       role6.setComedienTitulaire(comedien1);
       Role role7 = new Role("Homme", spectacle3);
       role7.setComedienTitulaire(comedien3);
       
       entityManager.persist(comedien1);
       entityManager.persist(comedien2);
       entityManager.persist(comedien3);
       entityManager.persist(comedien4);
       entityManager.persist(spectacle1);
       entityManager.persist(spectacle2);
       entityManager.persist(spectacle3);
       
       entityManager.persist(role1);
       entityManager.persist(role2);
       entityManager.persist(role3);
       entityManager.persist(role4);
       entityManager.persist(role5);
       entityManager.persist(role6);
       entityManager.persist(role7);
       
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH,3);
		cal.set(Calendar.HOUR_OF_DAY,  0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0); 
		
		Calendar calfin = Calendar.getInstance();
		calfin.set(Calendar.YEAR, 2019);
		calfin.set(Calendar.MONTH, Calendar.JANUARY);
		calfin.set(Calendar.DAY_OF_MONTH,15);
		calfin.set(Calendar.HOUR_OF_DAY,  0);
		calfin.set(Calendar.MINUTE, 0);
		calfin.set(Calendar.SECOND, 0);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, 2019);
		cal2.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal2.set(Calendar.DAY_OF_MONTH,3);
		cal2.set(Calendar.HOUR_OF_DAY,  0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0); 
		
		Calendar calfin2 = Calendar.getInstance();
		calfin2.set(Calendar.YEAR, 2019);
		calfin2.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin2.set(Calendar.DAY_OF_MONTH,12);
		calfin2.set(Calendar.HOUR_OF_DAY,  0);
		calfin2.set(Calendar.MINUTE, 0);
		calfin2.set(Calendar.SECOND, 0);
		
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.YEAR, 2019);
		cal3.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal3.set(Calendar.DAY_OF_MONTH,1);
		cal3.set(Calendar.HOUR_OF_DAY,  0);
		cal3.set(Calendar.MINUTE, 0);
		cal3.set(Calendar.SECOND, 0); 
		
		Calendar calfin3 = Calendar.getInstance();
		calfin3.set(Calendar.YEAR, 2019);
		calfin3.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin3.set(Calendar.DAY_OF_MONTH,12);
		calfin3.set(Calendar.HOUR_OF_DAY,  0);
		calfin3.set(Calendar.MINUTE, 0);
		calfin3.set(Calendar.SECOND, 0);
    	
    	Periode p1 = new Periode(cal.getTime(), calfin.getTime());
    	Periode p2 = new Periode(cal2.getTime(), calfin2.getTime());
    	Periode p3 = new Periode(cal3.getTime(), calfin3.getTime()); 
    	entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        
        Calendar cal4 = Calendar.getInstance();
		cal4.set(Calendar.YEAR, 2019);
		cal4.set(Calendar.MONTH, Calendar.JANUARY);
		cal4.set(Calendar.DAY_OF_MONTH,1);
		cal4.set(Calendar.HOUR_OF_DAY,  0);
		cal4.set(Calendar.MINUTE, 0);
		cal4.set(Calendar.SECOND, 0); 
		
		Calendar calfin4 = Calendar.getInstance();
		calfin4.set(Calendar.YEAR, 2019);
		calfin4.set(Calendar.MONTH, Calendar.JANUARY);
		calfin4.set(Calendar.DAY_OF_MONTH,30);
		calfin4.set(Calendar.HOUR_OF_DAY,  0);
		calfin4.set(Calendar.MINUTE, 0);
		calfin4.set(Calendar.SECOND, 0);
		
		Calendar cal5 = Calendar.getInstance();
		cal5.set(Calendar.YEAR, 2019);
		cal5.set(Calendar.MONTH, Calendar.JANUARY);
		cal5.set(Calendar.DAY_OF_MONTH,1);
		cal5.set(Calendar.HOUR_OF_DAY,  0);
		cal5.set(Calendar.MINUTE, 0);
		cal5.set(Calendar.SECOND, 0); 
		
		Calendar calfin5 = Calendar.getInstance();
		calfin5.set(Calendar.YEAR, 2019);
		calfin5.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin5.set(Calendar.DAY_OF_MONTH,28);
		calfin5.set(Calendar.HOUR_OF_DAY,  0);
		calfin5.set(Calendar.MINUTE, 0);
		calfin5.set(Calendar.SECOND, 0);
        
        Periode periodeFiltre1 = new Periode(cal4.getTime(), calfin4.getTime());
        Periode periodeFiltre2 = new Periode(cal5.getTime(), calfin5.getTime());
        entityManager.persist(periodeFiltre1);
        entityManager.persist(periodeFiltre2);
        
        Map<Long, Comedien> distrib1 = new HashMap<Long, Comedien>();
        distrib1.put(role1.getIdRole(), comedien1);
        distrib1.put(role2.getIdRole(), comedien2);
        distrib1.put(role3.getIdRole(), comedien3);
        
        Evenement ev1 = new Evenement("123456789", p1.getDateDebut(), spectacle1, "3T",listeComediens1);
        ev1.setCompagnie("333");
        ev1.setDistribution(distrib1);
        Evenement ev2 = new Evenement("09878765", p2.getDateDebut(), spectacle2, "3T D'A COTE",listeComediens2);
        ev2.setCompagnie("333");
        Evenement ev3 = new Evenement("7777777777", p3.getDateDebut(), spectacle3, "3T D'A COTE",listeComediens3);
        ev3.setCompagnie("333");
        Evenement ev4 = new Evenement("444444444", p3.getDateDebut(), spectacle1, "3T D'A COTE",listeComediens1);
        ev4.setCompagnie("333");
        ev4.setDistribution(distrib1);
        
        entityManager.persist(ev1);
        entityManager.persist(ev2);
        entityManager.persist(ev3);
        entityManager.persist(ev4);
        entityManager.flush();
        
        Collection<Evenement> listeEvent1 = new ArrayList<Evenement>();
        listeEvent1.add(ev1);
        
        Collection<Evenement> listeEvent2 = new ArrayList<Evenement>();
        listeEvent2.add(ev1);
        listeEvent2.add(ev4);        
        
        // when
        Collection<Evenement> found1 = evenementRepository.getListEvenementsParComedienParPeriode(comedien1.getId3T(), periodeFiltre1.getDateDebut(), periodeFiltre1.getDateFin(), "333");
        Collection<Evenement> found2 = evenementRepository.getListEvenementsParComedienParPeriode(comedien1.getId3T(), periodeFiltre2.getDateDebut(), periodeFiltre2.getDateFin(), "333");
        // then
        assertThat(found1)
        	.isEqualTo(listeEvent1);    
        assertThat(found2)
        	.isEqualTo(listeEvent2);
       
    } 
    
    @Test
    public void whengetNbreDatesParComedienParSpectacleParPeriodeParCompagnie_thenReturnNbreDates() {
    	// given
    	Comedien comedien1 = new Comedien("123456789", "Digne", "Sarah", null, null, null, "fdigne@me.com", null);    
        Comedien comedien2 = new Comedien("987654321", "Digne", "Florian", null, null, null, "fdigne2@me.com", null);
        Comedien comedien3 = new Comedien("0000000", "Vilardell", "Julie", null, null, null, "fdigne3@me.com", null);
        Comedien comedien4 = new Comedien("111111", "Andrieu", "Patrick", null, null, null, "fdigne4@me.com", null);
        
       Spectacle spectacle1 = new Spectacle("Les Clotildes");
       Spectacle spectacle2 = new Spectacle("En toutes amitiés");
       Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
     
       Collection<Comedien> listeRemplacants1 = new ArrayList<Comedien>() ;
       listeRemplacants1.add(comedien3);
       listeRemplacants1.add(comedien4);
       
       Set<Comedien> listeComediens1 = new HashSet<Comedien>() ;
       listeComediens1.add(comedien1);
       listeComediens1.add(comedien2);
       listeComediens1.add(comedien3);       
       Set<Comedien> listeComediens2 = new HashSet<Comedien>() ;
       listeComediens2.add(comedien4);
       listeComediens2.add(comedien1);
       Set<Comedien> listeComediens3 = new HashSet<Comedien>() ;
       listeComediens3.add(comedien3);
       listeComediens3.add(comedien1);
       
       Role role1 = new Role("Clotilde", spectacle1);
       role1.setComedienTitulaire(comedien1);
       role1.setListeRemplas(listeRemplacants1);
       Role role2 = new Role("Stagiaire", spectacle1);
       role2.setComedienTitulaire(comedien2);
       Role role3 = new Role("Clotilde Admin", spectacle1);
       role3.setComedienTitulaire(comedien3);
       Role role4 = new Role("Griberg", spectacle2);
       role4.setComedienTitulaire(comedien4);
       Role role5 = new Role("majordome", spectacle2);
       role5.setComedienTitulaire(comedien1);
       Role role6 = new Role("Femme", spectacle3);
       role6.setComedienTitulaire(comedien1);
       Role role7 = new Role("Homme", spectacle3);
       role7.setComedienTitulaire(comedien3);
       
       entityManager.persist(comedien1);
       entityManager.persist(comedien2);
       entityManager.persist(comedien3);
       entityManager.persist(comedien4);
       entityManager.persist(spectacle1);
       entityManager.persist(spectacle2);
       entityManager.persist(spectacle3);
       
       entityManager.persist(role1);
       entityManager.persist(role2);
       entityManager.persist(role3);
       entityManager.persist(role4);
       entityManager.persist(role5);
       entityManager.persist(role6);
       entityManager.persist(role7);
       
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH,3);
		cal.set(Calendar.HOUR_OF_DAY,  0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0); 
		
		Calendar calfin = Calendar.getInstance();
		calfin.set(Calendar.YEAR, 2019);
		calfin.set(Calendar.MONTH, Calendar.JANUARY);
		calfin.set(Calendar.DAY_OF_MONTH,15);
		calfin.set(Calendar.HOUR_OF_DAY,  0);
		calfin.set(Calendar.MINUTE, 0);
		calfin.set(Calendar.SECOND, 0);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, 2019);
		cal2.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal2.set(Calendar.DAY_OF_MONTH,3);
		cal2.set(Calendar.HOUR_OF_DAY,  0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0); 
		
		Calendar calfin2 = Calendar.getInstance();
		calfin2.set(Calendar.YEAR, 2019);
		calfin2.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin2.set(Calendar.DAY_OF_MONTH,12);
		calfin2.set(Calendar.HOUR_OF_DAY,  0);
		calfin2.set(Calendar.MINUTE, 0);
		calfin2.set(Calendar.SECOND, 0);
		
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.YEAR, 2019);
		cal3.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal3.set(Calendar.DAY_OF_MONTH,1);
		cal3.set(Calendar.HOUR_OF_DAY,  0);
		cal3.set(Calendar.MINUTE, 0);
		cal3.set(Calendar.SECOND, 0); 
		
		Calendar calfin3 = Calendar.getInstance();
		calfin3.set(Calendar.YEAR, 2019);
		calfin3.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin3.set(Calendar.DAY_OF_MONTH,12);
		calfin3.set(Calendar.HOUR_OF_DAY,  0);
		calfin3.set(Calendar.MINUTE, 0);
		calfin3.set(Calendar.SECOND, 0);
    	
    	Periode p1 = new Periode(cal.getTime(), calfin.getTime());
    	Periode p2 = new Periode(cal2.getTime(), calfin2.getTime());
    	Periode p3 = new Periode(cal3.getTime(), calfin3.getTime()); 
    	entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        
        Calendar cal4 = Calendar.getInstance();
		cal4.set(Calendar.YEAR, 2019);
		cal4.set(Calendar.MONTH, Calendar.JANUARY);
		cal4.set(Calendar.DAY_OF_MONTH,1);
		cal4.set(Calendar.HOUR_OF_DAY,  0);
		cal4.set(Calendar.MINUTE, 0);
		cal4.set(Calendar.SECOND, 0); 
		
		Calendar calfin4 = Calendar.getInstance();
		calfin4.set(Calendar.YEAR, 2019);
		calfin4.set(Calendar.MONTH, Calendar.JANUARY);
		calfin4.set(Calendar.DAY_OF_MONTH,30);
		calfin4.set(Calendar.HOUR_OF_DAY,  0);
		calfin4.set(Calendar.MINUTE, 0);
		calfin4.set(Calendar.SECOND, 0);
		
		Calendar cal5 = Calendar.getInstance();
		cal5.set(Calendar.YEAR, 2019);
		cal5.set(Calendar.MONTH, Calendar.JANUARY);
		cal5.set(Calendar.DAY_OF_MONTH,1);
		cal5.set(Calendar.HOUR_OF_DAY,  0);
		cal5.set(Calendar.MINUTE, 0);
		cal5.set(Calendar.SECOND, 0); 
		
		Calendar calfin5 = Calendar.getInstance();
		calfin5.set(Calendar.YEAR, 2019);
		calfin5.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin5.set(Calendar.DAY_OF_MONTH,28);
		calfin5.set(Calendar.HOUR_OF_DAY,  0);
		calfin5.set(Calendar.MINUTE, 0);
		calfin5.set(Calendar.SECOND, 0);
        
        Periode periodeFiltre1 = new Periode(cal4.getTime(), calfin4.getTime());
        Periode periodeFiltre2 = new Periode(cal5.getTime(), calfin5.getTime());
        entityManager.persist(periodeFiltre1);
        entityManager.persist(periodeFiltre2);
        
        Map<Long, Comedien> distrib1 = new HashMap<Long, Comedien>();
        distrib1.put(role1.getIdRole(), comedien1);
        distrib1.put(role2.getIdRole(), comedien2);
        distrib1.put(role3.getIdRole(), comedien3);
        
        Evenement ev1 = new Evenement("123456789", p1.getDateDebut(), spectacle1, "3T",listeComediens1);
        ev1.setCompagnie("333");
        ev1.setDistribution(distrib1);
        Evenement ev2 = new Evenement("09878765", p2.getDateDebut(), spectacle2, "3T D'A COTE",listeComediens2);
        ev2.setCompagnie("333");
        Evenement ev3 = new Evenement("7777777777", p3.getDateDebut(), spectacle3, "3T D'A COTE",listeComediens3);
        ev3.setCompagnie("333");
        Evenement ev4 = new Evenement("444444444", p1.getDateDebut(), spectacle1, "3T D'A COTE",listeComediens1);
        ev4.setCompagnie("333");
        ev4.setDistribution(distrib1);
        
        entityManager.persist(ev1);
        entityManager.persist(ev2);
        entityManager.persist(ev3);
        entityManager.persist(ev4);
        entityManager.flush();     
        
        // when
        int found1 = evenementRepository.getNbreDatesParComedienParSpectacleParPeriodeParCompagnie(comedien1.getId3T(),spectacle1.getIdSpectacle(),periodeFiltre1.getDateDebut(), periodeFiltre1.getDateFin(),"333");
        int found2 = evenementRepository.getNbreDatesParComedienParSpectacleParPeriodeParCompagnie(comedien1.getId3T(),spectacle2.getIdSpectacle(), periodeFiltre2.getDateDebut(), periodeFiltre2.getDateFin(), "333");
        int found3 = evenementRepository.getNbreDatesParComedienParSpectacleParPeriodeParCompagnie(comedien1.getId3T(),spectacle1.getIdSpectacle(), p3.getDateDebut(), p3.getDateFin(), "333");

        // then
        assertThat(found1)
        	.isEqualTo(2);    
        assertThat(found2)
        	.isEqualTo(0);
        assertThat(found3)
    	.isEqualTo(0);   
    }
    
    @Test
    public void whengetListSpectacleParComedienParPeriodeParCompagnie_thenReturnListeSpectacles() {
    	// given
    	Comedien comedien1 = new Comedien("123456789", "Digne", "Sarah", null, null, null, "fdigne@me.com", null);    
        Comedien comedien2 = new Comedien("987654321", "Digne", "Florian", null, null, null, "fdigne2@me.com", null);
        Comedien comedien3 = new Comedien("0000000", "Vilardell", "Julie", null, null, null, "fdigne3@me.com", null);
        Comedien comedien4 = new Comedien("111111", "Andrieu", "Patrick", null, null, null, "fdigne4@me.com", null);
        
       Spectacle spectacle1 = new Spectacle("Les Clotildes");
       Spectacle spectacle2 = new Spectacle("En toutes amitiés");
       Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
     
       Collection<Comedien> listeRemplacants1 = new ArrayList<Comedien>() ;
       listeRemplacants1.add(comedien3);
       listeRemplacants1.add(comedien4);
       
       Set<Comedien> listeComediens1 = new HashSet<Comedien>() ;
       listeComediens1.add(comedien1);
       listeComediens1.add(comedien2);
       listeComediens1.add(comedien3);       
       Set<Comedien> listeComediens2 = new HashSet<Comedien>() ;
       listeComediens2.add(comedien4);
       listeComediens2.add(comedien1);
       Set<Comedien> listeComediens3 = new HashSet<Comedien>() ;
       listeComediens3.add(comedien3);
       listeComediens3.add(comedien1);
       
       Role role1 = new Role("Clotilde", spectacle1);
       role1.setComedienTitulaire(comedien1);
       role1.setListeRemplas(listeRemplacants1);
       Role role2 = new Role("Stagiaire", spectacle1);
       role2.setComedienTitulaire(comedien2);
       Role role3 = new Role("Clotilde Admin", spectacle1);
       role3.setComedienTitulaire(comedien3);
       Role role4 = new Role("Griberg", spectacle2);
       role4.setComedienTitulaire(comedien4);
       Role role5 = new Role("majordome", spectacle2);
       role5.setComedienTitulaire(comedien1);
       Role role6 = new Role("Femme", spectacle3);
       role6.setComedienTitulaire(comedien1);
       Role role7 = new Role("Homme", spectacle3);
       role7.setComedienTitulaire(comedien3);
       
       entityManager.persist(comedien1);
       entityManager.persist(comedien2);
       entityManager.persist(comedien3);
       entityManager.persist(comedien4);
       entityManager.persist(spectacle1);
       entityManager.persist(spectacle2);
       entityManager.persist(spectacle3);
       
       entityManager.persist(role1);
       entityManager.persist(role2);
       entityManager.persist(role3);
       entityManager.persist(role4);
       entityManager.persist(role5);
       entityManager.persist(role6);
       entityManager.persist(role7);
       
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH,3);
		cal.set(Calendar.HOUR_OF_DAY,  0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0); 
		
		Calendar calfin = Calendar.getInstance();
		calfin.set(Calendar.YEAR, 2019);
		calfin.set(Calendar.MONTH, Calendar.JANUARY);
		calfin.set(Calendar.DAY_OF_MONTH,15);
		calfin.set(Calendar.HOUR_OF_DAY,  0);
		calfin.set(Calendar.MINUTE, 0);
		calfin.set(Calendar.SECOND, 0);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, 2019);
		cal2.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal2.set(Calendar.DAY_OF_MONTH,3);
		cal2.set(Calendar.HOUR_OF_DAY,  0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0); 
		
		Calendar calfin2 = Calendar.getInstance();
		calfin2.set(Calendar.YEAR, 2019);
		calfin2.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin2.set(Calendar.DAY_OF_MONTH,12);
		calfin2.set(Calendar.HOUR_OF_DAY,  0);
		calfin2.set(Calendar.MINUTE, 0);
		calfin2.set(Calendar.SECOND, 0);
		
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.YEAR, 2019);
		cal3.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal3.set(Calendar.DAY_OF_MONTH,1);
		cal3.set(Calendar.HOUR_OF_DAY,  0);
		cal3.set(Calendar.MINUTE, 0);
		cal3.set(Calendar.SECOND, 0); 
		
		Calendar calfin3 = Calendar.getInstance();
		calfin3.set(Calendar.YEAR, 2019);
		calfin3.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin3.set(Calendar.DAY_OF_MONTH,12);
		calfin3.set(Calendar.HOUR_OF_DAY,  0);
		calfin3.set(Calendar.MINUTE, 0);
		calfin3.set(Calendar.SECOND, 0);
    	
    	Periode p1 = new Periode(cal.getTime(), calfin.getTime());
    	Periode p2 = new Periode(cal2.getTime(), calfin2.getTime());
    	Periode p3 = new Periode(cal3.getTime(), calfin3.getTime()); 
    	entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        
        Calendar cal4 = Calendar.getInstance();
		cal4.set(Calendar.YEAR, 2019);
		cal4.set(Calendar.MONTH, Calendar.JANUARY);
		cal4.set(Calendar.DAY_OF_MONTH,1);
		cal4.set(Calendar.HOUR_OF_DAY,  0);
		cal4.set(Calendar.MINUTE, 0);
		cal4.set(Calendar.SECOND, 0); 
		
		Calendar calfin4 = Calendar.getInstance();
		calfin4.set(Calendar.YEAR, 2019);
		calfin4.set(Calendar.MONTH, Calendar.JANUARY);
		calfin4.set(Calendar.DAY_OF_MONTH,30);
		calfin4.set(Calendar.HOUR_OF_DAY,  0);
		calfin4.set(Calendar.MINUTE, 0);
		calfin4.set(Calendar.SECOND, 0);
		
		Calendar cal5 = Calendar.getInstance();
		cal5.set(Calendar.YEAR, 2019);
		cal5.set(Calendar.MONTH, Calendar.JANUARY);
		cal5.set(Calendar.DAY_OF_MONTH,1);
		cal5.set(Calendar.HOUR_OF_DAY,  0);
		cal5.set(Calendar.MINUTE, 0);
		cal5.set(Calendar.SECOND, 0); 
		
		Calendar calfin5 = Calendar.getInstance();
		calfin5.set(Calendar.YEAR, 2019);
		calfin5.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin5.set(Calendar.DAY_OF_MONTH,28);
		calfin5.set(Calendar.HOUR_OF_DAY,  0);
		calfin5.set(Calendar.MINUTE, 0);
		calfin5.set(Calendar.SECOND, 0);
        
        Periode periodeFiltre1 = new Periode(cal4.getTime(), calfin4.getTime());
        Periode periodeFiltre2 = new Periode(cal5.getTime(), calfin5.getTime());
        entityManager.persist(periodeFiltre1);
        entityManager.persist(periodeFiltre2);
        
        Map<Long, Comedien> distrib1 = new HashMap<Long, Comedien>();
        distrib1.put(role1.getIdRole(), comedien1);
        distrib1.put(role2.getIdRole(), comedien2);
        distrib1.put(role3.getIdRole(), comedien3);
        
        Evenement ev1 = new Evenement("123456789", p1.getDateDebut(), spectacle1, "3T",listeComediens1);
        ev1.setCompagnie("333");
        ev1.setDistribution(distrib1);
        Evenement ev2 = new Evenement("09878765", p2.getDateDebut(), spectacle2, "3T D'A COTE",listeComediens2);
        ev2.setCompagnie("333");
        Evenement ev3 = new Evenement("7777777777", p3.getDateDebut(), spectacle3, "3T D'A COTE",listeComediens3);
        ev3.setCompagnie("333");
        Evenement ev4 = new Evenement("444444444", p1.getDateDebut(), spectacle3, "3T D'A COTE",listeComediens1);
        ev4.setCompagnie("333");
        ev4.setDistribution(distrib1);
        
        entityManager.persist(ev1);
        entityManager.persist(ev2);
        entityManager.persist(ev3);
        entityManager.persist(ev4);
        entityManager.flush();
        
        Collection<BigInteger> expectedSpectacleList1 = new ArrayList<BigInteger>();
        expectedSpectacleList1.add(BigInteger.valueOf(spectacle1.getIdSpectacle()));
        expectedSpectacleList1.add(BigInteger.valueOf(spectacle3.getIdSpectacle()));
        
        Collection<Long> expectedSpectacleList2 = new ArrayList<Long>();
        
        // when
        Collection<BigInteger> found1 = evenementRepository.getListSpectacleParComedienParPeriodeParCompagnie(comedien1.getId3T(),periodeFiltre2.getDateDebut(),periodeFiltre2.getDateFin(), "333");
        Collection<BigInteger> found2 = evenementRepository.getListSpectacleParComedienParPeriodeParCompagnie(comedien1.getId3T(),p3.getDateDebut(),p3.getDateFin(), "333");

        // then
        assertThat(found1)
        	.isEqualTo(expectedSpectacleList1);    
        assertThat(found2)
        	.isEqualTo(expectedSpectacleList2);
    }
    
    @Test
    public void whengetListEvenementsParPeriode_thenReturnListeEvenements() {
    	// given
    	Comedien comedien1 = new Comedien("123456789", "Digne", "Sarah", null, null, null, "fdigne@me.com", null);    
        Comedien comedien2 = new Comedien("987654321", "Digne", "Florian", null, null, null, "fdigne2@me.com", null);
        Comedien comedien3 = new Comedien("0000000", "Vilardell", "Julie", null, null, null, "fdigne3@me.com", null);
        Comedien comedien4 = new Comedien("111111", "Andrieu", "Patrick", null, null, null, "fdigne4@me.com", null);
        
       Spectacle spectacle1 = new Spectacle("Les Clotildes");
       Spectacle spectacle2 = new Spectacle("En toutes amitiés");
       Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
     
       Collection<Comedien> listeRemplacants1 = new ArrayList<Comedien>() ;
       listeRemplacants1.add(comedien3);
       listeRemplacants1.add(comedien4);
       
       Set<Comedien> listeComediens1 = new HashSet<Comedien>() ;
       listeComediens1.add(comedien1);
       listeComediens1.add(comedien2);
       listeComediens1.add(comedien3);       
       Set<Comedien> listeComediens2 = new HashSet<Comedien>() ;
       listeComediens2.add(comedien4);
       listeComediens2.add(comedien1);
       Set<Comedien> listeComediens3 = new HashSet<Comedien>() ;
       listeComediens3.add(comedien3);
       listeComediens3.add(comedien1);
       
       Role role1 = new Role("Clotilde", spectacle1);
       role1.setComedienTitulaire(comedien1);
       role1.setListeRemplas(listeRemplacants1);
       Role role2 = new Role("Stagiaire", spectacle1);
       role2.setComedienTitulaire(comedien2);
       Role role3 = new Role("Clotilde Admin", spectacle1);
       role3.setComedienTitulaire(comedien3);
       Role role4 = new Role("Griberg", spectacle2);
       role4.setComedienTitulaire(comedien4);
       Role role5 = new Role("majordome", spectacle2);
       role5.setComedienTitulaire(comedien1);
       Role role6 = new Role("Femme", spectacle3);
       role6.setComedienTitulaire(comedien1);
       Role role7 = new Role("Homme", spectacle3);
       role7.setComedienTitulaire(comedien3);
       
       entityManager.persist(comedien1);
       entityManager.persist(comedien2);
       entityManager.persist(comedien3);
       entityManager.persist(comedien4);
       entityManager.persist(spectacle1);
       entityManager.persist(spectacle2);
       entityManager.persist(spectacle3);
       
       entityManager.persist(role1);
       entityManager.persist(role2);
       entityManager.persist(role3);
       entityManager.persist(role4);
       entityManager.persist(role5);
       entityManager.persist(role6);
       entityManager.persist(role7);
       
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH,3);
		cal.set(Calendar.HOUR_OF_DAY,  0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0); 
		
		Calendar calfin = Calendar.getInstance();
		calfin.set(Calendar.YEAR, 2019);
		calfin.set(Calendar.MONTH, Calendar.JANUARY);
		calfin.set(Calendar.DAY_OF_MONTH,15);
		calfin.set(Calendar.HOUR_OF_DAY,  0);
		calfin.set(Calendar.MINUTE, 0);
		calfin.set(Calendar.SECOND, 0);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, 2019);
		cal2.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal2.set(Calendar.DAY_OF_MONTH,3);
		cal2.set(Calendar.HOUR_OF_DAY,  0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0); 
		
		Calendar calfin2 = Calendar.getInstance();
		calfin2.set(Calendar.YEAR, 2019);
		calfin2.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin2.set(Calendar.DAY_OF_MONTH,12);
		calfin2.set(Calendar.HOUR_OF_DAY,  0);
		calfin2.set(Calendar.MINUTE, 0);
		calfin2.set(Calendar.SECOND, 0);
		
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.YEAR, 2019);
		cal3.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal3.set(Calendar.DAY_OF_MONTH,1);
		cal3.set(Calendar.HOUR_OF_DAY,  0);
		cal3.set(Calendar.MINUTE, 0);
		cal3.set(Calendar.SECOND, 0); 
		
		Calendar calfin3 = Calendar.getInstance();
		calfin3.set(Calendar.YEAR, 2019);
		calfin3.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin3.set(Calendar.DAY_OF_MONTH,12);
		calfin3.set(Calendar.HOUR_OF_DAY,  0);
		calfin3.set(Calendar.MINUTE, 0);
		calfin3.set(Calendar.SECOND, 0);
    	
    	Periode p1 = new Periode(cal.getTime(), calfin.getTime());
    	Periode p2 = new Periode(cal2.getTime(), calfin2.getTime());
    	Periode p3 = new Periode(cal3.getTime(), calfin3.getTime()); 
    	entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        
        Calendar cal4 = Calendar.getInstance();
		cal4.set(Calendar.YEAR, 2019);
		cal4.set(Calendar.MONTH, Calendar.JANUARY);
		cal4.set(Calendar.DAY_OF_MONTH,1);
		cal4.set(Calendar.HOUR_OF_DAY,  0);
		cal4.set(Calendar.MINUTE, 0);
		cal4.set(Calendar.SECOND, 0); 
		
		Calendar calfin4 = Calendar.getInstance();
		calfin4.set(Calendar.YEAR, 2019);
		calfin4.set(Calendar.MONTH, Calendar.JANUARY);
		calfin4.set(Calendar.DAY_OF_MONTH,30);
		calfin4.set(Calendar.HOUR_OF_DAY,  0);
		calfin4.set(Calendar.MINUTE, 0);
		calfin4.set(Calendar.SECOND, 0);
		
		Calendar cal5 = Calendar.getInstance();
		cal5.set(Calendar.YEAR, 2019);
		cal5.set(Calendar.MONTH, Calendar.SEPTEMBER);
		cal5.set(Calendar.DAY_OF_MONTH,1);
		cal5.set(Calendar.HOUR_OF_DAY,  0);
		cal5.set(Calendar.MINUTE, 0);
		cal5.set(Calendar.SECOND, 0); 
		
		Calendar calfin5 = Calendar.getInstance();
		calfin5.set(Calendar.YEAR, 2019);
		calfin5.set(Calendar.MONTH, Calendar.OCTOBER);
		calfin5.set(Calendar.DAY_OF_MONTH,28);
		calfin5.set(Calendar.HOUR_OF_DAY,  0);
		calfin5.set(Calendar.MINUTE, 0);
		calfin5.set(Calendar.SECOND, 0);
        
        Periode periodeFiltre1 = new Periode(cal4.getTime(), calfin4.getTime());
        Periode periodeFiltre2 = new Periode(cal5.getTime(), calfin5.getTime());
        entityManager.persist(periodeFiltre1);
        entityManager.persist(periodeFiltre2);
        
        Map<Long, Comedien> distrib1 = new HashMap<Long, Comedien>();
        distrib1.put(role1.getIdRole(), comedien1);
        distrib1.put(role2.getIdRole(), comedien2);
        distrib1.put(role3.getIdRole(), comedien3);
        
        Evenement ev1 = new Evenement("123456789", p1.getDateDebut(), spectacle1, "3T",listeComediens1);
        ev1.setCompagnie("333");
        ev1.setDistribution(distrib1);
        Evenement ev2 = new Evenement("09878765", p2.getDateDebut(), spectacle2, "3T D'A COTE",listeComediens2);
        ev2.setCompagnie("333");
        Evenement ev3 = new Evenement("7777777777", p3.getDateDebut(), spectacle3, "3T D'A COTE",listeComediens3);
        ev3.setCompagnie("333");
        Evenement ev4 = new Evenement("444444444", p1.getDateDebut(), spectacle3, "3T D'A COTE",listeComediens1);
        ev4.setCompagnie("333");
        ev4.setDistribution(distrib1);
        
        entityManager.persist(ev1);
        entityManager.persist(ev2);
        entityManager.persist(ev3);
        entityManager.persist(ev4);
        entityManager.flush();
        
        Collection<Evenement> expectedSpectacleList1 = new ArrayList<Evenement>();
        expectedSpectacleList1.add(ev1);
        expectedSpectacleList1.add(ev4);
        
        Collection<Evenement> expectedSpectacleList2 = new ArrayList<Evenement>();
        
        // when
        Collection<Evenement> found1 = evenementRepository.getListEvenementsParPeriode(periodeFiltre1.getDateDebut(), periodeFiltre1.getDateFin());
        Collection<Evenement> found2 = evenementRepository.getListEvenementsParPeriode(periodeFiltre2.getDateDebut(), periodeFiltre2.getDateFin());

        // then
        assertThat(found1)
        	.isEqualTo(expectedSpectacleList1);    
        assertThat(found2)
        	.isEqualTo(expectedSpectacleList2);
    }
    
    @Test
    public void whenggetNbreDatesParComedienParPeriode_thenReturnlistObjects() {
    	// given
    	Comedien comedien1 = new Comedien("123456789", "Digne", "Sarah", null, null, null, "fdigne@me.com", null);    
        Comedien comedien2 = new Comedien("987654321", "Digne", "Florian", null, null, null, "fdigne2@me.com", null);
        Comedien comedien3 = new Comedien("0000000", "Vilardell", "Julie", null, null, null, "fdigne3@me.com", null);
        Comedien comedien4 = new Comedien("111111", "Andrieu", "Patrick", null, null, null, "fdigne4@me.com", null);
        
       Spectacle spectacle1 = new Spectacle("Les Clotildes");
       Spectacle spectacle2 = new Spectacle("En toutes amitiés");
       Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
     
       Collection<Comedien> listeRemplacants1 = new ArrayList<Comedien>() ;
       listeRemplacants1.add(comedien3);
       listeRemplacants1.add(comedien4);
       
       Set<Comedien> listeComediens1 = new HashSet<Comedien>() ;
       listeComediens1.add(comedien1);
       listeComediens1.add(comedien2);
       listeComediens1.add(comedien3);       
       Set<Comedien> listeComediens2 = new HashSet<Comedien>() ;
       listeComediens2.add(comedien4);
       listeComediens2.add(comedien1);
       Set<Comedien> listeComediens3 = new HashSet<Comedien>() ;
       listeComediens3.add(comedien3);
       listeComediens3.add(comedien1);
       
       Role role1 = new Role("Clotilde", spectacle1);
       role1.setComedienTitulaire(comedien1);
       role1.setListeRemplas(listeRemplacants1);
       Role role2 = new Role("Stagiaire", spectacle1);
       role2.setComedienTitulaire(comedien2);
       Role role3 = new Role("Clotilde Admin", spectacle1);
       role3.setComedienTitulaire(comedien3);
       Role role4 = new Role("Griberg", spectacle2);
       role4.setComedienTitulaire(comedien4);
       Role role5 = new Role("majordome", spectacle2);
       role5.setComedienTitulaire(comedien1);
       Role role6 = new Role("Femme", spectacle3);
       role6.setComedienTitulaire(comedien1);
       Role role7 = new Role("Homme", spectacle3);
       role7.setComedienTitulaire(comedien3);
       
       entityManager.persist(comedien1);
       entityManager.persist(comedien2);
       entityManager.persist(comedien3);
       entityManager.persist(comedien4);
       entityManager.persist(spectacle1);
       entityManager.persist(spectacle2);
       entityManager.persist(spectacle3);
       
       entityManager.persist(role1);
       entityManager.persist(role2);
       entityManager.persist(role3);
       entityManager.persist(role4);
       entityManager.persist(role5);
       entityManager.persist(role6);
       entityManager.persist(role7);
       
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH,3);
		cal.set(Calendar.HOUR_OF_DAY,  0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0); 
		
		Calendar calfin = Calendar.getInstance();
		calfin.set(Calendar.YEAR, 2019);
		calfin.set(Calendar.MONTH, Calendar.JANUARY);
		calfin.set(Calendar.DAY_OF_MONTH,15);
		calfin.set(Calendar.HOUR_OF_DAY,  0);
		calfin.set(Calendar.MINUTE, 0);
		calfin.set(Calendar.SECOND, 0);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, 2019);
		cal2.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal2.set(Calendar.DAY_OF_MONTH,3);
		cal2.set(Calendar.HOUR_OF_DAY,  0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0); 
		
		Calendar calfin2 = Calendar.getInstance();
		calfin2.set(Calendar.YEAR, 2019);
		calfin2.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin2.set(Calendar.DAY_OF_MONTH,12);
		calfin2.set(Calendar.HOUR_OF_DAY,  0);
		calfin2.set(Calendar.MINUTE, 0);
		calfin2.set(Calendar.SECOND, 0);
		
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.YEAR, 2019);
		cal3.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal3.set(Calendar.DAY_OF_MONTH,1);
		cal3.set(Calendar.HOUR_OF_DAY,  0);
		cal3.set(Calendar.MINUTE, 0);
		cal3.set(Calendar.SECOND, 0); 
		
		Calendar calfin3 = Calendar.getInstance();
		calfin3.set(Calendar.YEAR, 2019);
		calfin3.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin3.set(Calendar.DAY_OF_MONTH,12);
		calfin3.set(Calendar.HOUR_OF_DAY,  0);
		calfin3.set(Calendar.MINUTE, 0);
		calfin3.set(Calendar.SECOND, 0);
    	
    	Periode p1 = new Periode(cal.getTime(), calfin.getTime());
    	Periode p2 = new Periode(cal2.getTime(), calfin2.getTime());
    	Periode p3 = new Periode(cal3.getTime(), calfin3.getTime()); 
    	entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        
        Calendar cal4 = Calendar.getInstance();
		cal4.set(Calendar.YEAR, 2019);
		cal4.set(Calendar.MONTH, Calendar.JANUARY);
		cal4.set(Calendar.DAY_OF_MONTH,1);
		cal4.set(Calendar.HOUR_OF_DAY,  0);
		cal4.set(Calendar.MINUTE, 0);
		cal4.set(Calendar.SECOND, 0); 
		
		Calendar calfin4 = Calendar.getInstance();
		calfin4.set(Calendar.YEAR, 2019);
		calfin4.set(Calendar.MONTH, Calendar.JANUARY);
		calfin4.set(Calendar.DAY_OF_MONTH,30);
		calfin4.set(Calendar.HOUR_OF_DAY,  0);
		calfin4.set(Calendar.MINUTE, 0);
		calfin4.set(Calendar.SECOND, 0);
		
		Calendar cal5 = Calendar.getInstance();
		cal5.set(Calendar.YEAR, 2019);
		cal5.set(Calendar.MONTH, Calendar.SEPTEMBER);
		cal5.set(Calendar.DAY_OF_MONTH,1);
		cal5.set(Calendar.HOUR_OF_DAY,  0);
		cal5.set(Calendar.MINUTE, 0);
		cal5.set(Calendar.SECOND, 0); 
		
		Calendar calfin5 = Calendar.getInstance();
		calfin5.set(Calendar.YEAR, 2019);
		calfin5.set(Calendar.MONTH, Calendar.OCTOBER);
		calfin5.set(Calendar.DAY_OF_MONTH,28);
		calfin5.set(Calendar.HOUR_OF_DAY,  0);
		calfin5.set(Calendar.MINUTE, 0);
		calfin5.set(Calendar.SECOND, 0);
        
        Periode periodeFiltre1 = new Periode(cal4.getTime(), calfin4.getTime());
        Periode periodeFiltre2 = new Periode(cal5.getTime(), calfin5.getTime());
        entityManager.persist(periodeFiltre1);
        entityManager.persist(periodeFiltre2);
        
        Map<Long, Comedien> distrib1 = new HashMap<Long, Comedien>();
        distrib1.put(role1.getIdRole(), comedien1);
        distrib1.put(role2.getIdRole(), comedien2);
        distrib1.put(role3.getIdRole(), comedien3);
        
        Evenement ev1 = new Evenement("123456789", p1.getDateDebut(), spectacle1, "3T",listeComediens1);
        ev1.setCompagnie("333");
        ev1.setDistribution(distrib1);
        Evenement ev2 = new Evenement("09878765", p2.getDateDebut(), spectacle2, "3T D'A COTE",listeComediens2);
        ev2.setCompagnie("333");
        Evenement ev3 = new Evenement("7777777777", p3.getDateDebut(), spectacle3, "3T D'A COTE",listeComediens3);
        ev3.setCompagnie("333");
        Evenement ev4 = new Evenement("444444444", p1.getDateDebut(), spectacle3, "3T D'A COTE",listeComediens1);
        ev4.setCompagnie("333");
        ev4.setDistribution(distrib1);
        
        entityManager.persist(ev1);
        entityManager.persist(ev2);
        entityManager.persist(ev3);
        entityManager.persist(ev4);
        entityManager.flush();
        
        // when
        Collection<Object[]> found1 = evenementRepository.getNbreDatesParComedienParPeriode(periodeFiltre1.getDateDebut(), periodeFiltre1.getDateFin());
        Collection<Object[]> found2 = evenementRepository.getNbreDatesParComedienParPeriode(periodeFiltre2.getDateDebut(), periodeFiltre2.getDateFin());
        
        
        // TODO : finish this test
    }
    
    @Test
    public void whengetNbreDatesParComedienParSpectacleParPeriode_thenReturnInt() {
    	// given
    	Comedien comedien1 = new Comedien("123456789", "Digne", "Sarah", null, null, null, "fdigne@me.com", null);    
        Comedien comedien2 = new Comedien("987654321", "Digne", "Florian", null, null, null, "fdigne2@me.com", null);
        Comedien comedien3 = new Comedien("0000000", "Vilardell", "Julie", null, null, null, "fdigne3@me.com", null);
        Comedien comedien4 = new Comedien("111111", "Andrieu", "Patrick", null, null, null, "fdigne4@me.com", null);
        
       Spectacle spectacle1 = new Spectacle("Les Clotildes");
       Spectacle spectacle2 = new Spectacle("En toutes amitiés");
       Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
     
       Collection<Comedien> listeRemplacants1 = new ArrayList<Comedien>() ;
       listeRemplacants1.add(comedien3);
       listeRemplacants1.add(comedien4);
       
       Set<Comedien> listeComediens1 = new HashSet<Comedien>() ;
       listeComediens1.add(comedien1);
       listeComediens1.add(comedien2);
       listeComediens1.add(comedien3);       
       Set<Comedien> listeComediens2 = new HashSet<Comedien>() ;
       listeComediens2.add(comedien4);
       listeComediens2.add(comedien1);
       Set<Comedien> listeComediens3 = new HashSet<Comedien>() ;
       listeComediens3.add(comedien3);
       listeComediens3.add(comedien1);
       
       Role role1 = new Role("Clotilde", spectacle1);
       role1.setComedienTitulaire(comedien1);
       role1.setListeRemplas(listeRemplacants1);
       Role role2 = new Role("Stagiaire", spectacle1);
       role2.setComedienTitulaire(comedien2);
       Role role3 = new Role("Clotilde Admin", spectacle1);
       role3.setComedienTitulaire(comedien3);
       Role role4 = new Role("Griberg", spectacle2);
       role4.setComedienTitulaire(comedien4);
       Role role5 = new Role("majordome", spectacle2);
       role5.setComedienTitulaire(comedien1);
       Role role6 = new Role("Femme", spectacle3);
       role6.setComedienTitulaire(comedien1);
       Role role7 = new Role("Homme", spectacle3);
       role7.setComedienTitulaire(comedien3);
       
       entityManager.persist(comedien1);
       entityManager.persist(comedien2);
       entityManager.persist(comedien3);
       entityManager.persist(comedien4);
       entityManager.persist(spectacle1);
       entityManager.persist(spectacle2);
       entityManager.persist(spectacle3);
       
       entityManager.persist(role1);
       entityManager.persist(role2);
       entityManager.persist(role3);
       entityManager.persist(role4);
       entityManager.persist(role5);
       entityManager.persist(role6);
       entityManager.persist(role7);
       
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH,3);
		cal.set(Calendar.HOUR_OF_DAY,  0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0); 
		
		Calendar calfin = Calendar.getInstance();
		calfin.set(Calendar.YEAR, 2019);
		calfin.set(Calendar.MONTH, Calendar.JANUARY);
		calfin.set(Calendar.DAY_OF_MONTH,15);
		calfin.set(Calendar.HOUR_OF_DAY,  0);
		calfin.set(Calendar.MINUTE, 0);
		calfin.set(Calendar.SECOND, 0);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, 2019);
		cal2.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal2.set(Calendar.DAY_OF_MONTH,3);
		cal2.set(Calendar.HOUR_OF_DAY,  0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0); 
		
		Calendar calfin2 = Calendar.getInstance();
		calfin2.set(Calendar.YEAR, 2019);
		calfin2.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin2.set(Calendar.DAY_OF_MONTH,12);
		calfin2.set(Calendar.HOUR_OF_DAY,  0);
		calfin2.set(Calendar.MINUTE, 0);
		calfin2.set(Calendar.SECOND, 0);
		
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.YEAR, 2019);
		cal3.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal3.set(Calendar.DAY_OF_MONTH,1);
		cal3.set(Calendar.HOUR_OF_DAY,  0);
		cal3.set(Calendar.MINUTE, 0);
		cal3.set(Calendar.SECOND, 0); 
		
		Calendar calfin3 = Calendar.getInstance();
		calfin3.set(Calendar.YEAR, 2019);
		calfin3.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin3.set(Calendar.DAY_OF_MONTH,12);
		calfin3.set(Calendar.HOUR_OF_DAY,  0);
		calfin3.set(Calendar.MINUTE, 0);
		calfin3.set(Calendar.SECOND, 0);
    	
    	Periode p1 = new Periode(cal.getTime(), calfin.getTime());
    	Periode p2 = new Periode(cal2.getTime(), calfin2.getTime());
    	Periode p3 = new Periode(cal3.getTime(), calfin3.getTime()); 
    	entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        
        Calendar cal4 = Calendar.getInstance();
		cal4.set(Calendar.YEAR, 2019);
		cal4.set(Calendar.MONTH, Calendar.JANUARY);
		cal4.set(Calendar.DAY_OF_MONTH,1);
		cal4.set(Calendar.HOUR_OF_DAY,  0);
		cal4.set(Calendar.MINUTE, 0);
		cal4.set(Calendar.SECOND, 0); 
		
		Calendar calfin4 = Calendar.getInstance();
		calfin4.set(Calendar.YEAR, 2019);
		calfin4.set(Calendar.MONTH, Calendar.JANUARY);
		calfin4.set(Calendar.DAY_OF_MONTH,30);
		calfin4.set(Calendar.HOUR_OF_DAY,  0);
		calfin4.set(Calendar.MINUTE, 0);
		calfin4.set(Calendar.SECOND, 0);
		
		Calendar cal5 = Calendar.getInstance();
		cal5.set(Calendar.YEAR, 2019);
		cal5.set(Calendar.MONTH, Calendar.SEPTEMBER);
		cal5.set(Calendar.DAY_OF_MONTH,1);
		cal5.set(Calendar.HOUR_OF_DAY,  0);
		cal5.set(Calendar.MINUTE, 0);
		cal5.set(Calendar.SECOND, 0); 
		
		Calendar calfin5 = Calendar.getInstance();
		calfin5.set(Calendar.YEAR, 2019);
		calfin5.set(Calendar.MONTH, Calendar.OCTOBER);
		calfin5.set(Calendar.DAY_OF_MONTH,28);
		calfin5.set(Calendar.HOUR_OF_DAY,  0);
		calfin5.set(Calendar.MINUTE, 0);
		calfin5.set(Calendar.SECOND, 0);
        
        Periode periodeFiltre1 = new Periode(cal4.getTime(), calfin4.getTime());
        Periode periodeFiltre2 = new Periode(cal5.getTime(), calfin5.getTime());
        entityManager.persist(periodeFiltre1);
        entityManager.persist(periodeFiltre2);
        
        Map<Long, Comedien> distrib1 = new HashMap<Long, Comedien>();
        distrib1.put(role1.getIdRole(), comedien1);
        distrib1.put(role2.getIdRole(), comedien2);
        distrib1.put(role3.getIdRole(), comedien3);
        
        Evenement ev1 = new Evenement("123456789", p1.getDateDebut(), spectacle1, "3T",listeComediens1);
        ev1.setCompagnie("333");
        ev1.setDistribution(distrib1);
        Evenement ev2 = new Evenement("09878765", p2.getDateDebut(), spectacle2, "3T D'A COTE",listeComediens2);
        ev2.setCompagnie("333");
        Evenement ev3 = new Evenement("7777777777", p3.getDateDebut(), spectacle3, "3T D'A COTE",listeComediens3);
        ev3.setCompagnie("333");
        Evenement ev4 = new Evenement("444444444", p1.getDateDebut(), spectacle1, "3T D'A COTE",listeComediens1);
        ev4.setCompagnie("333");
        ev4.setDistribution(distrib1);
        
        entityManager.persist(ev1);
        entityManager.persist(ev2);
        entityManager.persist(ev3);
        entityManager.persist(ev4);
        entityManager.flush();
        
        // when
        int found1 = evenementRepository.getNbreDatesParComedienParSpectacleParPeriode(comedien1.getId3T(), spectacle1.getIdSpectacle(), periodeFiltre1.getDateDebut(), periodeFiltre1.getDateFin());
        int found2 = evenementRepository.getNbreDatesParComedienParSpectacleParPeriode(comedien1.getId3T(), spectacle3.getIdSpectacle(), periodeFiltre2.getDateDebut(), periodeFiltre2.getDateFin());
        
     // then
        assertThat(found1)
        	.isEqualTo(2);    
        assertThat(found2)
        	.isEqualTo(0);
    }
    
    @Test
    public void whengetListSpectacleParComedienParPeriode_thenReturnListSpectacle() {
    	// given
    	Comedien comedien1 = new Comedien("123456789", "Digne", "Sarah", null, null, null, "fdigne@me.com", null);    
        Comedien comedien2 = new Comedien("987654321", "Digne", "Florian", null, null, null, "fdigne2@me.com", null);
        Comedien comedien3 = new Comedien("0000000", "Vilardell", "Julie", null, null, null, "fdigne3@me.com", null);
        Comedien comedien4 = new Comedien("111111", "Andrieu", "Patrick", null, null, null, "fdigne4@me.com", null);
        
       Spectacle spectacle1 = new Spectacle("Les Clotildes");
       Spectacle spectacle2 = new Spectacle("En toutes amitiés");
       Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
     
       Collection<Comedien> listeRemplacants1 = new ArrayList<Comedien>() ;
       listeRemplacants1.add(comedien3);
       listeRemplacants1.add(comedien4);
       
       Set<Comedien> listeComediens1 = new HashSet<Comedien>() ;
       listeComediens1.add(comedien1);
       listeComediens1.add(comedien2);
       listeComediens1.add(comedien3);       
       Set<Comedien> listeComediens2 = new HashSet<Comedien>() ;
       listeComediens2.add(comedien4);
       listeComediens2.add(comedien1);
       Set<Comedien> listeComediens3 = new HashSet<Comedien>() ;
       listeComediens3.add(comedien3);
       listeComediens3.add(comedien1);
       
       Role role1 = new Role("Clotilde", spectacle1);
       role1.setComedienTitulaire(comedien1);
       role1.setListeRemplas(listeRemplacants1);
       Role role2 = new Role("Stagiaire", spectacle1);
       role2.setComedienTitulaire(comedien2);
       Role role3 = new Role("Clotilde Admin", spectacle1);
       role3.setComedienTitulaire(comedien3);
       Role role4 = new Role("Griberg", spectacle2);
       role4.setComedienTitulaire(comedien4);
       Role role5 = new Role("majordome", spectacle2);
       role5.setComedienTitulaire(comedien1);
       Role role6 = new Role("Femme", spectacle3);
       role6.setComedienTitulaire(comedien1);
       Role role7 = new Role("Homme", spectacle3);
       role7.setComedienTitulaire(comedien3);
       
       entityManager.persist(comedien1);
       entityManager.persist(comedien2);
       entityManager.persist(comedien3);
       entityManager.persist(comedien4);
       entityManager.persist(spectacle1);
       entityManager.persist(spectacle2);
       entityManager.persist(spectacle3);
       
       entityManager.persist(role1);
       entityManager.persist(role2);
       entityManager.persist(role3);
       entityManager.persist(role4);
       entityManager.persist(role5);
       entityManager.persist(role6);
       entityManager.persist(role7);
       
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH,3);
		cal.set(Calendar.HOUR_OF_DAY,  0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0); 
		
		Calendar calfin = Calendar.getInstance();
		calfin.set(Calendar.YEAR, 2019);
		calfin.set(Calendar.MONTH, Calendar.JANUARY);
		calfin.set(Calendar.DAY_OF_MONTH,15);
		calfin.set(Calendar.HOUR_OF_DAY,  0);
		calfin.set(Calendar.MINUTE, 0);
		calfin.set(Calendar.SECOND, 0);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, 2019);
		cal2.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal2.set(Calendar.DAY_OF_MONTH,3);
		cal2.set(Calendar.HOUR_OF_DAY,  0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0); 
		
		Calendar calfin2 = Calendar.getInstance();
		calfin2.set(Calendar.YEAR, 2019);
		calfin2.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin2.set(Calendar.DAY_OF_MONTH,12);
		calfin2.set(Calendar.HOUR_OF_DAY,  0);
		calfin2.set(Calendar.MINUTE, 0);
		calfin2.set(Calendar.SECOND, 0);
		
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.YEAR, 2019);
		cal3.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal3.set(Calendar.DAY_OF_MONTH,1);
		cal3.set(Calendar.HOUR_OF_DAY,  0);
		cal3.set(Calendar.MINUTE, 0);
		cal3.set(Calendar.SECOND, 0); 
		
		Calendar calfin3 = Calendar.getInstance();
		calfin3.set(Calendar.YEAR, 2019);
		calfin3.set(Calendar.MONTH, Calendar.FEBRUARY);
		calfin3.set(Calendar.DAY_OF_MONTH,12);
		calfin3.set(Calendar.HOUR_OF_DAY,  0);
		calfin3.set(Calendar.MINUTE, 0);
		calfin3.set(Calendar.SECOND, 0);
    	
    	Periode p1 = new Periode(cal.getTime(), calfin.getTime());
    	Periode p2 = new Periode(cal2.getTime(), calfin2.getTime());
    	Periode p3 = new Periode(cal3.getTime(), calfin3.getTime()); 
    	entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        
        Calendar cal4 = Calendar.getInstance();
		cal4.set(Calendar.YEAR, 2019);
		cal4.set(Calendar.MONTH, Calendar.JANUARY);
		cal4.set(Calendar.DAY_OF_MONTH,1);
		cal4.set(Calendar.HOUR_OF_DAY,  0);
		cal4.set(Calendar.MINUTE, 0);
		cal4.set(Calendar.SECOND, 0); 
		
		Calendar calfin4 = Calendar.getInstance();
		calfin4.set(Calendar.YEAR, 2019);
		calfin4.set(Calendar.MONTH, Calendar.JANUARY);
		calfin4.set(Calendar.DAY_OF_MONTH,30);
		calfin4.set(Calendar.HOUR_OF_DAY,  0);
		calfin4.set(Calendar.MINUTE, 0);
		calfin4.set(Calendar.SECOND, 0);
		
		Calendar cal5 = Calendar.getInstance();
		cal5.set(Calendar.YEAR, 2019);
		cal5.set(Calendar.MONTH, Calendar.SEPTEMBER);
		cal5.set(Calendar.DAY_OF_MONTH,1);
		cal5.set(Calendar.HOUR_OF_DAY,  0);
		cal5.set(Calendar.MINUTE, 0);
		cal5.set(Calendar.SECOND, 0); 
		
		Calendar calfin5 = Calendar.getInstance();
		calfin5.set(Calendar.YEAR, 2019);
		calfin5.set(Calendar.MONTH, Calendar.OCTOBER);
		calfin5.set(Calendar.DAY_OF_MONTH,28);
		calfin5.set(Calendar.HOUR_OF_DAY,  0);
		calfin5.set(Calendar.MINUTE, 0);
		calfin5.set(Calendar.SECOND, 0);
        
        Periode periodeFiltre1 = new Periode(cal4.getTime(), calfin4.getTime());
        Periode periodeFiltre2 = new Periode(cal5.getTime(), calfin5.getTime());
        entityManager.persist(periodeFiltre1);
        entityManager.persist(periodeFiltre2);
        
        Map<Long, Comedien> distrib1 = new HashMap<Long, Comedien>();
        distrib1.put(role1.getIdRole(), comedien1);
        distrib1.put(role2.getIdRole(), comedien2);
        distrib1.put(role3.getIdRole(), comedien3);
        
        Evenement ev1 = new Evenement("123456789", p1.getDateDebut(), spectacle1, "3T",listeComediens1);
        ev1.setCompagnie("333");
        ev1.setDistribution(distrib1);
        Evenement ev2 = new Evenement("09878765", p2.getDateDebut(), spectacle2, "3T D'A COTE",listeComediens2);
        ev2.setCompagnie("333");
        Evenement ev3 = new Evenement("7777777777", p3.getDateDebut(), spectacle3, "3T D'A COTE",listeComediens3);
        ev3.setCompagnie("333");
        Evenement ev4 = new Evenement("444444444", p1.getDateDebut(), spectacle1, "3T D'A COTE",listeComediens1);
        ev4.setCompagnie("333");
        ev4.setDistribution(distrib1);
        
        entityManager.persist(ev1);
        entityManager.persist(ev2);
        entityManager.persist(ev3);
        entityManager.persist(ev4);
        entityManager.flush();
        
        Collection<BigInteger> listeSpectacleExpected1 = new ArrayList<BigInteger>();
        Collection<BigInteger> listeSpectacleExpected2 = new ArrayList<BigInteger>();

        listeSpectacleExpected1.add(BigInteger.valueOf(spectacle1.getIdSpectacle())) ;        

        // when
        Collection<BigInteger> found1 = evenementRepository.getListSpectacleParComedienParPeriode(comedien1.getId3T(), periodeFiltre1.getDateDebut(), periodeFiltre1.getDateFin());
        Collection<BigInteger> found2 = evenementRepository.getListSpectacleParComedienParPeriode(comedien2.getId3T(), periodeFiltre2.getDateDebut(), periodeFiltre1.getDateFin());

     // then
        assertThat(found1)
        	.isEqualTo(listeSpectacleExpected1);    
        assertThat(found2)
        	.isEqualTo(listeSpectacleExpected2);
    }
}