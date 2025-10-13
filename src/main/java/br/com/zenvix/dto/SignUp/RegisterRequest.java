package br.com.zenvix.dto.SignUp;

import br.com.zenvix.domain.client.Client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record RegisterRequest(
        @NotBlank(message = "Nome não pode ficar em branco!")
        @Size(min = 3)
        String username,

        @NotBlank(message = "E-mail não pode ficar em branco!")
        @Email(message = "E-mail inválido!")
        @Size(min = 10)
        String email,

        @NotBlank(message = "CPF não pode ficar em branco!")
        @CPF(message = "CPF inválido!")
        @Size(min = 11, max = 14)
        String cpf,

        @NotBlank(message = "Senha não pode ficar em branco!")
        @Size(min = 8)
        String password,

        @NotBlank(message = "Confirmação de senha não pode ficar em branco!")
        @Size(min = 8)
        String confirmPassword

) implements SignUpUserRequest {

    public Client toClient() {
        Client client = new Client();

        client.setUsername(username);
        client.setEmail(email);
        client.setCpf(cpf);
        client.setPassword(password);

        return client;
    }
}
