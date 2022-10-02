package br.edu.utfpr.testes.produto;

import br.edu.utfpr.testes.categoria.Categoria;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProdutoDTO {

    @NotNull(message = "Não pode ser nulo")
    private String descricao;

    @NotNull(message = "Não pode ser nulo")
    private String marca;

    @NotNull(message = "Não pode ser nulo")
    private double valor;

    @NotNull(message = "Não pode ser nulo")
    private Categoria categoria;
}
