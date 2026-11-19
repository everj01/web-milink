package com.linkrapi.estadisticas.dto;

import jakarta.validation.constraints.NotBlank;

public record ClicRequest(
        @NotBlank String codigo,
        String navegador
) {
}
