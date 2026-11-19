package com.linkrapi.enlaces.repositorio;

import com.linkrapi.enlaces.modelo.Enlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnlaceRepositorio extends JpaRepository<Enlace, Long> {

    Optional<Enlace> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Enlace> findAllByOrderByFechaCreacionDesc();
}
