package com.linkrapi.enlaces.cliente;

import com.linkrapi.enlaces.dto.ClicRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Cliente de servicio-estadisticas
@FeignClient(name = "servicio-estadisticas")
public interface EstadisticaCliente {

    @PostMapping("/api/estadisticas/clics")
    void registrarClic(@RequestBody ClicRequest clic);
}
