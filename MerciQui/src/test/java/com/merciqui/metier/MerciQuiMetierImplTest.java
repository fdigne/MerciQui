package com.merciqui.metier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.merciqui.entities.Comedien;
import com.merciqui.entities.Evenement;
import com.merciqui.entities.Role;
import com.merciqui.entities.Spectacle;
import com.merciqui.metier.MerciQuiMetierImpl;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class MerciQuiMetierImplTest {
	
	
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
	
	@Test
    public void whenListeRole_thenReturnListeRole() {
		
		Spectacle spectacle1 = new Spectacle("aaa");
		Role role1 = new Role("clotilde1", spectacle1);
		Role role2 = new Role("clotilde2", spectacle1);
		Role role3 = new Role("clotilde3", spectacle1);

		
		Collection<Role> listeRole = new ArrayList<Role>() ;
		listeRole.add(role1);
		listeRole.add(role2);
		listeRole.add(role3);

		Mockito.when(merciQuiMetier.listeRoles()).thenReturn(listeRole);
		
		Collection<Role> result = merciQuiMetier.listeRoles();
		assertEquals(result, listeRole);		
	}
	
	@Test
    public void whenListeRoleParSpectacle_thenReturnListeRole() {
		
		Spectacle spectacle1 = new Spectacle("aaa");
		spectacle1.setIdSpectacle(1L);
		Spectacle spectacle2 = new Spectacle("bbb");
		spectacle2.setIdSpectacle(2L);

		Role role1 = new Role("clotilde1", spectacle1);
		Role role2 = new Role("clotilde2", spectacle1);
		Role role3 = new Role("clotilde3", spectacle1);
		Role role4 = new Role("clotilde4", spectacle1);
		Role role5 = new Role("clotilde5", spectacle1);
		Role role6 = new Role("clotilde6", spectacle1);


		
		Collection<Role> listeRole = new ArrayList<Role>() ;
		listeRole.add(role1);
		listeRole.add(role2);
		listeRole.add(role3);
		
		Collection<Role> listeRole2 = new ArrayList<Role>() ;
		listeRole2.add(role4);
		listeRole2.add(role5);
		listeRole2.add(role6);
		
		Collection<Role> listeRole3 = new ArrayList<Role>();

		Mockito.when(merciQuiMetier.listeRolesParSpectacle(1L)).thenReturn(listeRole);
		Mockito.when(merciQuiMetier.listeRolesParSpectacle(2L)).thenReturn(listeRole2);

		
		Collection<Role> result = merciQuiMetier.listeRolesParSpectacle(1L);
		Collection<Role> result2 = merciQuiMetier.listeRolesParSpectacle(2L);
		Collection<Role> result3 = merciQuiMetier.listeRolesParSpectacle(3L);

		assertEquals(result, listeRole);
		assertEquals(result2, listeRole2);
		assertEquals(result3, listeRole3);		
	}
	
	@Test
    public void whenCreerEvenement_thenReturnEvenement() {
		
		Spectacle spectacle1 = new Spectacle("aaa");
		spectacle1.setIdSpectacle(1L);
		
		Comedien expectedComedien1 = new Comedien("111", "Digne", "Florian", null, null, null, "fdigne@me.com",null);
		Comedien expectedComedien2 = new Comedien("222", "Digne", "Sarah", null, null, null, "fdigne@me.com",null);
		Comedien expectedComedien3 = new Comedien("333", "Digne", "Aaron", null, null, null, "fdigne@me.com",null);

		Set<Comedien> listeComedien = new HashSet<Comedien>();
		listeComedien.add(expectedComedien1);
		listeComedien.add(expectedComedien2);
		listeComedien.add(expectedComedien3);
		
		Evenement expectedEvenement = new Evenement("azerty", new Date(), spectacle1, "3T", listeComedien);
		Mockito.when(merciQuiMetier.creerEvenement(expectedEvenement)).thenReturn(expectedEvenement);
		
		Evenement result = merciQuiMetier.creerEvenement(expectedEvenement);
		assertEquals(result, expectedEvenement);	
	}
	

}
