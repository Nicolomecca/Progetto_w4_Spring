package nicolomecca.u5d5.repositories;

import nicolomecca.u5d5.entities.Postazione;
import nicolomecca.u5d5.entities.Prenotazione;
import nicolomecca.u5d5.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByUtenteAndData(Utente utente, LocalDate data);

    List<Prenotazione> findByPostazioneAndData(Postazione postazione, LocalDate data);

}
