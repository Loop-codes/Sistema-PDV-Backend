package com.ciadainformatica.vendas.api;

import com.ciadainformatica.vendas.dto.ProductRequestDTO;
import com.ciadainformatica.vendas.dto.ProductResponseDTO;
import com.ciadainformatica.vendas.service.ProdutoService;
import com.ciadainformatica.vendas.domain.Produto;
import jakarta.validation.Valid;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {

    private ProdutoService produtoService = new ProdutoService();

    @GET
    public Response listarTodos() {
        try {
            List<ProductResponseDTO> lista = produtoService.listarProdutosAtivos();

            return Response.status(Response.Status.OK).entity(lista).build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro na busca: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{codigo}")
    public Response editarProduto(@PathParam("codigo") Long codigo, @Valid ProductRequestDTO dto){
        try {
            ProductResponseDTO produtoAtualizado = produtoService.atualizarProduto(codigo, dto);

            return Response.status(Response.Status.OK).entity(produtoAtualizado).build();
        } catch (RuntimeException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro na atualizacao: " + e.getMessage()).build();
        }
    }

    @POST
    public Response criarProduto(@Valid ProductRequestDTO dto) {

        try {
            Produto produtoSalvo = produtoService.cadastrarProduto(dto);
            return Response.status(Response.Status.CREATED).entity(produtoSalvo).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro no QG: " + e.getMessage()).build();
        }
    }
}