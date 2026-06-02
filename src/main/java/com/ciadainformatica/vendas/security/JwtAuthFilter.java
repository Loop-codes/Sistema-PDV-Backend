package com.ciadainformatica.vendas.security;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/pages/*", "/api/*"})
public class JwtAuthFilter implements Filter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // nada a inicializar por enquanto
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest  = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestPath = httpRequest.getRequestURI();

        // rotas públicas
        if (isPublicRoute(requestPath)) {
            chain.doFilter(request, response);
            return;
        }

        String token = extractToken(httpRequest);

        if (token == null || !JwtService.validateToken(token)) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.getWriter().write("{\"erro\": \"Token inválido ou expirado\"}");
            return;
        }

        // informações para uso posterior
        httpRequest.setAttribute("usuario_nome", JwtService.extractNome(token));
        httpRequest.setAttribute("usuario_tipo", JwtService.extractTipo(token));
        httpRequest.setAttribute("usuario_papeis", JwtService.extractPapeis(token));
        httpRequest.setAttribute("jwt_token", token);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // nada a destruir
    }

    private String extractToken(HttpServletRequest request) {
        // Header Authorization
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }

        // Sessão (para JSF, se você salvar lá)
        Object sessionToken = request.getSession().getAttribute("jwt_token");
        if (sessionToken != null) {
            return sessionToken.toString();
        }

        return null;
    }

    private boolean isPublicRoute(String path) {
        return path.contains("/autenticacao.xhtml") ||
                path.contains("/login") ||
                path.contains("/index.xhtml") ||
                path.contains("/javax.faces.resource") ||
                path.endsWith(".css") ||
                path.endsWith(".js") ||
                path.endsWith(".png") ||
                path.endsWith(".jpg") ||
                path.endsWith(".gif") ||
                path.endsWith(".woff") ||
                path.endsWith(".woff2") ||
                path.endsWith(".ttf");
    }
}