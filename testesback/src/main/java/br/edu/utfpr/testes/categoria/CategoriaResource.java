package br.edu.utfpr.testes.categoria;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/categoria")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    private CategoriaRepository repository;

    @Inject
    CategoriaResource(CategoriaRepository repository){
        this.repository = repository;
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
