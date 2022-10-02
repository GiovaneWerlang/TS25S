package br.edu.utfpr.testes.categoria;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Path("/categoria")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    private CategoriaRepository repository;

    private Validator validator;

    @Inject
    CategoriaResource(CategoriaRepository repository, Validator validator){

        this.repository = repository;
        this.validator = validator;
    }

    @GET
    public Response getAll(){
        List<Categoria> lista = repository.listAll();
        if(lista.isEmpty()){

            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response getByIdCategoria(@PathParam("id") Long id) {

        Categoria categoria = repository.findById(id);
        if(categoria != null){
            return Response.ok(categoria).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Transactional
    public Response addCategoria(CategoriaDTO categoriaDTO){

        Set<ConstraintViolation<CategoriaDTO>> violations = validator.validate(categoriaDTO);
        if(!violations.isEmpty()){
            return br.edu.utfpr.testes.erro.ResponseError.createFromViolations(violations).returnWithStatusCode(422);
        }

        Categoria categoria = new Categoria();
        categoria.setDescricao(categoriaDTO.getDescricao());
        categoria.setData_inclusao(categoriaDTO.getData_inclusao());

        try {
            repository.persist(categoria);
        }catch (Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status( Response.Status.CREATED.getStatusCode(),categoria.toString()).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response atualizaCategoria(@PathParam("id") Long id, CategoriaDTO categoriaDTO){

        Set<ConstraintViolation<CategoriaDTO>> violations = validator.validate(categoriaDTO);
        if(!violations.isEmpty()){
            return br.edu.utfpr.testes.erro.ResponseError.createFromViolations(violations).returnWithStatusCode(422);
        }
        Categoria categoria = repository.findById(id);
        if(categoria != null){
            categoria.setDescricao(categoriaDTO.getDescricao());
            categoria.setData_inclusao(categoriaDTO.getData_inclusao());
            return Response.ok(categoria).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteCategoria(@PathParam("id") Long id){
        Categoria categoria = repository.findById(id);
        if(categoria != null){
            repository.delete(categoria);
            return Response.ok(categoria).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
