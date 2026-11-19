package com.linkrapi.estadisticas.servicio;

import com.linkrapi.estadisticas.dto.ClicRequest;
import com.linkrapi.estadisticas.dto.Conteo;
import com.linkrapi.estadisticas.dto.EstadisticaResponse;
import com.linkrapi.estadisticas.modelo.Clic;
import com.linkrapi.estadisticas.repositorio.ClicRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadisticaServicio {

    private static final int DIAS = 7;

    private final ClicRepositorio clicRepositorio;

    // Registrar clic
    @Transactional
    public void registrar(ClicRequest request) {
        Clic clic = new Clic();
        clic.setCodigo(request.codigo());
        clic.setNavegador(request.navegador() == null ? "Otro" : request.navegador());
        clicRepositorio.save(clic);
    }

    // Totales de todos los enlaces
    @Transactional(readOnly = true)
    public List<Conteo> totales() {
        return clicRepositorio.contarPorCodigo();
    }

    // Detalle de un enlace
    @Transactional(readOnly = true)
    public EstadisticaResponse detalle(String codigo) {
        return new EstadisticaResponse(
                codigo,
                clicRepositorio.countByCodigo(codigo),
                clicsPorDia(codigo),
                clicRepositorio.contarPorNavegador(codigo)
        );
    }

    // Últimos 7 días (incluye días en 0)
    private List<Conteo> clicsPorDia(String codigo) {
        LocalDate hoy = LocalDate.now();
        LocalDate inicio = hoy.minusDays(DIAS - 1);
        List<Clic> clics = clicRepositorio.findByCodigoAndFechaAfter(codigo, inicio.atStartOfDay());

        List<Conteo> resultado = new ArrayList<>();
        for (LocalDate dia = inicio; !dia.isAfter(hoy); dia = dia.plusDays(1)) {
            LocalDate actual = dia;
            long total = clics.stream().filter(c -> c.getFecha().toLocalDate().equals(actual)).count();
            resultado.add(new Conteo(actual.toString(), total));
        }
        return resultado;
    }
}
