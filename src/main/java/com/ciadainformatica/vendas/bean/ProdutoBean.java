package com.ciadainformatica.vendas.bean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import com.ciadainformatica.vendas.security.SecurityConfig;

@ManagedBean
@ViewScoped
public class ProdutoBean {

    public void salvarProduto() {
        HttpServletRequest request = (HttpServletRequest) Faces.getExternalContext().getRequest();

        // Verificar se tem acesso de ADMIN ou GERENTE
        if (!SecurityConfig.temAcesso(request, SecurityConfig.ROLE_ADMIN, SecurityConfig.ROLE_GERENTE)) {
            Messages.addGlobalError("Acesso negado. Apenas ADMIN ou GERENTE podem salvar produtos.");
            return;
        }

        Long usuarioId = SecurityConfig.getUsuarioId(request);
        String usuarioNome = SecurityConfig.getUsuarioNome(request);

        System.out.println("Produto salvo por: " + usuarioNome + " (ID: " + usuarioId + ")");
        // ... resto do código
    }

    public void apenasAdmin() {
        HttpServletRequest request = (HttpServletRequest) Faces.getExternalContext().getRequest();

        if (!SecurityConfig.isAdmin(request)) {
            Messages.addGlobalError("Acesso restrito a administradores");
            return;
        }
        // ... código protegido
    }

    public void apenasGerente() {
        HttpServletRequest request = (HttpServletRequest) Faces.getExternalContext().getRequest();

        if (!SecurityConfig.isGerente(request)) {
            Messages.addGlobalError("Acesso restrito a gerentes");
            return;
        }
        // ... código protegido
    }
}