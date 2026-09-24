package com.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helpdesk.entity.Prioridad;

@Repository
public interface PrioridadRepository extends JpaRepository<Prioridad, Integer> {

}