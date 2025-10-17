package br.com.zenvix.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class SignIn {

    @GetMapping
    public String signIn(@RequestParam(name = "action", required = false) String action, Model model) {
        if ("register".equals(action)) {
            model.addAttribute("show", "register");
        }
        return "auth";
    }
}
