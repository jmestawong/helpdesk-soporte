package com.helpdesk.service;

import java.util.List;

import com.helpdesk.entity.Requerimiento;
import com.helpdesk.entity.Usuario;

public interface RequerimientoService {

    List<Requerimiento> listar();

    Requerimiento guardar(Requerimiento requerimiento);

    Requerimiento buscarPorId(Integer id);

    void eliminar(Integer id);

    List<Requerimiento> listarPorUsuario(Usuario usuario);

    List<Requerimiento> listarPorCategorias(List<Integer> idsCategorias);

    List<Requerimiento> buscarPorTitulo(String titulo);
}
