package com.merciqui.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.merciqui.entities.Spectacle;


public interface SpectacleRepository extends JpaRepository<Spectacle, Long>{

	@Query("select o from Spectacle o where o.nomSpectacle=:x")
	Spectacle findOne(@Param("x") String nomSpectacle);
	
	@Query("select o from Spectacle o where o.nomSpectacle=:x")
	Collection<Spectacle> getListSpectaclesByComedien(@Param("x") String id3T);

}
