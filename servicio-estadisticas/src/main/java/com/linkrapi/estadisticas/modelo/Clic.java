package com.linkrapi.estadisticas.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "clics", indexes = @Index(name = "idx_clics_codigo", columnList = "codigo"))
@Getter
@Setter
@NoArgsConstructor
public class Clic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String codigo;

    @Column(length = 20)
    private String navegador;

    private LocalDateTime fecha;

    @PrePersist
    void alCrear() {
        fecha = LocalDateTime.now();
    }
}
