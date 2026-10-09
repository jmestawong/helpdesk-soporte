package com.helpdesk.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpdesk.entity.PreguntaFrecuente;
import com.helpdesk.repository.PreguntaFrecuenteRepository;
import com.helpdesk.service.PreguntaFrecuenteService;

@Service
public class PreguntaFrecuenteServiceImpl implements PreguntaFrecuenteService {

    @Autowired
    private PreguntaFrecuenteRepository repository;

    @Override
    public List<PreguntaFrecuente> listar() {
        return repository.findAll();
    }

    @Override
    public PreguntaFrecuente guardar(PreguntaFrecuente pregunta) {

        if (pregunta.getIdPregunta() == null && pregunta.getFechaCreacion() == null) {
            pregunta.setFechaCreacion(LocalDateTime.now());
        }

        return repository.save(pregunta);
    }

    @Override
    public PreguntaFrecuente buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
