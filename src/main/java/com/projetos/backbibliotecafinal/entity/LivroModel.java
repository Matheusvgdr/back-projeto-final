package com.projetos.backbibliotecafinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb005_livro")
public class LivroModel {

    public LivroModel(String titulo, AutorModel autor, EditoraModel editora, BigDecimal preco, String isbn, BibliotecaModel biblioteca) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.preco = preco;
        this.isbn = isbn;
        this.biblioteca = biblioteca;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 90)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private AutorModel autor;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private EditoraModel editora;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private String isbn;

    private LocalDate dataExclusao;

    @ManyToOne
    private BibliotecaModel biblioteca;

    @ManyToMany(mappedBy = "livros")
    private List<TransacaoModel> transacao;

}
