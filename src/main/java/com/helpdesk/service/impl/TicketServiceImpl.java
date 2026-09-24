package com.helpdesk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpdesk.entity.EstadoTicket;
import com.helpdesk.entity.Ticket;
import com.helpdesk.entity.Usuario;
import com.helpdesk.repository.TicketRepository;
import com.helpdesk.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository repository;

    @Override
    public List<Ticket> listar() {
        return repository.findAll();
    }

    @Override
    public Ticket guardar(Ticket ticket) {
        return repository.save(ticket);
    }

    @Override
    public Ticket buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Ticket> listarPorUsuario(Usuario usuario) {
        return repository.findByUsuario(usuario);
    }

    @Override
    public List<Ticket> listarPorEstado(EstadoTicket estado) {
        return repository.findByEstado(estado);
    }

    @Override
    public List<Ticket> buscarPorTitulo(String titulo) {
        return repository.findByTituloContaining(titulo);
    }
}
