package com.linkrapi.enlaces.dto;

import com.linkrapi.enlaces.modelo.Enlace;

import java.time.LocalDateTime;

public record EnlaceResponse(
        String codigo,
        String urlOriginal,
        LocalDateTime fechaCreacion
) {

    // Entidad a DTO
    public static EnlaceResponse desde(Enlace e) {
        return new EnlaceResponse(e.getCodigo(), e.getUrlOriginal(), e.getFechaCreacion());
    }
}
