package com.merciqui.entities;

import static org.assertj.core.api.Assertions.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.merciqui.dao.ComedienRepository;
import com.merciqui.dao.PeriodeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PeriodeRepositoryIntegrationTest {
	
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private  PeriodeRepository periodeRepository;
   
    @Autowired
    private  ComedienRepository comedienRepository;
 
    @Test
    public void whenFindByName_thenReturnPeriode() {
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
        
    	entityManager.persist(p1);
        entityManager.persist(p2);
        
        entityManager.flush();
     
        // when
        Periode found = periodeRepository.findOne(p1.getIdPeriode());
     
        // then
        assertThat(found)
          .isEqualTo(p1);
    }
    
    @Test
    public void whenFindAll_thenReturnListPeriodes() {
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
        
    	entityManager.persist(p1);
        entityManager.persist(p2);
        
        entityManager.flush();
     
        Collection<Periode> listePeriodes = new ArrayList<Periode>();
        listePeriodes.add(p1);
        listePeriodes.add(p2);
         
        // when
        Collection<Periode> found = periodeRepository.findAll();
     
        // then
        assertThat(found)
          .isEqualTo(listePeriodes);
    }
    
    @Test 
    public void whenDelete_thenReturnNoPeriode() {
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
        
    	entityManager.persist(p1);
        entityManager.persist(p2);
        
        entityManager.flush();
     
        Collection<Periode> listePeriodes = new ArrayList<Periode>();
        listePeriodes.add(p2);
        
        // when
        periodeRepository.delete(p1.getIdPeriode());
        Periode found = periodeRepository.findOne(p1.getIdPeriode());
        List<Periode> found2 = periodeRepository.findAll();
        
        
     // then
        assertThat(found).isNull();
        assertThat(found2).isEqualTo(listePeriodes);
        assertThat(found2).doesNotContain(p1);
    }
    
    @Test
    public void deletePeriodeComedien() {
    	// given
    	
    	Comedien comedien1 = new Comedien("123456789", "Digne", "Sarah", null, null, null, "fdigne@me.com", null);
        
        Comedien comedien2 = new Comedien("987654321", "Digne", "Florian", null, null, null, "fdigne2@me.com", null);
        
        Comedien comedien3 = new Comedien("0000000", "Vilardell", "Julie", null, null, null, "fdigne3@me.com", null);
        
        Comedien comedien4 = new Comedien("111111", "Andrieu", "Patrick", null, null, null, "fdigne4@me.com", null);
        
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
    	

    	Collection<Periode> listePeriodes = new ArrayList<Periode>();
        listePeriodes.add(p1);
        listePeriodes.add(p2);
          
    	comedien1.setListeIndispos(listePeriodes);
    	
    	Collection<Periode> listePeriodesAfterDelete = new ArrayList<Periode>();
        listePeriodesAfterDelete.add(p2);
        
    	entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(comedien1);
        entityManager.persist(comedien2);
        entityManager.persist(comedien3);
        entityManager.persist(comedien4);
        
        entityManager.flush();
     
     
        // when
        periodeRepository.deletePeriodeComedien(p1.getIdPeriode(),comedien1.getId3T());
        Comedien found = comedienRepository.findOne(comedien1.getId3T());
        // then
        assertThat(found.getListeIndispos().size())
          .isEqualTo(1);
        assertThat(found.getListeIndispos()).doesNotContain(p1);
    }
}