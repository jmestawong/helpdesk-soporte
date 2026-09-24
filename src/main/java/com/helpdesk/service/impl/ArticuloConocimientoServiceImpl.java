package com.helpdesk.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpdesk.entity.ArticuloConocimiento;
import com.helpdesk.repository.ArticuloConocimientoRepository;
import com.helpdesk.service.ArticuloConocimientoService;

@Service
public class ArticuloConocimientoServiceImpl implements ArticuloConocimientoService {

    @Autowired
    private ArticuloConocimientoRepository repository;

    @Override
    public List<ArticuloConocimiento> listar() {
        return repository.findAll();
    }

    @Override
    public ArticuloConocimiento guardar(ArticuloConocimiento articulo) {

        if (articulo.getIdArticulo() == null) {
            articulo.setFechaCreacion(LocalDateTime.now());
        } else {
            ArticuloConocimiento existente = repository.findById(articulo.getIdArticulo()).orElse(null);
            if (existente != null) {
                articulo.setFechaCreacion(existente.getFechaCreacion());
            }
        }

        articulo.setFechaActualizacion(LocalDateTime.now());

        return repository.save(articulo);
    }

    @Override
    public ArticuloConocimiento buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<ArticuloConocimiento> buscarPorTexto(String texto) {
        return repository.findByTituloContainingIgnoreCaseOrContenidoContainingIgnoreCase(texto, texto);
    }
}
