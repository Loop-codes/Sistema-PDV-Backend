package com.ciadainformatica.vendas.domain;

public enum Papel {
    ROLE_ADMIN("Administrador", 'A'),
    ROLE_CAIXA("Caixa", 'C'),
    ROLE_GERENTE("Gerente", 'G');

    private String descricao;
    private Character codigo;

    Papel(String descricao, Character codigo) {
        this.descricao = descricao;
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Character getCodigo() {
        return codigo;
    }

    public static Papel porCodigo(Character codigo) {
        if (codigo == null) return null;
        for (Papel papel : Papel.values()) {
            if (papel.codigo.equals(codigo)) {
                return papel;
            }
        }
        return null;
    }

    public static String getRoleFromTipo(Character tipo) {
        Papel papel = porCodigo(tipo);
        return papel != null ? papel.name() : null;
    }
}