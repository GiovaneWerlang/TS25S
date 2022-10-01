package br.edu.utfpr.testes.produto;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoRepository  implements PanacheRepository<Produto> {
}
