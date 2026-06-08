package com.ciadainformatica.vendas.service;

import com.ciadainformatica.vendas.dao.ProdutoDAO;
import com.ciadainformatica.vendas.domain.Produto;
import com.ciadainformatica.vendas.dto.ProductRequestDTO;
import com.ciadainformatica.vendas.dto.ProductResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoService {

    private ProdutoDAO produtoDAO;

    public ProdutoService(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public ProdutoService() {
        this.produtoDAO = new ProdutoDAO();
    }

    public Produto cadastrarProduto(ProductRequestDTO dto) {
        Produto produto = new Produto();
        produto.setDescricao(dto.getNome());
        produto.setCodBarras(dto.getCodigoBarras());
        produto.setPreco(dto.getPreco());
        produto.setEstoqueAtual(dto.getEstoqueAtual());
        produto.setEstoqueMinimo(dto.getEstoqueMinimo());
        produto.setQuantidade(dto.getEstoqueAtual().shortValue());

        produtoDAO.salvar(produto);
        return produto;
    }

    public List<ProductResponseDTO> listarProdutosAtivos(){
        List<Produto> todos = produtoDAO.listar();

        return todos.stream()
                .filter(Produto::getAtivo)
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO atualizarProduto(Long codigo, ProductRequestDTO dto){
        Produto produtoExistente = produtoDAO.buscar(codigo);

        if (produtoExistente == null || !produtoExistente.getAtivo()){
            throw new RuntimeException("Alvo inválido: Produto não existe ou está desativado!");
        }

        produtoExistente.setDescricao(dto.getNome());
        produtoExistente.setPreco(dto.getPreco());
        produtoExistente.setEstoqueAtual(dto.getEstoqueAtual());
        produtoExistente.setCodBarras(dto.getCodigoBarras());
        produtoExistente.setEstoqueMinimo(dto.getEstoqueMinimo());
        produtoExistente.setQuantidade(dto.getEstoqueAtual().shortValue());

        produtoDAO.editar(produtoExistente);

        return new ProductResponseDTO(produtoExistente);
    }

    public void desativarProduto(Long codigoProduto) {
        Produto produto = produtoDAO.buscar(codigoProduto);
        if (produto == null) {
            throw new RuntimeException("Alvo não encontrado: Produto não existe!");
        }
        produto.setAtivo(false);
        produtoDAO.editar(produto);
    }
}
