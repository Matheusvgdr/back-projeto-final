package com.projetos.backbibliotecafinal.utils.mapper;

import com.projetos.backbibliotecafinal.dto.request.livro.LivroRequest;
import com.projetos.backbibliotecafinal.dto.response.LivroResponse;
import com.projetos.backbibliotecafinal.entity.LivroModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LivroMapper {

    LivroModel toLivroModel(LivroRequest livroRequest);

    @Mapping(source = "autor.nome", target = "nomeAutor")
    @Mapping(source = "editora.nome", target = "nomeEditora")
    @Mapping(source = "biblioteca.nome", target = "nomeBiblioteca")
    LivroResponse toLivroResponse(LivroModel livroModel);

    @Mapping(source = "autor.nome", target = "nomeAutor")
    @Mapping(source = "editora.nome", target = "nomeEditora")
    @Mapping(source = "biblioteca.nome", target = "nomeBiblioteca")
    List<LivroResponse> toListLivroResponse(List<LivroModel> livrosModel);
}
