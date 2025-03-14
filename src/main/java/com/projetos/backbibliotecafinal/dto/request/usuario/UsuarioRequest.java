package com.projetos.backbibliotecafinal.dto.request.usuario;

import com.projetos.backbibliotecafinal.constants.enums.TipoUsuarioEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
        @NotBlank(message = "O campo nome é obrigatório")
        String nome,
        @NotBlank(message = "O campo email é obrigatório")
        @Email(message = "O email deve estar em um formato válido")
        String email,
        @NotBlank(message = "O campo senha é obrigatório")
        @Size(min = 8, max = 16)
        String senha,
        @NotBlank(message = "O campo CPF é obrigatório")
        @Size(min = 11, max = 11, message = "O CPF deve conter 11 dígitos")
        String cpf,
        @NotNull(message = "O campo tipo usuário é obrigatório")
        TipoUsuarioEnum tipo

) {
}
