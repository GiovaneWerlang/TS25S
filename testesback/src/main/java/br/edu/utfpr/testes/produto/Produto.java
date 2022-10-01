package br.edu.utfpr.testes.produto;

import br.edu.utfpr.testes.categoria.Categoria;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "produto")
@Table(name = "produto")
public class Produto{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String descricao;

    private String marca;

    private double valor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

}
