package com.linkrapi.estadisticas.dto;

import java.util.List;

public record EstadisticaResponse(
        String codigo,
        long total,
        List<Conteo> porDia,
        List<Conteo> porNavegador
) {
}
