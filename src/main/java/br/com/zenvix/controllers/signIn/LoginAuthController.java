package br.com.zenvix.controllers.signIn;

import br.com.zenvix.dto.signIn.SignInRequest;
import br.com.zenvix.infra.service.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginAuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginAuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid SignInRequest signInRequest, HttpServletResponse httpServletResponse) {
        try {
            // A autenticação agora usa o CPF, conforme o request
            var usernamePassword = new UsernamePasswordAuthenticationToken(signInRequest.cpf(), signInRequest.password());
            var auth = authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generatedToken((UserDetails) auth.getPrincipal());

            Cookie cookie = new Cookie("jwt_token", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(2 * 60 * 60); // 2 horas

            httpServletResponse.addCookie(cookie);

            // Retorna a URL de redirecionamento para o JavaScript
            return ResponseEntity.ok(Map.of("redirectUrl", "/home"));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(Map.of("error", "CPF ou senha inválidos."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Ocorreu um erro interno."));
        }
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletResponse httpServletResponse) {

        Cookie cookie = new Cookie("jwt_token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);

        SecurityContextHolder.clearContext();

        return new ModelAndView("redirect:/v1/clients/login?logout");
    }
}