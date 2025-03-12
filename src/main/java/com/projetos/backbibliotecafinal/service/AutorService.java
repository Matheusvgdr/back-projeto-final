package com.projetos.backbibliotecafinal.service;

import com.projetos.backbibliotecafinal.constants.messages.AutorMessage;
import com.projetos.backbibliotecafinal.dto.request.AutorRequest;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.dto.response.InsercaoExistenteResponse;
import com.projetos.backbibliotecafinal.entity.AutorModel;
import com.projetos.backbibliotecafinal.repository.AutorRepository;
import com.projetos.backbibliotecafinal.utils.mapper.AutorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    public ApiResponse<?> salvar(AutorRequest autorRequest) {
        autorRepository.save(autorMapper.toAutorModel(autorRequest));

        return new ApiResponse<>(null, AutorMessage.CADASTRO_SUCESSO, HttpStatus.CREATED.value());
    }

    public ApiResponse<List<AutorModel>> buscar() {
        return new ApiResponse<>(autorRepository.findAll(), "", HttpStatus.OK.value());
    }

    public AutorModel buscarPorId(Long id) {
        return autorRepository.findById(id).orElseThrow(() -> new RuntimeException(AutorMessage.ERRO_BUSCA));
    }

    public InsercaoExistenteResponse<AutorModel> buscarOuSalvar(AutorModel autorModel) {
        var resultado = new InsercaoExistenteResponse<AutorModel>();
        var autor = autorRepository.findByNome((autorModel.getNome()));

        if (autor.isPresent()) {
            resultado.setDado(autor.get());
            resultado.setExiste(true);
        } else {
            resultado.setExiste(false);
            resultado.setDado(autorRepository.save(autorModel));
        }
        return resultado;

        //return autorRepository.findByNome((autorModel.getNome())).orElseGet(() -> autorRepository.save(autorModel));
    }

}
