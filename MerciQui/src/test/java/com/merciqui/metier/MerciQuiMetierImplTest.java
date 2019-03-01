package com.merciqui.metier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.merciqui.dao.ComedienRepository;
import com.merciqui.entities.Comedien;
import com.merciqui.entities.Spectacle;
import com.merciqui.metier.MerciQuiMetierImpl;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class MerciQuiMetierImplTest {
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Mock
	private MerciQuiMetierImpl merciQuiMetier;
	
	
	@Before
	public void init() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@Test
    public void whenConsulterComedien_thenReturnComedien() {
		
		Comedien expectedComedien = new Comedien("111", "Digne", "Florian", null, null, null, "fdigne@me.com",null);
		Mockito.when(merciQuiMetier.consulterComedien("111")).thenReturn(expectedComedien);
		
		Comedien result = merciQuiMetier.consulterComedien("111");
		Comedien result2 = merciQuiMetier.consulterComedien("2222");
		assertEquals(result, expectedComedien);	
		assertNull(result2);	
	}
	
	@Test
    public void whenListeComedien_thenReturnListeComedien() {
		
		Comedien expectedComedien1 = new Comedien("111", "Digne", "Florian", null, null, null, "fdigne@me.com",null);
		Comedien expectedComedien2 = new Comedien("222", "Digne", "Sarah", null, null, null, "fdigne@me.com",null);
		Comedien expectedComedien3 = new Comedien("333", "Digne", "Aaron", null, null, null, "fdigne@me.com",null);

		Collection<Comedien> listeComedien = new ArrayList<Comedien>();
		listeComedien.add(expectedComedien1);
		listeComedien.add(expectedComedien2);
		listeComedien.add(expectedComedien3);

		Mockito.when(merciQuiMetier.listeComediens()).thenReturn(listeComedien);
		
		Collection<Comedien> result = merciQuiMetier.listeComediens();
		assertEquals(result, listeComedien);		
	}
	
	@Test
    public void whenConsulterSpectacle_thenReturnSpectacle() {
		
		Spectacle spectacle = new Spectacle("Clotildes");
		Mockito.when(merciQuiMetier.consulterSpectacle("Clotildes")).thenReturn(spectacle);
		
		Spectacle result = merciQuiMetier.consulterSpectacle("Clotildes");
		Spectacle result2 = merciQuiMetier.consulterSpectacle("blabla");
		assertEquals(result, spectacle);	
		assertNull(result2);	
	}
	
	@Test
    public void whenListeSpectacle_thenReturnListeSpectacle() {
		
		Spectacle spectacle1 = new Spectacle("aaa");
		Spectacle spectacle2 = new Spectacle("bbb");
		Spectacle spectacle3 = new Spectacle("ccc");
		
		Collection<Spectacle> listeSpectacle1 = new ArrayList<Spectacle>() ;
		listeSpectacle1.add(spectacle1);
		listeSpectacle1.add(spectacle2);
		listeSpectacle1.add(spectacle3);

		Mockito.when(merciQuiMetier.listeSpectacles()).thenReturn(listeSpectacle1);
		
		Collection<Spectacle> result = merciQuiMetier.listeSpectacles();
		assertEquals(result, listeSpectacle1);		
	}
	
	

}
