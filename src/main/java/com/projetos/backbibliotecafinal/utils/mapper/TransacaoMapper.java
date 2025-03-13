package com.projetos.backbibliotecafinal.utils.mapper;

import com.projetos.backbibliotecafinal.dto.request.TransacaoRequest;
import com.projetos.backbibliotecafinal.dto.response.TransacaoResponse;
import com.projetos.backbibliotecafinal.entity.TransacaoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransacaoMapper {

    TransacaoResponse toTransacaoResponse(TransacaoModel transacaoModel);
}
