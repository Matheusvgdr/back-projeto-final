package com.projetos.backbibliotecafinal.dto.request.livro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record LivroRequest(
        @NotBlank(message = "O campo título é obrigatório")  String titulo,
        @NotBlank(message = "O campo isbn é obrigatório") String isbn,
        @NotNull Long idAutor,
        @NotNull Long idEditora,
        @NotNull Long idBiblioteca,
        @NotNull(message = "O campo preço é obrigatório") BigDecimal preco

) {
}
