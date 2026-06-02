package com.ciadainformatica.vendas.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.ciadainformatica.vendas.bean.AutenticacaoBean;
import com.ciadainformatica.vendas.domain.Usuario;
import com.ciadainformatica.vendas.security.SecurityConfig;

@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        String paginaAtual = Faces.getViewId();

        boolean ehPaginaDeAutenticacao = paginaAtual.contains("autenticacao.xhtml");

        if (!ehPaginaDeAutenticacao) {
            AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");

            if (autenticacaoBean == null) {
                Faces.navigate("/pages/autenticacao.xhtml");
                return;
            }

            Usuario usuario = autenticacaoBean.getUsuarioLogado();
            if (usuario == null) {
                Faces.navigate("/pages/autenticacao.xhtml");
                return;
            }

            // Verificar permissões por página
            validarAcesso(paginaAtual, usuario);
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    /**
     * Valida o acesso baseado na página e no tipo de usuário
     */
    private void validarAcesso(String paginaAtual, Usuario usuario) {
        // Páginas que requerem ADMIN ou GERENTE
        if (paginaAtual.contains("usuarios.xhtml") ||
                paginaAtual.contains("funcionarios.xhtml") ||
                paginaAtual.contains("produtos.xhtml") ||
                paginaAtual.contains("fabricantes.xhtml") ||
                paginaAtual.contains("cidades.xhtml") ||
                paginaAtual.contains("estados.xhtml") ||
                paginaAtual.contains("troca.xhtml") ||
                paginaAtual.contains("financeiro.xhtml") ||
                paginaAtual.contains("cancelarVenda.xhtml") ||
                paginaAtual.contains("relatorio")) {

            if (usuario.getTipo() != 'A' && usuario.getTipo() != 'G') {
                Faces.navigate("/pages/principal.xhtml");
                Messages.addFlashGlobalError("Você não tem privilégios para entrar nesta seção!");
                return;
            }
        }

        // Páginas que apenas ADMIN pode acessar
        if (paginaAtual.contains("auditoria.xhtml") ||
                paginaAtual.contains("backup.xhtml")) {

            if (usuario.getTipo() != 'A') {
                Faces.navigate("/pages/principal.xhtml");
                Messages.addFlashGlobalError("Acesso restrito a administradores!");
                return;
            }
        }
    }
}