package br.edu.utfpr.testes.categoria;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoriaDTO {

    @NotBlank(message = "Não pode ser vazio")
    private String descricao;

    @NotBlank(message = "Não pode ser vazio")
    private String data_inclusao;

}
