package com.projetos.backbibliotecafinal.entity;

import com.projetos.backbibliotecafinal.constants.enums.StatusEmprestimoEnum;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb005_emprestimo")
public class EmprestimoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;

    private LocalDate dataEmprestimo;

    private LocalDate dataDevolucao;

    @ManyToMany()
    @JoinTable(
            name = "tb006_emprestimo_livro",
            joinColumns = @JoinColumn(name = "emprestimo_id"),
            inverseJoinColumns = @JoinColumn(name = "livro_id"))
    private List<LivroModel> livros;

    @ManyToOne
    private UsuarioModel usuario;

    private StatusEmprestimoEnum status;

}
