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
@Entity
@Table(name = "tb005_livro")
public class LivroModel {

    public LivroModel(String titulo, AutorModel autor, EditoraModel editora, BigDecimal preco, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.preco = preco;
        this.isbn = isbn;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 90)
    public String titulo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    public AutorModel autor;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    public EditoraModel editora;

    @Column(nullable = false)
    public BigDecimal preco;

    @Column(nullable = false)
    public String isbn;

    private LocalDate dataExclusao;

    @ManyToOne
    private BibliotecaModel biblioteca;

    @ManyToMany(mappedBy = "livros")
    private List<EmprestimoModel> emprestimos;

}
