package com.projetos.backbibliotecafinal.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record LivroClientResponse(
        @JsonProperty("name")
        String autor,
        @JsonProperty("publisher")
        String editora,
        @JsonProperty("book")
        String nomeLivro,
        @JsonProperty("price")
        BigDecimal precoLivro,
        String isbn
) {
}