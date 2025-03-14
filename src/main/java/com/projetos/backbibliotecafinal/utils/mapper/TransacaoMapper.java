package com.projetos.backbibliotecafinal.utils.mapper;

import com.projetos.backbibliotecafinal.dto.response.TransacaoResponse;
import com.projetos.backbibliotecafinal.entity.TransacaoModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransacaoMapper {

    TransacaoResponse toTransacaoResponse(TransacaoModel transacaoModel);
}
