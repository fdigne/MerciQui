package com.merciqui.entities;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

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
}