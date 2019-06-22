package com.merciqui.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.merciqui.entities.Comedien;
import com.merciqui.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	@Query("select o from Role o where o.spectacle.idSpectacle=:x")
	Collection<Role> getListeRolesParSpectacle(@Param("x")Long idSpectacle);
	
	@Query(value="select listeRemplas from Role role where role.idRole=:x")
	Collection<Comedien> getListeRemplacants(@Param("x")Long idRole);
	

}
