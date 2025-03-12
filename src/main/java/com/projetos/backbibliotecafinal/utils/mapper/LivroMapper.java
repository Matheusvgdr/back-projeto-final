package com.projetos.backbibliotecafinal.utils.mapper;

import com.projetos.backbibliotecafinal.dto.request.LivroRequest;
import com.projetos.backbibliotecafinal.entity.LivroModel;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LivroMapper {
    LivroModel toLivroModel(LivroRequest livroRequest);
}
