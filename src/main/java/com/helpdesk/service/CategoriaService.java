package com.helpdesk.service;

import java.util.List;

import com.helpdesk.entity.Categoria;

public interface CategoriaService {

    List<Categoria> listar();

    Categoria guardar(Categoria categoria);

    Categoria buscarPorId(Integer id);

    void eliminar(Integer id);
}
