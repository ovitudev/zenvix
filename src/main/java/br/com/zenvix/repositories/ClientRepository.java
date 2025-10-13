package br.com.zenvix.repositories;

import br.com.zenvix.domain.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Query("SELECT u FROM Client u WHERE u.cpf = :cpf")
    Client findByCpf(@Param("cpf") String cpf);

    @Query("SELECT u FROM Client u WHERE u.email = :email")
    Client findByEmail(@Param("email") String email);
}
