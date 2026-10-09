package com.helpdesk.service;

import java.util.List;

import com.helpdesk.entity.PreguntaFrecuente;

public interface PreguntaFrecuenteService {

    List<PreguntaFrecuente> listar();

    PreguntaFrecuente guardar(PreguntaFrecuente pregunta);

    PreguntaFrecuente buscarPorId(Integer id);

    void eliminar(Integer id);
}
