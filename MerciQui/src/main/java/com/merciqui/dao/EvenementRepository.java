package com.merciqui.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.merciqui.entities.Evenement;

public interface EvenementRepository extends JpaRepository<Evenement, String> {
		
	@Query(value ="SELECT count(*) FROM evenement_liste_comediens WHERE liste_comediens_id3t = ?1", nativeQuery = true)
	int getNbreDatesByComedien(@Param("x")String id3T);
	
	@Query("select o from Evenement o where o.spectacle.idSpectacle=:x")
	Collection<Evenement> getListEvenementsParSpectacle(@Param("x")Long idSpectacle);
}
