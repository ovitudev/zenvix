package br.com.zenvix.pages;

import br.com.zenvix.domain.client.Client;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class Home {

    @GetMapping
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String displayName;

        // O principal (userDetails) é o nosso objeto Client.
        // Fazemos o cast para acessar o metodo getClientName().
        if (userDetails instanceof Client) {
            displayName = ((Client) userDetails).getClientName();
        } else {
            displayName = userDetails.getUsername();
        }

        // Adiciona o nome do usuário ao modelo para ser exibido na saudação
        model.addAttribute("username", displayName);
        return "home"; // Renderiza o novo arquivo home.html
    }
}