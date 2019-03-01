package com.merciqui.metier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

}
