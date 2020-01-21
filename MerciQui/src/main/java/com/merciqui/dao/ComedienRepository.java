package com.merciqui.dao;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.merciqui.entities.Comedien;
import com.merciqui.entities.Periode;

public interface ComedienRepository extends JpaRepository<Comedien, Long>{
	
	@Query(value="select listeIndispos from Comedien where id3t=:x")
	Collection<Periode> getListeIndispos(@Param("x")Long id3T);


	@Query(value = "select * from comedien where id3t in (select distribution_id3t from evenement_distribution where evenement_id_evenement in (select id_evenement " +
			"from evenement where date_evenement >= :x and date_evenement <= :y) group by distribution_id3t);", nativeQuery = true)
    Collection<Comedien> getListeComediensParPeriode(@Param("x") Date dateDebutFiltre, @Param("y")Date dateFinFiltre);
}
