package com.merciqui.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merciqui.entities.Distribution;

public interface DistributionRepository extends JpaRepository<Distribution, Long> {

}
