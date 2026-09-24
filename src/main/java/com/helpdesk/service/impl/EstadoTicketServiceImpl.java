package com.helpdesk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpdesk.entity.EstadoTicket;
import com.helpdesk.repository.EstadoTicketRepository;
import com.helpdesk.service.EstadoTicketService;

@Service
public class EstadoTicketServiceImpl implements EstadoTicketService {

    @Autowired
    private EstadoTicketRepository repository;

    @Override
    public List<EstadoTicket> listar() {
        return repository.findAll();
    }

    @Override
    public EstadoTicket guardar(EstadoTicket estado) {
        return repository.save(estado);
    }

    @Override
    public EstadoTicket buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
