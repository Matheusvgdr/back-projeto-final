package com.projetos.backbibliotecafinal.dto.response;

public record LivroResponse(
        Long id,
        String titulo,
        String nomeAutor,
        String nomeEditora,
        String preco,
        String isbn,
        String nomeBiblioteca
) {
}
