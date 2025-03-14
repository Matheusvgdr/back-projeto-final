package com.projetos.backbibliotecafinal.dto.request.biblioteca;

import jakarta.validation.constraints.NotNull;

public record BibliotecaUpdateRequest(
        String nome,
        String endereco,
        @NotNull Long idUsuario
) {
}
