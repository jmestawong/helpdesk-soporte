package com.helpdesk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpdesk.entity.Prioridad;
import com.helpdesk.repository.PrioridadRepository;
import com.helpdesk.service.PrioridadService;

@Service
public class PrioridadServiceImpl implements PrioridadService {

    @Autowired
    private PrioridadRepository repository;

    @Override
    public List<Prioridad> listar() {
        return repository.findAll();
    }

    @Override
    public Prioridad guardar(Prioridad prioridad) {
        return repository.save(prioridad);
    }

    @Override
    public Prioridad buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
