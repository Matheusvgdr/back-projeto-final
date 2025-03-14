package com.projetos.backbibliotecafinal.entity;

import com.projetos.backbibliotecafinal.constants.enums.StatusEmprestimoEnum;
import com.projetos.backbibliotecafinal.constants.enums.TipoTransacaoEnum;
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
@Table(name = "tb005_transacao")
public class TransacaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private BigDecimal valor;

    @Column()
    private LocalDate dataTransacao;

    @Column()
    private LocalDate dataDevolucao;

    @ManyToMany()
    @JoinTable(name = "tb006_transacao_livro", joinColumns = @JoinColumn(name = "transacao_id"), inverseJoinColumns = @JoinColumn(name = "livro_id"))
    private List<LivroModel> livros;

    @ManyToOne
    private UsuarioModel usuario;

    private BigDecimal multa;

    @Column()
    private StatusEmprestimoEnum status;

    @Column()
    private TipoTransacaoEnum tipo;

}
