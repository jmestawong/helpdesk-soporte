package com.helpdesk.service;

import java.util.List;

import com.helpdesk.entity.Usuario;

public interface UsuarioService {

    List<Usuario> listar();

    Usuario guardar(Usuario usuario);

    Usuario buscarPorId(Integer id);

    Usuario buscarPorCorreo(String correo);

    void eliminar(Integer id);
}
