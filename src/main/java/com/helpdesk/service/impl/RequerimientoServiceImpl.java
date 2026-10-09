package com.helpdesk.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpdesk.entity.Requerimiento;
import com.helpdesk.entity.Usuario;
import com.helpdesk.repository.RequerimientoRepository;
import com.helpdesk.service.RequerimientoService;

@Service
public class RequerimientoServiceImpl implements RequerimientoService {

    @Autowired
    private RequerimientoRepository repository;

    @Override
    public List<Requerimiento> listar() {
        return repository.findAll();
    }

    @Override
    public Requerimiento guardar(Requerimiento requerimiento) {

        if (requerimiento.getIdRequerimiento() == null
                && requerimiento.getFechaRegistro() == null) {
            requerimiento.setFechaRegistro(LocalDateTime.now());
        }

        return repository.save(requerimiento);
    }

    @Override
    public Requerimiento buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Requerimiento> listarPorUsuario(Usuario usuario) {
        return repository.findByUsuario(usuario);
    }

    @Override
    public List<Requerimiento> listarPorCategorias(List<Integer> idsCategorias) {
        return repository.findByCategoriaIdCategoriaIn(idsCategorias);
    }

    @Override
    public List<Requerimiento> buscarPorTitulo(String titulo) {
        return repository.findByTituloContaining(titulo);
    }
}
