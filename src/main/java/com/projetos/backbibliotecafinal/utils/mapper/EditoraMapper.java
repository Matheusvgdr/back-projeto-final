package com.projetos.backbibliotecafinal.utils.mapper;

import com.projetos.backbibliotecafinal.dto.request.EditoraRequest;
import com.projetos.backbibliotecafinal.entity.EditoraModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EditoraMapper {
    EditoraModel toEditoraModel(EditoraRequest editoraRequest);
}
