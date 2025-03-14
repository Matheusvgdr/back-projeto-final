package com.projetos.backbibliotecafinal.utils.mapper;

import com.projetos.backbibliotecafinal.dto.request.editora.EditoraRequest;
import com.projetos.backbibliotecafinal.dto.response.EditoraResponse;
import com.projetos.backbibliotecafinal.entity.EditoraModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EditoraMapper {
    EditoraModel toEditoraModel(EditoraRequest editoraRequest);
    List<EditoraResponse> toEditoraResponseList(List<EditoraModel> editoraModelList);
}
