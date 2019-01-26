package com.merciqui.entities;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.merciqui.dao.SpectacleRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SpectacleRepositoryInterationTest {
	
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private  SpectacleRepository spectacleRepository;
 
    @Test
    public void whenFindByName_thenReturnSpectacle() {
        // given
        Spectacle spectacle = new Spectacle("Les Clotildes");
        entityManager.persist(spectacle);
        entityManager.flush();
     
        // when
        Spectacle found = spectacleRepository.findOne(spectacle.getNomSpectacle());
     
        // then
        assertThat(found)
          .isEqualTo(spectacle);
    }
    
    @Test
    public void whenFindAll_thenReturnListSpectacle() {
        // given
        
        Spectacle spectacle1 = new Spectacle("Les Clotildes");
        Spectacle spectacle2 = new Spectacle("En toutes amitiés");
        Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
        entityManager.persist(spectacle1);
        entityManager.persist(spectacle2);
        entityManager.persist(spectacle3);

        entityManager.flush();
     
        Collection<Spectacle> listeSpectacle = new ArrayList<Spectacle>();
        listeSpectacle.add(spectacle1);
        listeSpectacle.add(spectacle2);
        listeSpectacle.add(spectacle3);

        
        // when
        List<Spectacle> found = spectacleRepository.findAll();
     
        // then
        assertThat(found)
          .isEqualTo(listeSpectacle);
    }
    
    @Test 
    public void whenDelete_thenReturnNoSpectacle() {
    	// given
    	 Spectacle spectacle1 = new Spectacle("Les Clotildes");
         Spectacle spectacle2 = new Spectacle("En toutes amitiés");
         Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
         entityManager.persist(spectacle1);
         entityManager.persist(spectacle2);
         entityManager.persist(spectacle3);

         entityManager.flush();
         
         Collection<Spectacle> listeSpectacle = new ArrayList<Spectacle>();
         listeSpectacle.add(spectacle1);
         listeSpectacle.add(spectacle2);
        
        // when
        spectacleRepository.delete(spectacle3.getIdSpectacle());
        Spectacle found = spectacleRepository.findOne(spectacle3.getIdSpectacle());
        List<Spectacle> found2 = spectacleRepository.findAll();
        
        
     // then
        assertThat(found).isNull();
        assertThat(found2).isEqualTo(listeSpectacle);
        assertThat(found2).doesNotContain(spectacle3);
    }
 
}