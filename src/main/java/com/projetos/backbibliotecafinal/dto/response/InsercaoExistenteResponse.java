package com.projetos.backbibliotecafinal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsercaoExistenteResponse<T> {
    private T dado;
    private boolean existe;
}
