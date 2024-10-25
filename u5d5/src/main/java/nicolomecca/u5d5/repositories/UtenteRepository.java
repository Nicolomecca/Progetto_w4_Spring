package nicolomecca.u5d5.repositories;

import nicolomecca.u5d5.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Optional<Utente> findById(Long id);


}
