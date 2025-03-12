package com.projetos.backbibliotecafinal.client;

import com.projetos.backbibliotecafinal.dto.response.LivroClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        value = "autorClient",
        url = "http://localhost:3000"
)
public interface ImportClient {

    @GetMapping(value = "/books")
    List<LivroClientResponse> importarAutores();

}

