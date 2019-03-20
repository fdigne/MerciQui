package com.merciqui.entities;

import static org.assertj.core.api.Assertions.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.merciqui.dao.RoleRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryIntegrationTest {
	
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private  RoleRepository roleRepository;
 
    @Test
    public void whenFindByName_thenReturnRole() {
        // given
    	Spectacle spectacle1 = new Spectacle("Les Clotildes");
        Spectacle spectacle2 = new Spectacle("En toutes amitiés");
        Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
        entityManager.persist(spectacle1);
        entityManager.persist(spectacle2);
        entityManager.persist(spectacle3);
        
        Role role1 = new Role("Clotilde", spectacle1);
        Role role2 = new Role("Stagiaire", spectacle1);
        Role role3 = new Role("Clotilde Admin", spectacle1);
        Role role4 = new Role("Griberg", spectacle2);
        Role role5 = new Role("majordome", spectacle2);
        Role role6 = new Role("Femme", spectacle3);
        Role role7 = new Role("Homme", spectacle3);
        entityManager.persist(role1);
        entityManager.persist(role2);
        entityManager.persist(role3);
        entityManager.persist(role4);
        entityManager.persist(role5);
        entityManager.persist(role6);
        entityManager.persist(role7);
        
        entityManager.flush();
     
        // when
        Role found = roleRepository.findOne(role1.getIdRole());
     
        // then
        assertThat(found)
          .isEqualTo(role1);
    }
    
    @Test
    public void whenFindAll_thenReturnListRoles() {
        // given
        
    	Spectacle spectacle1 = new Spectacle("Les Clotildes");
        Spectacle spectacle2 = new Spectacle("En toutes amitiés");
        Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
        entityManager.persist(spectacle1);
        entityManager.persist(spectacle2);
        entityManager.persist(spectacle3);
        
        Role role1 = new Role("Clotilde", spectacle1);
        Role role2 = new Role("Stagiaire", spectacle1);
        Role role3 = new Role("Clotilde Admin", spectacle1);
        Role role4 = new Role("Griberg", spectacle2);
        Role role5 = new Role("majordome", spectacle2);
        Role role6 = new Role("Femme", spectacle3);
        Role role7 = new Role("Homme", spectacle3);
        entityManager.persist(role1);
        entityManager.persist(role2);
        entityManager.persist(role3);
        entityManager.persist(role4);
        entityManager.persist(role5);
        entityManager.persist(role6);
        entityManager.persist(role7);
        
        entityManager.flush();
     
        Collection<Role> listeRoles = new ArrayList<Role>();
        listeRoles.add(role1);
        listeRoles.add(role2);
        listeRoles.add(role3);
        listeRoles.add(role4);
        listeRoles.add(role5);
        listeRoles.add(role6);
        listeRoles.add(role7);

        
        // when
        List<Role> found = roleRepository.findAll();
     
        // then
        assertThat(found)
          .isEqualTo(listeRoles);
    }
    
    @Test 
    public void whenDelete_thenReturnNoRole() {
    	// given
    	Spectacle spectacle1 = new Spectacle("Les Clotildes");
        Spectacle spectacle2 = new Spectacle("En toutes amitiés");
        Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
        entityManager.persist(spectacle1);
        entityManager.persist(spectacle2);
        entityManager.persist(spectacle3);
        
        Role role1 = new Role("Clotilde", spectacle1);
        Role role2 = new Role("Stagiaire", spectacle1);
        Role role3 = new Role("Clotilde Admin", spectacle1);
        Role role4 = new Role("Griberg", spectacle2);
        Role role5 = new Role("majordome", spectacle2);
        Role role6 = new Role("Femme", spectacle3);
        Role role7 = new Role("Homme", spectacle3);
        entityManager.persist(role1);
        entityManager.persist(role2);
        entityManager.persist(role3);
        entityManager.persist(role4);
        entityManager.persist(role5);
        entityManager.persist(role6);
        entityManager.persist(role7);
        
        entityManager.flush();
         
        Collection<Role> listeRoles = new ArrayList<Role>();
        listeRoles.add(role2);
        listeRoles.add(role3);
        listeRoles.add(role4);
        listeRoles.add(role5);
        listeRoles.add(role6);
        listeRoles.add(role7);
        
        // when
        roleRepository.delete(role1.getIdRole());
        Role found = roleRepository.findOne(role1.getIdRole());
        List<Role> found2 = roleRepository.findAll();
        
        
     // then
        assertThat(found).isNull();
        assertThat(found2).isEqualTo(listeRoles);
        assertThat(found2).doesNotContain(role1);
    }
    
    @Test
    public void whenGetListeRolesParSpectacle_thenReturnListRole() {
        // given
    	Spectacle spectacle1 = new Spectacle("Les Clotildes");
        Spectacle spectacle2 = new Spectacle("En toutes amitiés");
        Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
        entityManager.persist(spectacle1);
        entityManager.persist(spectacle2);
        entityManager.persist(spectacle3);
        
        Role role1 = new Role("Clotilde", spectacle1);
        Role role2 = new Role("Stagiaire", spectacle1);
        Role role3 = new Role("Clotilde Admin", spectacle1);
        Role role4 = new Role("Griberg", spectacle2);
        Role role5 = new Role("majordome", spectacle2);
        Role role6 = new Role("Femme", spectacle3);
        Role role7 = new Role("Homme", spectacle3);
        entityManager.persist(role1);
        entityManager.persist(role2);
        entityManager.persist(role3);
        entityManager.persist(role4);
        entityManager.persist(role5);
        entityManager.persist(role6);
        entityManager.persist(role7);
        
        entityManager.flush();
        
        Collection<Role> listeRoles = new ArrayList<Role>();
        listeRoles.add(role1);
        listeRoles.add(role2);
        listeRoles.add(role3);
     
        // when
        Collection<Role> found = roleRepository.getListeRolesParSpectacle(spectacle1.getIdSpectacle());
     
        // then
        assertThat(found)
          .isEqualTo(listeRoles);
        assertThat(found).doesNotContain(role5);
    }
    
    @Test
    public void whengetListeRemplacants_thenReturnListComedien() {
        // given
    	
Comedien comedien1 = new Comedien("Digne", "Sarah", null, null, null, "fdigne@me.com", null, null);
        
        Comedien comedien2 = new Comedien("Digne", "Florian", null, null, null, "fdigne2@me.com", null, null);
        
        Comedien comedien3 = new Comedien("Vilardell", "Julie", null, null, null, "fdigne3@me.com", null, null);
        
        Comedien comedien4 = new Comedien("Andrieu", "Patrick", null, null, null, "fdigne4@me.com", null, null);
         
    	Spectacle spectacle1 = new Spectacle("Les Clotildes");
        Spectacle spectacle2 = new Spectacle("En toutes amitiés");
        Spectacle spectacle3 = new Spectacle("Un diner d'adieu");
      
        Collection<Comedien> listeRemplacants1 = new ArrayList<Comedien>() ;
        listeRemplacants1.add(comedien3);
        listeRemplacants1.add(comedien4);
        
        Role role1 = new Role("Clotilde", spectacle1);
        role1.setComedienTitulaire(comedien1);
        role1.setListeRemplas(listeRemplacants1);
        Role role2 = new Role("Stagiaire", spectacle1);
        role2.setComedienTitulaire(comedien2);
        Role role3 = new Role("Clotilde Admin", spectacle1);
        Role role4 = new Role("Griberg", spectacle2);
        Role role5 = new Role("majordome", spectacle2);
        Role role6 = new Role("Femme", spectacle3);
        Role role7 = new Role("Homme", spectacle3);
        
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
        
        entityManager.flush();
        
        Collection<Comedien> listeRemplas = new ArrayList<Comedien>();
        listeRemplas.add(comedien3);
        listeRemplas.add(comedien4);
     
        // when
        Collection<Comedien> found = roleRepository.getListeRemplacants(role1.getIdRole());
     
        // then
        assertThat(found)
          .isEqualTo(listeRemplas);
        assertThat(found).doesNotContain(comedien1);
    }
}