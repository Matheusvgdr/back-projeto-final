package com.projetos.backbibliotecafinal.dto.request.autor;

import jakarta.validation.constraints.NotBlank;

public record AutorRequest(
        @NotBlank(message = "O campo nome é obrigatório") String nome
) {
}
