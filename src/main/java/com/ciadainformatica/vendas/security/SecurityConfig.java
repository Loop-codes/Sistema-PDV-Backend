package com.ciadainformatica.vendas.security;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class SecurityConfig {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_CAIXA = "ROLE_CAIXA";
    public static final String ROLE_GERENTE = "ROLE_GERENTE";

    /**
     * Verifica se o usuário tem acesso (qualquer um dos papéis)
     */
    public static boolean temAcesso(HttpServletRequest request, String... papaisRequeridos) {
        @SuppressWarnings("unchecked")
        Set<String> papeis = (Set<String>) request.getAttribute("usuario_papeis");

        if (papeis == null || papeis.isEmpty()) {
            return false;
        }

        for (String papel : papaisRequeridos) {
            if (papeis.contains(papel)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se o usuário tem um papel específico
     */
    public static boolean temPapel(HttpServletRequest request, String papel) {
        @SuppressWarnings("unchecked")
        Set<String> papeis = (Set<String>) request.getAttribute("usuario_papeis");
        return papeis != null && papeis.contains(papel);
    }

    /**
     * Verifica se é ADMIN
     */
    public static boolean isAdmin(HttpServletRequest request) {
        return temPapel(request, ROLE_ADMIN);
    }

    /**
     * Verifica se é GERENTE
     */
    public static boolean isGerente(HttpServletRequest request) {
        return temPapel(request, ROLE_GERENTE);
    }

    /**
     * Verifica se é CAIXA
     */
    public static boolean isCaixa(HttpServletRequest request) {
        return temPapel(request, ROLE_CAIXA);
    }

    /**
     * Obtém o ID do usuário logado
     */
    public static Long getUsuarioId(HttpServletRequest request) {
        return (Long) request.getAttribute("usuario_id");
    }

    /**
     * Obtém o CPF do usuário logado
     */
    public static String getUsuarioCpf(HttpServletRequest request) {
        return (String) request.getAttribute("usuario_cpf");
    }

    /**
     * Obtém o nome do usuário logado
     */
    public static String getUsuarioNome(HttpServletRequest request) {
        return (String) request.getAttribute("usuario_nome");
    }

    /**
     * Obtém o tipo do usuário (A/C/G)
     */
    public static Character getUsuarioTipo(HttpServletRequest request) {
        return (Character) request.getAttribute("usuario_tipo");
    }

    /**
     * Obtém todos os papéis do usuário
     */
    @SuppressWarnings("unchecked")
    public static Set<String> getUsuarioPapeis(HttpServletRequest request) {
        return (Set<String>) request.getAttribute("usuario_papeis");
    }

    /**
     * Obtém o token JWT
     */
    public static String getToken(HttpServletRequest request) {
        return (String) request.getAttribute("jwt_token");
    }
}