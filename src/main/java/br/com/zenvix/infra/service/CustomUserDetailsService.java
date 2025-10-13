package br.com.zenvix.infra.service;


import br.com.zenvix.domain.client.Client;
import br.com.zenvix.repositories.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private final ClientRepository clientRepository;

    public CustomUserDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(
            String identifier
    ) throws UsernameNotFoundException {
        Client client = clientRepository.findByCpf(identifier);
        if (client != null){
            return (UserDetails) client;
        }

        throw new UsernameNotFoundException("Usuário não encontrado com o identificado: " + identifier);
    }
}
