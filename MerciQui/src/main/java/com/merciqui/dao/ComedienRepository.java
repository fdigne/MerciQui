package com.merciqui.dao;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.merciqui.entities.Comedien;
import com.merciqui.entities.Periode;

public interface ComedienRepository extends JpaRepository<Comedien, String>{
	
	@Query(value="select listeIndispos from Comedien where id3t=:x")
	Collection<Periode> getListeIndispos(@Param("x")String id3T);
	
	
	
}
