package com.helpdesk.service;

import java.util.List;

import com.helpdesk.entity.Rol;

public interface RolService {

    List<Rol> listar();

    Rol guardar(Rol rol);

    Rol buscarPorId(Integer id);

    void eliminar(Integer id);
}