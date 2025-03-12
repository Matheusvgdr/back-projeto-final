package com.projetos.backbibliotecafinal.utils.mapper;

import com.projetos.backbibliotecafinal.dto.request.BibliotecaRequest;
import com.projetos.backbibliotecafinal.entity.BibliotecaModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BibliotecaMapper {
    BibliotecaModel toBibliotecaModel(BibliotecaRequest bibliotecaRequest);
}
