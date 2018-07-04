package com.merciqui.dao;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.merciqui.entities.Evenement;

public interface EvenementRepository extends JpaRepository<Evenement, String> {
		
	@Query(value ="SELECT count(*) FROM evenement_liste_comediens WHERE liste_comediens_id3t = ?1", nativeQuery = true)
	int getNbreDatesByComedien(@Param("x")String id3T);
	
	@Query("select o from Evenement o where o.spectacle.idSpectacle=:x ORDER BY o.dateEvenement")
	Collection<Evenement> getListEvenementsParSpectacle(@Param("x")Long idSpectacle);
	
	@Query(value ="SELECT liste_evenements_id_evenement FROM evenement_liste_comediens WHERE liste_comediens_id3t = ?1", nativeQuery = true)
	Collection<String> getListEvenementsParComedien(@Param("x")String id3T);
	
	@Query("select o from Evenement o where o.nomSalle=:x")
	Collection<Evenement> getListEvenementsParSalle(@Param("x")String nomSalle);
	
	@Query(value = "select * from evenement_distribution d INNER JOIN evenement e ON d.evenement_id_evenement=e.id_evenement AND d.distribution_id3t=:x AND e.compagnie=:w AND e.date_evenement>=:y AND e.date_evenement<=:z", nativeQuery = true)
	Collection<Evenement> getListEvenementsParComedienParPeriode(@Param("x")String id3t, @Param("y")Date dateDebut,@Param("z")Date dateFin, @Param("w")String compagnie );

	@Query(value = "select count(*) from evenement_distribution d INNER JOIN evenement e ON d.evenement_id_evenement=e.id_evenement AND d.distribution_id3t=:x AND e.code_spectacle=:v AND e.compagnie=:w AND e.date_evenement>=:y AND e.date_evenement<=:z", nativeQuery = true)
	int getNbreDatesParComedienParSpectacleParPeriodeParCompagnie(@Param("x")String id3t,@Param("v")Long idSpectacle, @Param("y")Date dateDebut,@Param("z")Date dateFin, @Param("w")String compagnie );
	
	@Query(value = "select code_spectacle from evenement_distribution d INNER JOIN evenement e ON d.evenement_id_evenement=e.id_evenement AND d.distribution_id3t=:x AND e.compagnie=:w AND e.date_evenement>=:y AND e.date_evenement<=:z GROUP BY code_spectacle", nativeQuery = true)
	Collection<Long> getListSpectacleParComedienParPeriodeParCompagnie(@Param("x")String id3t, @Param("y")Date dateDebut,@Param("z")Date dateFin, @Param("w")String compagnie);
}
