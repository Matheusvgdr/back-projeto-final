package com.projetos.backbibliotecafinal.utils.mapper;

import com.projetos.backbibliotecafinal.dto.request.EmprestimoRequest;
import com.projetos.backbibliotecafinal.entity.EmprestimoModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmprestimoMapper {

    EmprestimoModel toEmprestimoModel(EmprestimoRequest emprestimoRequest);
}
