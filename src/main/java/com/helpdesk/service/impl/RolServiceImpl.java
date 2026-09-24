package com.helpdesk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpdesk.entity.Rol;
import com.helpdesk.repository.RolRepository;
import com.helpdesk.service.RolService;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository repository;

    @Override
    public List<Rol> listar() {
        return repository.findAll();
    }

    @Override
    public Rol guardar(Rol rol) {
        return repository.save(rol);
    }

    @Override
    public Rol buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
