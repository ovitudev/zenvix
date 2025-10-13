package br.com.zenvix.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class User {

    @Column(nullable = false)
    @Size(min = 3, max = 50)
    private String username;

    @Column(nullable = false, unique = true)
    @Size(min = 8, max = 30)
    private String email;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    @Size(min = 8)
    private String password;

}
