package com.linkrapi.enlaces.controlador;

import com.linkrapi.enlaces.dto.EnlaceRequest;
import com.linkrapi.enlaces.dto.EnlaceResponse;
import com.linkrapi.enlaces.servicio.EnlaceServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EnlaceControlador {

    private final EnlaceServicio enlaceServicio;

    @PostMapping("/api/enlaces")
    public ResponseEntity<EnlaceResponse> crear(@Valid @RequestBody EnlaceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enlaceServicio.crear(request));
    }

    @GetMapping("/api/enlaces")
    public List<EnlaceResponse> listar() {
        return enlaceServicio.listar();
    }

    // Redirección
    @GetMapping("/r/{codigo}")
    public ResponseEntity<Void> redirigir(@PathVariable String codigo,
                                          @RequestHeader(value = HttpHeaders.USER_AGENT, required = false) String userAgent) {
        String url = enlaceServicio.resolver(codigo, userAgent);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }
}
