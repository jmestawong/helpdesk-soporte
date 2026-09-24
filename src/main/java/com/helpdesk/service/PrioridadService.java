package com.helpdesk.service;

import java.util.List;

import com.helpdesk.entity.Prioridad;

public interface PrioridadService {

    List<Prioridad> listar();

    Prioridad guardar(Prioridad prioridad);

    Prioridad buscarPorId(Integer id);

    void eliminar(Integer id);
}
