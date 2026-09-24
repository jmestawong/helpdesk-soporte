package com.helpdesk.repository;

import java.util.List;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helpdesk.entity.EstadoTicket;
import com.helpdesk.entity.Ticket;
import com.helpdesk.entity.Usuario;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByUsuario(Usuario usuario);

    List<Ticket> findByEstado(EstadoTicket estado);

    List<Ticket> findByTituloContaining(String titulo);
    List<Ticket> findByFechaRegistroBetween(
            LocalDateTime inicio,
            LocalDateTime fin);
}