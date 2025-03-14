package com.projetos.backbibliotecafinal.utils.mapper;

import com.projetos.backbibliotecafinal.dto.request.biblioteca.BibliotecaRequest;
import com.projetos.backbibliotecafinal.dto.response.BibliotecaResponse;
import com.projetos.backbibliotecafinal.entity.BibliotecaModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BibliotecaMapper {
    BibliotecaModel toBibliotecaModel(BibliotecaRequest bibliotecaRequest);
    List<BibliotecaResponse> toBibliotecaResponseList(List<BibliotecaModel> bibliotecaModel);
}
