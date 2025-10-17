package br.com.zenvix.domain.client;

import br.com.zenvix.domain.user.User;
import br.com.zenvix.dto.signUp.RegisterRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLIENTS")
public class Client extends User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    public Client(RegisterRequest data) {
        this.setUsername(data.username());
        this.setEmail(data.email());
        this.setCpf(data.cpf());
        this.setPassword(data.password());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Como não estamos usando papéis (roles) ainda, retornamos uma lista vazia.
        return Collections.emptyList();
    }

    /**
     * Este método é da interface UserDetails.
     * Para a autenticação, o nosso "username" principal será o CPF.
     */
    @Override
    public String getUsername() {
        return this.getCpf();
    }

    /**
     * Método para obter o nome de exibição do usuário,
     * já que getUsername() agora retorna o CPF para o Spring Security.
     */
    public String getClientName() {
        return super.getUsername(); // Acessa o getUsername() original da classe User
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}