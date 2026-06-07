package com.ciadainformatica.vendas.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@SuppressWarnings("serial")// ignora warnings deste tipo
@Entity//diz que a clase é uma entidade, uma tabela
public class Produto extends GenericDomain{
	
	@Column(length = 25, nullable = false, unique=true)//diz: tamanho de 25 caracteres e não poderá ser nulo
	private String codBarras;
	
	@Column(length = 100, nullable = false, unique=true)//diz: tamanho de 100 caracteres e não poderá ser nulo
	private String descricao;
	
	@Column(nullable = false)// diz: não poderá ser nulo
	private Short quantidade;
	
	@Column(nullable = false, precision = 6, scale = 2)// diz: não poderá ser nulo, terá até 6 algarismos numericos sendo que 2 são após a virgula
	private BigDecimal preco;
	
	@ManyToOne//diz: que varios produtos poderão ter apenas um fabricante, fabricante é a chave estrangeira de produto
	@JoinColumn(nullable = false)
	private Fabricante fabricante;

    @Column(name = "estoque_atual", nullable = false, columnDefinition = "integer check (estoque_atual >= 0)")
    private Integer estoqueAtual;

    @Column(name = "estoque_minimo", nullable = false)
    private Integer estoqueMinimo;

    @Column(nullable = false)
    private Boolean ativo = true;
	
	
	public String getDescricao() {
		return descricao;
	}
	public String getCodBarras() {
		return codBarras;
	}
	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Short getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Short quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public Fabricante getFabricante() {
		return fabricante;
	}
	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}
    public Integer getEstoqueAtual() {
        return estoqueAtual;
    }
    public void setEstoqueAtual(Integer estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }
    public Integer getEstoqueMinimo() {
        return estoqueMinimo;
    }
    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }
    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }


}
