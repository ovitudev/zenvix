package br.com.zenvix.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sign-up")
public class SignUp {

    @GetMapping
    public String signUp() {
        // Redireciona para a página de login com o painel de registro ativo
        return "redirect:/login?action=register";
    }
}
