package com.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helpdesk.entity.EstadoTicket;

@Repository
public interface EstadoTicketRepository extends JpaRepository<EstadoTicket, Integer> {

}
