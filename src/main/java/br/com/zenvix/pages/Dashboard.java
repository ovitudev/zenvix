package br.com.zenvix.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class Dashboard {

    @GetMapping
    public String dashboard() {
        return "dashboard";
    }
}
