package com.ciadainformatica.vendas.dto;

import com.ciadainformatica.vendas.domain.Produto;

import java.math.BigDecimal;

public class ProductResponseDTO {

    private Long id;
    private String nome;
    private BigDecimal preco;
    private Integer estoqueAtual;

    public ProductResponseDTO(Produto produto) {
        this.id = produto.getCodigo();
        this.nome = produto.getDescricao();
        this.preco = produto.getPreco();
        this.estoqueAtual = produto.getEstoqueAtual();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }


    public BigDecimal getPreco() {
        return preco;
    }
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }


    public Integer getEstoqueAtual() {
        return estoqueAtual;
    }
    public void setEstoqueAtual(Integer estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }
}
