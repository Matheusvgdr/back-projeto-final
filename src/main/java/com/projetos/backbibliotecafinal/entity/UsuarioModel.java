package com.projetos.backbibliotecafinal.entity;


import com.projetos.backbibliotecafinal.constants.enums.TipoUsuarioEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb002_usuario")
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    @Size(max = 120)
    private String nome;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, length = 120)
    @Size(max = 120)
    private String email;

    @Column(nullable = false, length = 11)
    @Size(min = 11, max = 11)
    private String cpf;

    @Column(nullable = false)
    private TipoUsuarioEnum tipo;
}
