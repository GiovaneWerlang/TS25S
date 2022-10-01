package br.edu.utfpr.testes.produto;

import br.edu.utfpr.testes.categoria.Categoria;
import lombok.Data;

@Data
public class ProdutoDTO {

    private String descricao;

    private String marca;

    private double valor;

    private Categoria categoria;
}
