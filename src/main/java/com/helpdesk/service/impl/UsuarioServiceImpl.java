package com.helpdesk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpdesk.entity.Usuario;
import com.helpdesk.repository.UsuarioRepository;
import com.helpdesk.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public List<Usuario> listar() {
        return repository.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        return repository.findByCorreo(correo).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
