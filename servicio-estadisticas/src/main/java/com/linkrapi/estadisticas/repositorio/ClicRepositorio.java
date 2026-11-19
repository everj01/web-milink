package com.linkrapi.estadisticas.repositorio;

import com.linkrapi.estadisticas.dto.Conteo;
import com.linkrapi.estadisticas.modelo.Clic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ClicRepositorio extends JpaRepository<Clic, Long> {

    long countByCodigo(String codigo);

    List<Clic> findByCodigoAndFechaAfter(String codigo, LocalDateTime desde);

    // Total por enlace
    @Query("SELECT new com.linkrapi.estadisticas.dto.Conteo(c.codigo, COUNT(c)) FROM Clic c GROUP BY c.codigo")
    List<Conteo> contarPorCodigo();

    // Total por navegador
    @Query("SELECT new com.linkrapi.estadisticas.dto.Conteo(c.navegador, COUNT(c)) FROM Clic c " +
           "WHERE c.codigo = :codigo GROUP BY c.navegador ORDER BY COUNT(c) DESC")
    List<Conteo> contarPorNavegador(@Param("codigo") String codigo);
}
