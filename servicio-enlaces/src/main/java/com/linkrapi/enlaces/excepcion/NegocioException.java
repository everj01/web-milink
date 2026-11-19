package com.linkrapi.enlaces.excepcion;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// Error de negocio con su código HTTP
@Getter
public class NegocioException extends RuntimeException {

    private final HttpStatus estado;

    public NegocioException(HttpStatus estado, String mensaje) {
        super(mensaje);
        this.estado = estado;
    }
}
