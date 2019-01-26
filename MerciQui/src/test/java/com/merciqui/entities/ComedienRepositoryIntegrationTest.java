package com.merciqui.entities;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.merciqui.dao.ComedienRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ComedienRepositoryIntegrationTest {
	
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private  ComedienRepository comedienRepository;
    
    @Test
    public void whenFindAll_thenReturnListComedien() {
        // given
        Comedien comedien1 = new Comedien("123456789", "Digne", "Sarah", null, null, null, "fdigne@me.com", null);
        entityManager.persist(comedien1);
        
        Comedien comedien2 = new Comedien("987654321", "Digne", "Florian", null, null, null, "fdigne2@me.com", null);
        entityManager.persist(comedien2);
        entityManager.flush();
     
        Collection<Comedien> listeComediens = new ArrayList<Comedien>();
        listeComediens.add(comedien1);
        listeComediens.add(comedien2);
        
        // when
        List<Comedien> found = comedienRepository.findAll();
     
        // then
        assertThat(found)
          .isEqualTo(listeComediens);
    }
    
    @Test 
    public void whenDelete_thenReturnNoComedien() {
    	// given
        Comedien comedien1 = new Comedien("123456789", "Digne", "Sarah", null, null, null, "fdigne@me.com", null);
        entityManager.persist(comedien1);
        
        Comedien comedien2 = new Comedien("987654321", "Digne", "Florian", null, null, null, "fdigne2@me.com", null);
        entityManager.persist(comedien2);
        entityManager.flush();
        Collection<Comedien> listeComediens = new ArrayList<Comedien>();
        listeComediens.add(comedien2);
        
        // when
        comedienRepository.delete(comedien1.getId3T());
        Comedien found = comedienRepository.findOne(comedien1.getId3T());
        List<Comedien> found2 = comedienRepository.findAll();
        
        
     // then
        assertThat(found).isNull();
        assertThat(found2).isEqualTo(listeComediens);
        assertThat(found2).doesNotContain(comedien1);
    }
 
    @Test
    public void whenFindById3T_thenReturnComedien() {
        // given
        Comedien comedien = new Comedien("123456789", "Digne", "Sarah", null, null, null, "fdigne@me.com", null);
        entityManager.persist(comedien);
        entityManager.flush();
     
        // when
        Comedien found = comedienRepository.findOne(comedien.getId3T());
     
        // then
        assertThat(found)
          .isEqualTo(comedien);
    }
    
	@Test
    public void whenFindListIndispoById3T_thenReturnIndispos() {
        // given
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
    	
    	Periode p1 = new Periode(cal.getTime(), calfin.getTime());
    	Periode p2 = new Periode(cal2.getTime(), calfin2.getTime());
    	Collection<Periode> listeIndispos = new ArrayList<Periode>();
    	listeIndispos.add(p1);
    	listeIndispos.add(p2);
    	entityManager.persist(p1);
    	entityManager.persist(p2);
        Comedien comedien = new Comedien("123456789", "Digne", "Sarah", null, null, null, "fdigne@me.com", null);
        comedien.setListeIndispos(listeIndispos);
        entityManager.persist(comedien);
        entityManager.flush();
     
        // when
        Collection<Periode> found = comedienRepository.getListeIndispos(comedien.getId3T());
     
        // then
        assertThat(found)
          .isEqualTo(listeIndispos);
    }
}