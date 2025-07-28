package br.com.zenvix.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signIn")
public class SignIn {

    @GetMapping
    public String signIn() {
        return "signIn";
    }
}
