package com.ciadainformatica.vendas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class Usuario extends GenericDomain{

    @Column(length = 32, nullable = false)
    private String senha;

    @Column(nullable = false)
    private Character tipo;

    @Column(nullable = false)
    private Boolean ativo;

    @JoinColumn(nullable = false, unique = true)
    @OneToOne
    private Pessoa pessoa;

    public Usuario() {
        super();
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Character getTipo() {
        return tipo;
    }
    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}