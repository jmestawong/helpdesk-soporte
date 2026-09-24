package com.helpdesk.service;

import java.util.List;

import com.helpdesk.entity.ArticuloConocimiento;

public interface ArticuloConocimientoService {

    List<ArticuloConocimiento> listar();

    ArticuloConocimiento guardar(ArticuloConocimiento articulo);

    ArticuloConocimiento buscarPorId(Integer id);

    void eliminar(Integer id);

    List<ArticuloConocimiento> buscarPorTexto(String texto);
}
