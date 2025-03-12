package com.projetos.backbibliotecafinal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BibliotecaRequest(
        @NotBlank(message = "O campo nome é obrigatório") String nome,
        @NotBlank(message = "O campo endereço é obrigatório") String endereco,
        @NotNull Long idUsuario
) {
}
