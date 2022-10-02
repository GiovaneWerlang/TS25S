package br.edu.utfpr.testes.produto;

import br.edu.utfpr.testes.categoria.CategoriaRepository;
import br.edu.utfpr.testes.erro.ResponseError;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Path("/produto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    private ProdutoRepository repository;

    private CategoriaRepository fkRepository;

    private Validator validator;

    @Inject
    public ProdutoResource(ProdutoRepository repository, CategoriaRepository fkRepository){
        this.repository = repository;
        this.fkRepository = fkRepository;
    }

    @GET
    public Response getAll() {
        List<Produto> lista = repository.listAll();
        if(lista.isEmpty()){

            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(lista).build();

    }

    @GET
    @Path("{id}")
    public Response getByIdProduto(@PathParam("id") Long id) {
        Produto produto = repository.findById(id);
        if(produto != null){
            return Response.ok(produto).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Transactional
    public Response addProduto(ProdutoDTO produtoDTO){

        Set<ConstraintViolation<ProdutoDTO>> violations = validator.validate(produtoDTO);
        if(!violations.isEmpty()){
            return ResponseError.createFromViolations(violations).returnWithStatusCode(422);
        }

        Produto produto = new Produto();
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setMarca(produtoDTO.getMarca());
        produto.setValor(produtoDTO.getValor());

        if(fkRepository.findById(produtoDTO.getCategoria().getId()) == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        produto.setCategoria(fkRepository.findById(produtoDTO.getCategoria().getId()));
        repository.persist(produto);

        return Response.status( Response.Status.CREATED.getStatusCode(),produto.toString()).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizaProduto(@PathParam("id") Long id, ProdutoDTO produtoDTO){

        Set<ConstraintViolation<ProdutoDTO>> violations = validator.validate(produtoDTO);
        if(!violations.isEmpty()){
            return ResponseError.createFromViolations(violations).returnWithStatusCode(422);
        }

        Produto produto = repository.findById(id);
        if(produto != null){

            produto.setDescricao(produto.getDescricao());
            produto.setMarca(produto.getMarca());
            produto.setValor(produto.getValor());

            if(fkRepository.findById(produtoDTO.getCategoria().getId()) == null){
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            produto.setCategoria(fkRepository.findById(produtoDTO.getCategoria().getId()));

            return Response.ok(produto).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteProduto(@PathParam("id") Long id){
        Produto produto = repository.findById(id);
        if(produto != null){
            repository.delete(produto);
            return Response.ok(produto).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
