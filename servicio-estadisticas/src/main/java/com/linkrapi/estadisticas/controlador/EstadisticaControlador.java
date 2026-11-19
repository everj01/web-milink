package com.linkrapi.estadisticas.controlador;

import com.linkrapi.estadisticas.dto.ClicRequest;
import com.linkrapi.estadisticas.dto.Conteo;
import com.linkrapi.estadisticas.dto.EstadisticaResponse;
import com.linkrapi.estadisticas.servicio.EstadisticaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
@RequiredArgsConstructor
public class EstadisticaControlador {

    private final EstadisticaServicio estadisticaServicio;

    // Uso interno: servicio-enlaces
    @PostMapping("/clics")
    @ResponseStatus(HttpStatus.CREATED)
    public void registrar(@Valid @RequestBody ClicRequest request) {
        estadisticaServicio.registrar(request);
    }

    @GetMapping("/totales")
    public List<Conteo> totales() {
        return estadisticaServicio.totales();
    }

    @GetMapping("/{codigo}")
    public EstadisticaResponse detalle(@PathVariable String codigo) {
        return estadisticaServicio.detalle(codigo);
    }
}
