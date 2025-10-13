package br.com.zenvix.controllers.clients.view;

import br.com.zenvix.dto.SignUp.RegisterRequest;
import br.com.zenvix.services.RegisterClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/v1/clients")
public class RegisterClientsView {

    private final RegisterClient registerClient;

    public RegisterClientsView(RegisterClient registerClient) {
        this.registerClient = registerClient;
    }

    @GetMapping("/register")
    public String register(){
        return "auth";
    }

    @PostMapping("/register")
    public String registerClient(@ModelAttribute RegisterRequest registerRequest, RedirectAttributes redirectAttributes){
        try {
            registerClient.registerClient(registerRequest);
            redirectAttributes.addFlashAttribute("message", "Register successful");

        return "redirect:/login";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Register failed");
            return "redirect:/v1/clients/register";
        }
    }

}
