package com.linkrapi.enlaces.dto;

// Datos que se envían a servicio-estadisticas
public record ClicRequest(
        String codigo,
        String navegador
) {
}
