package com.projetos.backbibliotecafinal.utils.mapper;

import com.projetos.backbibliotecafinal.dto.request.autor.AutorRequest;
import com.projetos.backbibliotecafinal.dto.response.AutorResponse;
import com.projetos.backbibliotecafinal.entity.AutorModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AutorMapper {
    AutorModel toAutorModel(AutorRequest autorRequest);
    List<AutorResponse> toAutorResponseList(List<AutorModel> autorModel);
}
