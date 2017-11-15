package com.merciqui.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merciqui.entities.Evenement;

public interface EvenementRepository extends JpaRepository<Evenement, Long> {

}
