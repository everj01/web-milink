package com.linkrapi.enlaces.servicio;

import com.linkrapi.enlaces.cliente.EstadisticaCliente;
import com.linkrapi.enlaces.dto.ClicRequest;
import com.linkrapi.enlaces.dto.EnlaceRequest;
import com.linkrapi.enlaces.dto.EnlaceResponse;
import com.linkrapi.enlaces.excepcion.NegocioException;
import com.linkrapi.enlaces.modelo.Enlace;
import com.linkrapi.enlaces.repositorio.EnlaceRepositorio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnlaceServicio {

    private static final String CARACTERES = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private final EnlaceRepositorio enlaceRepositorio;
    private final EstadisticaCliente estadisticaCliente;

    // Crear enlace corto
    @Transactional
    public EnlaceResponse crear(EnlaceRequest request) {
        String codigo = tieneAlias(request) ? request.alias().trim() : generarCodigoUnico();

        if (enlaceRepositorio.existsByCodigo(codigo)) {
            throw new NegocioException(HttpStatus.CONFLICT, "Ese alias ya está en uso");
        }

        Enlace enlace = new Enlace();
        enlace.setCodigo(codigo);
        enlace.setUrlOriginal(request.urlOriginal().trim());

        return EnlaceResponse.desde(enlaceRepositorio.save(enlace));
    }

    // Listar enlaces
    @Transactional(readOnly = true)
    public List<EnlaceResponse> listar() {
        return enlaceRepositorio.findAllByOrderByFechaCreacionDesc()
                .stream().map(EnlaceResponse::desde).toList();
    }

    // Obtener URL y registrar clic
    @Transactional(readOnly = true)
    public String resolver(String codigo, String userAgent) {
        Enlace enlace = enlaceRepositorio.findByCodigo(codigo)
                .orElseThrow(() -> new NegocioException(HttpStatus.NOT_FOUND, "Enlace no encontrado"));

        try {
            estadisticaCliente.registrarClic(new ClicRequest(codigo, detectarNavegador(userAgent)));
        } catch (Exception e) {
            // Si falla, igual redirige
            log.warn("No se pudo registrar el clic de {}", codigo);
        }

        return enlace.getUrlOriginal();
    }

    private boolean tieneAlias(EnlaceRequest request) {
        return request.alias() != null && !request.alias().isBlank();
    }

    private String generarCodigoUnico() {
        String codigo;
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                sb.append(CARACTERES.charAt(RANDOM.nextInt(CARACTERES.length())));
            }
            codigo = sb.toString();
        } while (enlaceRepositorio.existsByCodigo(codigo));
        return codigo;
    }

    private String detectarNavegador(String userAgent) {
        if (userAgent == null) return "Otro";
        if (userAgent.contains("Edg")) return "Edge";
        if (userAgent.contains("OPR")) return "Opera";
        if (userAgent.contains("Chrome")) return "Chrome";
        if (userAgent.contains("Firefox")) return "Firefox";
        if (userAgent.contains("Safari")) return "Safari";
        return "Otro";
    }
}
