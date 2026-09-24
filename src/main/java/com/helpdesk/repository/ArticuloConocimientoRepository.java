package com.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helpdesk.entity.ArticuloConocimiento;
import com.helpdesk.entity.Categoria;

@Repository
public interface ArticuloConocimientoRepository extends JpaRepository<ArticuloConocimiento, Integer> {

    List<ArticuloConocimiento> findByTituloContainingIgnoreCaseOrContenidoContainingIgnoreCase(
            String titulo, String contenido);

    List<ArticuloConocimiento> findByCategoria(Categoria categoria);
}
