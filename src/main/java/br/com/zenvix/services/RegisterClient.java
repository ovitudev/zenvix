package br.com.zenvix.services;

import br.com.zenvix.domain.client.Client;
import br.com.zenvix.dto.SignUp.RegisterRequest;
import br.com.zenvix.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class RegisterClient {

    private final ClientRepository repository;
    private final PasswordEncoder passwordEncoder;


    public RegisterClient(ClientRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Client registerClient(RegisterRequest request){
        Optional<Client> existClientByCpf = Optional.ofNullable(repository.findByCpf(request.cpf()));
        if(existClientByCpf.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Client already exists with CPF: " + request.cpf());
        }

        Optional<Client> existClientByEmail = Optional.ofNullable(repository.findByEmail(request.email()));
        if (existClientByEmail.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Client already exists with email: " + request.email());
        }

        Client client = new Client();
        client.setUsername(request.username());
        client.setEmail(request.email());
        client.setCpf(request.cpf());
        client.setPassword(request.password());

        Client newClient = repository.save(client);

        String encodedPassword = passwordEncoder.encode(request.password());
        newClient.setPassword(encodedPassword);

        return repository.save(newClient);
    }
}
