package br.com.zenvix.controllers.clients.api;

import br.com.zenvix.dto.SignUp.RegisterRequest;
import br.com.zenvix.services.RegisterClient;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/clients")
public class RegisterClientsApi {

    private final RegisterClient registerClient;

    public RegisterClientsApi(RegisterClient registerClient) {
        this.registerClient = registerClient;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody @Valid RegisterRequest registerRequest) {
        if (!registerRequest.password().equals(registerRequest.confirmPassword())) {
            return ResponseEntity.badRequest().body("As senhas não conferem!");
        }

        registerClient.registerClient(registerRequest);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String redirectUrl = "/login";

        return ResponseEntity.ok(Map.of("redirectUrl", redirectUrl));
    }
}
