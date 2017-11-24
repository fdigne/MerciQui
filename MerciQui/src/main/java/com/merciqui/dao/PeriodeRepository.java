package com.merciqui.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.merciqui.entities.Periode;

public interface PeriodeRepository extends JpaRepository<Periode, Long> {
	
	@Query(value ="DELETE FROM `comedien_liste_indispos` WHERE `comedien_liste_indispos`.`liste_comediens_id3t` = ?2 AND `comedien_liste_indispos`.`liste_indispos_id_periode` = ?1", nativeQuery = true)
	void deletePeriodeComedien(Long idPeriode, String id3T) ;

}