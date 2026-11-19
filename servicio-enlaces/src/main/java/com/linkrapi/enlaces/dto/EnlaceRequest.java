package com.linkrapi.enlaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;

public record EnlaceRequest(
        @NotBlank(message = "La URL es obligatoria")
        @URL(message = "La URL no es válida")
        String urlOriginal,

        @Pattern(regexp = "^[a-zA-Z0-9-]{3,20}$",
                 message = "El alias debe tener de 3 a 20 letras, números o guiones")
        String alias
) {
}
