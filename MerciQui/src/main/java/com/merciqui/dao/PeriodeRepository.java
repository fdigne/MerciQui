package com.merciqui.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.merciqui.entities.Periode;

public interface PeriodeRepository extends JpaRepository<Periode, Long> {
	
	@Modifying(clearAutomatically = true)
	@Query(value ="DELETE FROM `comedien_liste_indispos` WHERE `comedien_liste_indispos`.`liste_comediens_id3t` = ?2 AND `comedien_liste_indispos`.`liste_indispos_id_periode` = ?1", nativeQuery = true)
	void deletePeriodeComedien(Long idPeriode, Long id3T) ;
	
	@Modifying(clearAutomatically = true)
	@Query(value="delete p from periode p left outer join evenement e on p.id_periode=e.id_periode where id_evenement is null and is_vacances = false ;", nativeQuery = true)
	void cleanPeriode() ;
	
	@Modifying(clearAutomatically = true)
	@Query(value="INSERT INTO `comedien_liste_indispos`(`comedien_liste_indispos`.`liste_comediens_id3t`, `comedien_liste_indispos`.`liste_indispos_id_periode`) values (?2, ?1);", nativeQuery = true)
	void repairIndispos(Long idPeriode, Long id3T);

}
