package nicolomecca.u5d5.services;

import lombok.extern.slf4j.Slf4j;
import nicolomecca.u5d5.entities.Utente;
import nicolomecca.u5d5.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    public Utente creaUtente(String username, String nomeCompleto, String email) {
        log.info("Creazione nuovo utente ", username);
        Utente utente = new Utente();
        utente.setUserName(username);
        utente.setNomeCompleto(nomeCompleto);
        utente.setEmail(email);
        return utenteRepository.save(utente);
    }
}
