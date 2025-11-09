package br.com.fiap.universidade_fiap.control;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.universidade_fiap.model.Operacao;
import br.com.fiap.universidade_fiap.model.Usuario;
import br.com.fiap.universidade_fiap.repository.MotoRepository;
import br.com.fiap.universidade_fiap.repository.OperacaoRepository;
import br.com.fiap.universidade_fiap.service.AuthenticationService;

/**
 * Controller para gerenciar Operações
 * Refatorado para usar AuthenticationService e evitar código duplicado
 */
@Controller
public class OperacaoController {

    private static final Logger logger = LoggerFactory.getLogger(OperacaoController.class);

    @Autowired
    private OperacaoRepository operacaoRepository;
    
    @Autowired
    private MotoRepository motoRepository;
    
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/operacoes")
    public ModelAndView operacoes() {
        ModelAndView mv = new ModelAndView("/operacoes/lista");
        
        authenticationService.adicionarUsuarioLogado(mv);
        
        List<Operacao> operacoes = operacaoRepository.findAll();
        mv.addObject("operacoes", operacoes);
        
        return mv;
    }

    @GetMapping("/operacoes/nova")
    public ModelAndView novaOperacao() {
        logger.debug("Nova operação");
        try {
            if (!authenticationService.isAuthenticated()) {
                logger.warn("Usuário não autenticado tentando criar operação");
                return new ModelAndView("redirect:/login");
            }
            
            ModelAndView mv = new ModelAndView("/operacoes/cadastro");
            authenticationService.adicionarUsuarioLogado(mv);
            
            mv.addObject("operacao", new Operacao());
            mv.addObject("motos", motoRepository.findAll());
            mv.addObject("tiposOperacao", new String[]{"CHECK_IN", "CHECK_OUT"});
            
            return mv;
        } catch (Exception e) {
            logger.error("Erro ao criar nova operação: {}", e.getMessage(), e);
            ModelAndView mv = new ModelAndView("error/500");
            mv.addObject("error", "Erro interno: " + e.getMessage());
            return mv;
        }
    }

    @PostMapping("/operacoes/salvar")
    public ModelAndView salvarOperacao(Operacao operacao) {
        logger.info("Salvando operação: tipo={}", operacao.getTipoOperacao());
        
        try {
            Usuario usuarioLogado = authenticationService.getUsuarioLogado();
            
            if (usuarioLogado == null) {
                logger.warn("Tentativa de salvar operação sem autenticação");
                return new ModelAndView("redirect:/login");
            }
            
            // Buscar a moto pelo ID
            if (operacao.getMoto() != null && operacao.getMoto().getId() != null) {
                Long motoId = operacao.getMoto().getId();
                
                motoRepository.findById(motoId).ifPresent(moto -> {
                    operacao.setMoto(moto);
                    operacao.setUsuario(usuarioLogado);
                    
                    operacaoRepository.save(operacao);
                    logger.info("Operação salva com sucesso: id={}", operacao.getId());
                });
            }
            
            return new ModelAndView("redirect:/operacoes?sucesso=true");
        } catch (Exception e) {
            logger.error("Erro ao salvar operação: {}", e.getMessage(), e);
            
            ModelAndView mv = new ModelAndView("/operacoes/cadastro");
            authenticationService.adicionarUsuarioLogado(mv);
            
            mv.addObject("operacao", operacao);
            mv.addObject("motos", motoRepository.findAll());
            mv.addObject("tiposOperacao", new String[]{"CHECK_IN", "CHECK_OUT"});
            mv.addObject("erro", "Erro ao salvar operação: " + e.getMessage());
            return mv;
        }
    }
}
