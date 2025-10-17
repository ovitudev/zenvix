package br.com.zenvix.dto.signIn;

public record SignInRequest(
        String cpf,
        String password
) {
}
