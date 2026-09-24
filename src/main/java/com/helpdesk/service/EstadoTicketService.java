package com.helpdesk.service;

import java.util.List;

import com.helpdesk.entity.EstadoTicket;

public interface EstadoTicketService {

    List<EstadoTicket> listar();

    EstadoTicket guardar(EstadoTicket estado);

    EstadoTicket buscarPorId(Integer id);

    void eliminar(Integer id);
}
