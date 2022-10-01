package br.edu.utfpr.testes.categoria;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "categoria")
@Table(name = "categoria")
public class Categoria{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    private String descricao;

    private String data_inclusao;
}
