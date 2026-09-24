package com.helpdesk.service;

import java.util.List;

import com.helpdesk.entity.EstadoTicket;
import com.helpdesk.entity.Ticket;
import com.helpdesk.entity.Usuario;

public interface TicketService {

    List<Ticket> listar();

    Ticket guardar(Ticket ticket);

    Ticket buscarPorId(Integer id);

    void eliminar(Integer id);

    List<Ticket> listarPorUsuario(Usuario usuario);

    List<Ticket> listarPorEstado(EstadoTicket estado);

    List<Ticket> buscarPorTitulo(String titulo);
}
