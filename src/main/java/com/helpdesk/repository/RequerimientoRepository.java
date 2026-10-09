package com.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helpdesk.entity.Requerimiento;
import com.helpdesk.entity.Usuario;

@Repository
public interface RequerimientoRepository extends JpaRepository<Requerimiento, Integer> {

    List<Requerimiento> findByUsuario(Usuario usuario);

    List<Requerimiento> findByCategoriaIdCategoriaIn(List<Integer> idsCategorias);

    List<Requerimiento> findByTituloContaining(String titulo);
}
