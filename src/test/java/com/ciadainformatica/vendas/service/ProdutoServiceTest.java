package com.ciadainformatica.vendas.service;

import com.ciadainformatica.vendas.dao.ProdutoDAO;
import com.ciadainformatica.vendas.domain.Produto;
import com.ciadainformatica.vendas.dto.ProductRequestDTO;
import com.ciadainformatica.vendas.dto.ProductResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoDAO produtoDAO;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    public void deveCadastrarProdutoComSucesso(){
        //arrange
        ProductRequestDTO dto = new ProductRequestDTO();

        dto.setNome("Farinha de Trigo");
        dto.setPreco(new BigDecimal(20.99));
        dto.setEstoqueAtual(20);
        dto.setEstoqueMinimo(1);

        //act
        Produto produtoSalvo = produtoService.cadastrarProduto(dto);

        //assert
        assertNotNull(produtoSalvo);
        assertEquals("Farinha de Trigo", produtoSalvo.getDescricao());

        Mockito.verify(produtoDAO, Mockito.times(1)).salvar(any(Produto.class));
    }

    @Test
    public void deveDesativarProdutoComSucesso(){
        // criacao do Arrange
        Produto produtoFalso = new Produto();
        produtoFalso.setAtivo(true);
        //mockito aprende a "mentir", buscando o ID 1
        Mockito.when(produtoDAO.buscar(1L)).thenReturn(produtoFalso);

        //ACT (acao)
        produtoService.desativarProduto(1L);

       //assert
        assertFalse(produtoFalso.getAtivo());
        Mockito.verify(produtoDAO, Mockito.times(1)).editar(produtoFalso);
    }
}
