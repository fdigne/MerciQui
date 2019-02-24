package com.merciqui.dao;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	
	@Query(value = "select * from evenement_distribution d INNER JOIN evenement e ON d.evenement_id_evenement=e.id_evenement AND d.distribution_id3t=:x AND e.compagnie=:w AND e.date_evenement>=:y AND e.date_evenement<=:z ORDER BY e.date_evenement ASC", nativeQuery = true)
	Collection<Evenement> getListEvenementsParComedienParPeriode(@Param("x")String id3t, @Param("y")Date dateDebut,@Param("z")Date dateFin, @Param("w")String compagnie );

	@Query(value = "select count(*) from evenement_distribution d INNER JOIN evenement e ON d.evenement_id_evenement=e.id_evenement AND d.distribution_id3t=:x AND e.code_spectacle=:v AND e.compagnie=:w AND e.date_evenement>=:y AND e.date_evenement<=:z", nativeQuery = true)
	int getNbreDatesParComedienParSpectacleParPeriodeParCompagnie(@Param("x")String id3t,@Param("v")Long idSpectacle, @Param("y")Date dateDebut,@Param("z")Date dateFin, @Param("w")String compagnie );
	
	@Query(value = "select code_spectacle from evenement_distribution d INNER JOIN evenement e ON d.evenement_id_evenement=e.id_evenement AND d.distribution_id3t=:x AND e.compagnie=:w AND e.date_evenement>=:y AND e.date_evenement<=:z GROUP BY code_spectacle", nativeQuery = true)
	Collection<BigInteger> getListSpectacleParComedienParPeriodeParCompagnie(@Param("x")String id3t, @Param("y")Date dateDebut,@Param("z")Date dateFin, @Param("w")String compagnie);

	@Query("select o from Evenement o where o.dateEvenement>=:x and o.dateEvenement<=:y ORDER BY o.dateEvenement")
	Collection<Evenement> getListEvenementsParPeriode(@Param("x")Date dateDebut, @Param("y")Date dateFin);

	@Query(value = "select distribution_id3t,count(*) from evenement_distribution d INNER JOIN evenement e ON d.evenement_id_evenement=e.id_evenement WHERE e.date_evenement>=:y AND e.date_evenement<=:z GROUP BY d.distribution_id3t", nativeQuery = true)
	Collection<Object[]> getNbreDatesParComedienParPeriode(@Param("y")Date dateDebutFiltre, @Param("z")Date dateFinFiltre);

	@Query(value = "select count(*) from evenement_distribution d INNER JOIN evenement e ON d.evenement_id_evenement=e.id_evenement WHERE d.distribution_id3t=:x AND e.code_spectacle=:w AND e.date_evenement>=:y AND e.date_evenement<=:z", nativeQuery = true)
	int getNbreDatesParComedienParSpectacleParPeriode(@Param("x")String id3t, @Param("w")Long idSpectacle, @Param("y")Date dateDebutFiltre,
			@Param("z")Date dateFinFiltre);
	
	@Query(value = "select code_spectacle from evenement_distribution d INNER JOIN evenement e ON d.evenement_id_evenement=e.id_evenement WHERE d.distribution_id3t=:x AND e.date_evenement>=:y AND e.date_evenement<=:z GROUP BY code_spectacle", nativeQuery = true)
	Collection<BigInteger> getListSpectacleParComedienParPeriode(@Param("x")String id3t, @Param("y")Date dateDebutFiltre, @Param("z")Date dateFinFiltre);

	@Query(value = "SELECT count(*) FROM evenement_distribution d inner join evenement e ON d.evenement_id_evenement=e.id_evenement where d.distribution_id3t=:x and e.date_evenement >= now()", nativeQuery = true)
	int existEvenementFuturParComedien(@Param("x")String id3t);
	
	@Modifying(clearAutomatically = true)
	@Query(value="delete c from comedien_liste_indispos c left outer join evenement e on c.liste_indispos_id_periode = e.id_periode inner join periode on periode.id_periode = liste_indispos_id_periode where id_evenement is null and is_vacances = false ;", nativeQuery = true)
	void cleanIndisposComediens() ;
	
}
