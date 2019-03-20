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
		
		Comedien expectedComedien = new Comedien(1L, "Digne", "Florian", null, null, null, "fdigne@me.com",null, null);
		Mockito.when(merciQuiMetier.consulterComedien(1L)).thenReturn(expectedComedien);
		
		Comedien result = merciQuiMetier.consulterComedien(1L);
		Comedien result2 = merciQuiMetier.consulterComedien(2L);
		assertEquals(result, expectedComedien);	
		assertNull(result2);	
	}
	
	@Test
    public void whenListeComedien_thenReturnListeComedien() {
		
		Comedien expectedComedien1 = new Comedien(1L, "Digne", "Florian", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien2 = new Comedien(2L, "Digne", "Sarah", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien3 = new Comedien(3L, "Digne", "Aaron", null, null, null, "fdigne@me.com",null, null);

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
		
		Comedien expectedComedien1 = new Comedien(1L, "Digne", "Florian", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien2 = new Comedien(2L, "Digne", "Sarah", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien3 = new Comedien(3L, "Digne", "Aaron", null, null, null, "fdigne@me.com",null, null);

		Set<Comedien> listeComedien = new HashSet<Comedien>();
		listeComedien.add(expectedComedien1);
		listeComedien.add(expectedComedien2);
		listeComedien.add(expectedComedien3);
		
		Evenement expectedEvenement = new Evenement("azerty", new Date(), spectacle1, "3T", listeComedien);
		Mockito.when(merciQuiMetier.creerEvenement(expectedEvenement)).thenReturn(expectedEvenement);
		
		Evenement result = merciQuiMetier.creerEvenement(expectedEvenement);
		assertEquals(result, expectedEvenement);	
	}
	
	@Test
    public void whenListeEvenement_thenReturnListeEvenement() {
		
		Spectacle spectacle1 = new Spectacle("aaa");
		spectacle1.setIdSpectacle(1L);
		
		Comedien expectedComedien1 = new Comedien(1L, "Digne", "Florian", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien2 = new Comedien(2L, "Digne", "Sarah", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien3 = new Comedien(3L, "Digne", "Aaron", null, null, null, "fdigne@me.com",null, null);

		Set<Comedien> listeComedien = new HashSet<Comedien>();
		listeComedien.add(expectedComedien1);
		listeComedien.add(expectedComedien2);
		listeComedien.add(expectedComedien3);
		
		Evenement ev1 = new Evenement("azerty", new Date(), spectacle1, "3T", listeComedien);
		Evenement ev2 = new Evenement("azerty", new Date(), spectacle1, "3T", listeComedien);
		Evenement ev3 = new Evenement("azerty", new Date(), spectacle1, "3T", listeComedien);
		Evenement ev4 = new Evenement("azerty", new Date(), spectacle1, "3T", listeComedien);
		
		Collection<Evenement> expectedListeEvenements = new ArrayList<Evenement>();
		expectedListeEvenements.add(ev1);
		expectedListeEvenements.add(ev2);
		expectedListeEvenements.add(ev3);
		expectedListeEvenements.add(ev4);

		Mockito.when(merciQuiMetier.listeEvenements()).thenReturn(expectedListeEvenements);
		
		Collection<Evenement> result = merciQuiMetier.listeEvenements();
		assertEquals(result, expectedListeEvenements);	
	}
	
	@Test
    public void whenListeEvenementParSpectacle_thenReturnListeEvenement() {
		
		Spectacle spectacle1 = new Spectacle("aaa");
		spectacle1.setIdSpectacle(1L);
		Spectacle spectacle2 = new Spectacle("bbb");
		spectacle2.setIdSpectacle(2L);
		Spectacle spectacle3 = new Spectacle("ccc");
		spectacle3.setIdSpectacle(3L);
		
		Comedien expectedComedien1 = new Comedien(1L, "Digne", "Florian", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien2 = new Comedien(2L, "Digne", "Sarah", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien3 = new Comedien(3L, "Digne", "Aaron", null, null, null, "fdigne@me.com",null, null);

		Set<Comedien> listeComedien = new HashSet<Comedien>();
		listeComedien.add(expectedComedien1);
		listeComedien.add(expectedComedien2);
		listeComedien.add(expectedComedien3);
		
		Evenement ev1 = new Evenement("azerty", new Date(), spectacle1, "3T", listeComedien);
		Evenement ev2 = new Evenement("qqqqq", new Date(), spectacle1, "3T", listeComedien);
		Evenement ev3 = new Evenement("dddd", new Date(), spectacle2, "3T", listeComedien);
		Evenement ev4 = new Evenement("kkkk", new Date(), spectacle1, "3T", listeComedien);
		
		Collection<Evenement> expectedListeEvenements1 = new ArrayList<Evenement>();
		expectedListeEvenements1.add(ev1);
		expectedListeEvenements1.add(ev2);
		expectedListeEvenements1.add(ev4);
		
		Collection<Evenement> expectedListeEvenements2 = new ArrayList<Evenement>();
		expectedListeEvenements2.add(ev3);
		
		Collection<Evenement> expectedListeEvenements3 = new ArrayList<Evenement>();



		Mockito.when(merciQuiMetier.listeEvenementsParSpectacle(spectacle1.getIdSpectacle())).thenReturn(expectedListeEvenements1);
		Mockito.when(merciQuiMetier.listeEvenementsParSpectacle(spectacle2.getIdSpectacle())).thenReturn(expectedListeEvenements2);
		Mockito.when(merciQuiMetier.listeEvenementsParSpectacle(spectacle3.getIdSpectacle())).thenReturn(expectedListeEvenements3);

		
		Collection<Evenement> result1 = merciQuiMetier.listeEvenementsParSpectacle(spectacle1.getIdSpectacle());
		Collection<Evenement> result2 = merciQuiMetier.listeEvenementsParSpectacle(spectacle2.getIdSpectacle());
		Collection<Evenement> result3 = merciQuiMetier.listeEvenementsParSpectacle(spectacle3.getIdSpectacle());

		assertEquals(result1, expectedListeEvenements1);
		assertEquals(result2, expectedListeEvenements2);
		assertEquals(result3, expectedListeEvenements3);
	}
	
	@Test
    public void whenConsulterEvenement_thenReturnEvenement() {
		
		Spectacle spectacle1 = new Spectacle("aaa");
		spectacle1.setIdSpectacle(1L);
		
		Comedien expectedComedien1 = new Comedien(1L, "Digne", "Florian", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien2 = new Comedien(2L, "Digne", "Sarah", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien3 = new Comedien(3L, "Digne", "Aaron", null, null, null, "fdigne@me.com",null, null);

		Set<Comedien> listeComedien = new HashSet<Comedien>();
		listeComedien.add(expectedComedien1);
		listeComedien.add(expectedComedien2);
		listeComedien.add(expectedComedien3);
		
		Evenement expectedEvenement = new Evenement("azerty", new Date(), spectacle1, "3T", listeComedien);
		Mockito.when(merciQuiMetier.consulterEvenement(expectedEvenement.getIdEvenement())).thenReturn(expectedEvenement);
		
		Evenement result = merciQuiMetier.consulterEvenement(expectedEvenement.getIdEvenement());
		assertEquals(result, expectedEvenement);	
	}
	
	@Test
    public void whengetNbreTotalDate_thenReturnInt() {
		
		Spectacle spectacle1 = new Spectacle("aaa");
		spectacle1.setIdSpectacle(1L);
		Spectacle spectacle2 = new Spectacle("bbb");
		spectacle2.setIdSpectacle(2L);
		Spectacle spectacle3 = new Spectacle("ccc");
		spectacle3.setIdSpectacle(3L);
		
		Comedien expectedComedien1 = new Comedien(1L, "Digne", "Florian", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien2 = new Comedien(2L, "Digne", "Sarah", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien3 = new Comedien(3L, "Digne", "Aaron", null, null, null, "fdigne@me.com",null, null);
		
		Mockito.when(merciQuiMetier.getNombreDatesTotal(expectedComedien1.getId3T())).thenReturn(3);
		Mockito.when(merciQuiMetier.getNombreDatesTotal(expectedComedien2.getId3T())).thenReturn(1);
		Mockito.when(merciQuiMetier.getNombreDatesTotal(expectedComedien3.getId3T())).thenReturn(0);
		
		int result1 = merciQuiMetier.getNombreDatesTotal(expectedComedien1.getId3T());
		int result2 = merciQuiMetier.getNombreDatesTotal(expectedComedien2.getId3T());
		int result3 = merciQuiMetier.getNombreDatesTotal(expectedComedien3.getId3T());

		assertEquals(result1, 3);
		assertEquals(result2, 1);
		assertEquals(result3, 0);
	}
	
	@Test
    public void whengetNombreDatesParSpectacleParComedien_thenReturnInt() {
		
		Spectacle spectacle1 = new Spectacle("aaa");
		spectacle1.setIdSpectacle(1L);
		Spectacle spectacle2 = new Spectacle("bbb");
		spectacle2.setIdSpectacle(2L);
		Spectacle spectacle3 = new Spectacle("ccc");
		spectacle3.setIdSpectacle(3L);
		
		Comedien expectedComedien1 = new Comedien(1L, "Digne", "Florian", null, null, null, "fdigne@me.com",null, null);
		
		Mockito.when(merciQuiMetier.getNombreDatesParSpectacleParComedien(spectacle1.getIdSpectacle(), expectedComedien1.getId3T())).thenReturn(1);
		
		int result1 = merciQuiMetier.getNombreDatesParSpectacleParComedien(spectacle1.getIdSpectacle(),expectedComedien1.getId3T());
		
		assertEquals(result1, 1);		
	}
	
	@Test
    public void whenGetNombreDatesParComedienParEvenement_thenReturnInt() {
		
		Spectacle spectacle1 = new Spectacle("aaa");
		spectacle1.setIdSpectacle(1L);
		Comedien expectedComedien1 = new Comedien(1L, "Digne", "Florian", null, null, null, "fdigne@me.com",null, null);
		Set<Comedien> listeComediens = new HashSet<Comedien>();
		listeComediens.add(expectedComedien1);
		
		Mockito.when(merciQuiMetier.getNombreDatesParComedienParEvenement(spectacle1.getIdSpectacle(), expectedComedien1.getId3T(), "3T", new Date(), new Date())).thenReturn(1);
		
		int result1 = merciQuiMetier.getNombreDatesParComedienParEvenement(spectacle1.getIdSpectacle(), expectedComedien1.getId3T(), "3T", new Date(), new Date());
		
		assertEquals(result1, 1);		
	}
	
	@Test
    public void whenlisteEvenementsParComedienParPeriodeParCompagnie_thenReturnCollectionEvenement() {
		
		Spectacle spectacle1 = new Spectacle("aaa");
		spectacle1.setIdSpectacle(1L);
		Spectacle spectacle2 = new Spectacle("bbb");
		spectacle2.setIdSpectacle(2L);
		Spectacle spectacle3 = new Spectacle("ccc");
		spectacle3.setIdSpectacle(3L);
		
		Comedien expectedComedien1 = new Comedien(1L, "Digne", "Florian", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien2 = new Comedien(2L, "Digne", "Sarah", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien3 = new Comedien(3L, "Digne", "Aaron", null, null, null, "fdigne@me.com",null, null);

		Set<Comedien> listeComedien = new HashSet<Comedien>();
		listeComedien.add(expectedComedien1);
		listeComedien.add(expectedComedien2);
		listeComedien.add(expectedComedien3);
		
		Evenement ev1 = new Evenement("azerty", new Date(), spectacle1, "3T", listeComedien);
		Evenement ev2 = new Evenement("qqqqq", new Date(), spectacle1, "3T", listeComedien);
		Evenement ev3 = new Evenement("dddd", new Date(), spectacle2, "3T", listeComedien);
		Evenement ev4 = new Evenement("kkkk", new Date(), spectacle1, "3T", listeComedien);
		
		Collection<Evenement> expectedListeEvenements1 = new ArrayList<Evenement>();
		expectedListeEvenements1.add(ev1);
		expectedListeEvenements1.add(ev2);
		expectedListeEvenements1.add(ev3);
		expectedListeEvenements1.add(ev4);
		
		
		Mockito.when(merciQuiMetier.listeEvenementsParComedienParPeriodeParCompagnie(1L, new Date(), new Date(), "333")).thenReturn(expectedListeEvenements1);
		
		Collection<Evenement> result1 = merciQuiMetier.listeEvenementsParComedienParPeriodeParCompagnie(1L, new Date(), new Date(), "333");
		
		assertEquals(result1, expectedListeEvenements1);		
	}
	
	@Test
    public void whenListeEvenementsParComedien_thenReturnCollectionEvenement() {
		
		Spectacle spectacle1 = new Spectacle("aaa");
		spectacle1.setIdSpectacle(1L);
		Spectacle spectacle2 = new Spectacle("bbb");
		spectacle2.setIdSpectacle(2L);
		Spectacle spectacle3 = new Spectacle("ccc");
		spectacle3.setIdSpectacle(3L);
		
		Comedien expectedComedien1 = new Comedien(1L, "Digne", "Florian", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien2 = new Comedien(2L, "Digne", "Sarah", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien3 = new Comedien(3L, "Digne", "Aaron", null, null, null, "fdigne@me.com",null, null);

		Set<Comedien> listeComedien = new HashSet<Comedien>();
		listeComedien.add(expectedComedien1);
		listeComedien.add(expectedComedien2);
		listeComedien.add(expectedComedien3);
		
		Evenement ev1 = new Evenement("azerty", new Date(), spectacle1, "3T", listeComedien);
		Evenement ev2 = new Evenement("qqqqq", new Date(), spectacle1, "3T", listeComedien);
		Evenement ev3 = new Evenement("dddd", new Date(), spectacle2, "3T", listeComedien);
		Evenement ev4 = new Evenement("kkkk", new Date(), spectacle1, "3T", listeComedien);
		
		Collection<Evenement> expectedListeEvenements1 = new ArrayList<Evenement>();
		expectedListeEvenements1.add(ev1);
		expectedListeEvenements1.add(ev2);
		expectedListeEvenements1.add(ev3);
		expectedListeEvenements1.add(ev4);
		
		
		Mockito.when(merciQuiMetier.listeEvenementsParComedien(expectedComedien1.getId3T())).thenReturn(expectedListeEvenements1);
		
		Collection<Evenement> result1 = merciQuiMetier.listeEvenementsParComedien(expectedComedien1.getId3T());
		
		assertEquals(result1, expectedListeEvenements1);		
	}
	
	@Test
    public void whenListeEvenementParSalle_thenReturnCollectionEvenement() {
		
		Spectacle spectacle1 = new Spectacle("aaa");
		spectacle1.setIdSpectacle(1L);
		Spectacle spectacle2 = new Spectacle("bbb");
		spectacle2.setIdSpectacle(2L);
		Spectacle spectacle3 = new Spectacle("ccc");
		spectacle3.setIdSpectacle(3L);
		
		Comedien expectedComedien1 = new Comedien(1L, "Digne", "Florian", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien2 = new Comedien(2L, "Digne", "Sarah", null, null, null, "fdigne@me.com",null, null);
		Comedien expectedComedien3 = new Comedien(3L, "Digne", "Aaron", null, null, null, "fdigne@me.com",null, null);

		Set<Comedien> listeComedien = new HashSet<Comedien>();
		listeComedien.add(expectedComedien1);
		listeComedien.add(expectedComedien2);
		listeComedien.add(expectedComedien3);
		
		Evenement ev1 = new Evenement("azerty", new Date(), spectacle1, "3T", listeComedien);
		Evenement ev2 = new Evenement("qqqqq", new Date(), spectacle1, "3T", listeComedien);
		Evenement ev3 = new Evenement("dddd", new Date(), spectacle2, "3T", listeComedien);
		Evenement ev4 = new Evenement("kkkk", new Date(), spectacle1, "3T", listeComedien);
		
		Collection<Evenement> expectedListeEvenements1 = new ArrayList<Evenement>();
		expectedListeEvenements1.add(ev1);
		expectedListeEvenements1.add(ev2);
		expectedListeEvenements1.add(ev3);
		expectedListeEvenements1.add(ev4);
		
		
		Mockito.when(merciQuiMetier.listeEvenementParSalle("salle")).thenReturn(expectedListeEvenements1);
		
		Collection<Evenement> result1 = merciQuiMetier.listeEvenementParSalle("salle");
		
		assertEquals(result1, expectedListeEvenements1);		
	}
}
