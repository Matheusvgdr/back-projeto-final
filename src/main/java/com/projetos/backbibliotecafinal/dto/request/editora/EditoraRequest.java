package com.projetos.backbibliotecafinal.dto.request.editora;

import jakarta.validation.constraints.NotBlank;

public record EditoraRequest(
        @NotBlank(message = "O campo nome é obrigatório") String nome
) {
}
