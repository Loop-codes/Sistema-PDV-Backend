package com.ciadainformatica.vendas.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ciadainformatica.vendas.dao.UsuarioDAO;
import com.ciadainformatica.vendas.domain.Usuario;
import com.ciadainformatica.vendas.security.JwtService;
import com.ciadainformatica.vendas.security.SecurityConfig;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    @POST
    @Path("/login")
    public Response login(LoginRequest loginRequest) {
        try {
            if (loginRequest.getCpf() == null || loginRequest.getSenha() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"erro\": \"CPF e senha são obrigatórios\"}")
                        .build();
            }

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = usuarioDAO.autenticar(loginRequest.getCpf(), loginRequest.getSenha());

            if (usuario == null || !usuario.getAtivo()) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"erro\": \"CPF ou senha inválidos\"}")
                        .build();
            }

            String token = JwtService.generateToken(usuario);
            LoginResponse response = new LoginResponse(token, usuario.getPessoa().getNome());

            return Response.ok(response)
                    .header("Authorization", "Bearer " + token)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/me")
    public Response getUsuarioLogado(@Context HttpServletRequest request) {
        if (!SecurityConfig.temAcesso(request, SecurityConfig.ROLE_ADMIN,
                SecurityConfig.ROLE_CAIXA,
                SecurityConfig.ROLE_GERENTE)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"erro\": \"Acesso negado\"}")
                    .build();
        }

        String nome = SecurityConfig.getUsuarioNome(request);
        String cpf = SecurityConfig.getUsuarioCpf(request);

        UsuarioResponse response = new UsuarioResponse(nome, cpf);
        return Response.ok(response).build();
    }

    @GET
    @Path("/validate")
    public Response validateToken(@Context HttpServletRequest request) {
        String token = SecurityConfig.getToken(request);
        if (token == null || !JwtService.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"valido\": false}")
                    .build();
        }
        return Response.ok("{\"valido\": true}").build();
    }

    // DTOs internos
    public static class LoginRequest {
        private String cpf;
        private String senha;

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }

    public static class LoginResponse {
        private String token;
        private String nome;

        public LoginResponse(String token, String nome) {
            this.token = token;
            this.nome = nome;
        }

        public String getToken() {
            return token;
        }

        public String getNome() {
            return nome;
        }
    }

    public static class UsuarioResponse {
        private String nome;
        private String cpf;

        public UsuarioResponse(String nome, String cpf) {
            this.nome = nome;
            this.cpf = cpf;
        }

        public String getNome() {
            return nome;
        }

        public String getCpf() {
            return cpf;
        }
    }
}