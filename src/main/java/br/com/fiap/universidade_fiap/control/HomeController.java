package br.com.fiap.universidade_fiap.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.universidade_fiap.repository.MotoRepository;
import br.com.fiap.universidade_fiap.service.AuthenticationService;

/**
 * Controller para página inicial
 * Refatorado para usar AuthenticationService e evitar código duplicado
 */
@Controller
public class HomeController {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping({"/", "/index"})
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("home/index");

        authenticationService.adicionarUsuarioLogado(mv);
        mv.addObject("motos", motoRepository.findAll());

        return mv;
    }

    @GetMapping("/cadastrar-moto")
    public String cadastrarMoto() {
        return "redirect:/motos/novo";
    }
}
