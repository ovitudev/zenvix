package br.com.zenvix.repositories;

import br.com.zenvix.domain.client.Client;
import br.com.zenvix.dto.SignUp.RegisterRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should get Client by CPF successfully from DB")
    void existsClientByCpf() {
        String cpf = "12345678911";
        RegisterRequest data = new RegisterRequest("Victor", "teste@gmail.com", "12345678911", "teste123", "teste123");
        this.registerClient(data);

        Optional<Client> result = Optional.ofNullable(this.clientRepository.findByCpf(cpf));

        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Should not get Client by CPF from DB")
    void notExistsClientByCpf() {
        String cpf = "12345678910";
        RegisterRequest data = new RegisterRequest("Victor", "teste@gmail.com", "12345678911", "teste123", "teste123");
        this.registerClient(data);

        Optional<Client> result = Optional.ofNullable(this.clientRepository.findByCpf(cpf));

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should get Client by E-mail successfully from DB")
    void existsClientByEmail() {
        String email = "teste@gmail.com";
        RegisterRequest data = new RegisterRequest("Victor", "teste@gmail.com", "12346578911", "teste123", "teste123");
        this.registerClient(data);

        Optional<Client> result = Optional.ofNullable(this.clientRepository.findByEmail(email));

        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Should not get Client by E-mail from DB")
    void notExistsClientByEmail() {
        String email = "teste@gmail.com";
        RegisterRequest data = new RegisterRequest("Victor", "teste123@gmail.com", "12346578911", "teste123", "teste123");
        this.registerClient(data);

        Optional<Client> result = Optional.ofNullable(this.clientRepository.findByEmail(email));

        assertTrue(result.isEmpty());
    }

    private Client registerClient(RegisterRequest data) {
        Client newClient = new Client(data);
        this.entityManager.persist(newClient);
        this.entityManager.flush();
        return newClient;
    }
}