package nicolomecca.u5d5.Runner;

import lombok.extern.slf4j.Slf4j;
import nicolomecca.u5d5.entities.*;
import nicolomecca.u5d5.exceptions.PrenotazioneException;
import nicolomecca.u5d5.exceptions.PrenotazioneNonValidaException;
import nicolomecca.u5d5.services.EdificioService;
import nicolomecca.u5d5.services.PostazioneService;
import nicolomecca.u5d5.services.PrenotazioneService;
import nicolomecca.u5d5.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class MyRunner implements CommandLineRunner {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private EdificioService edificioService;
    @Autowired
    private PostazioneService postazioneService;
    @Autowired
    private PrenotazioneService prenotazioneService;

    @Override
    public void run(String... args) throws Exception {
        log.info("inizializzazione");

        Utente utente1 = utenteService.creaUtente("user1", "Aldo Baglio", "peperonata@gmail.com");
        Utente utente2 = utenteService.creaUtente("user2", "Giovanni Storti", "topimorti@gmail.com");

        Edificio edificio1 = edificioService.creaEdificio("Sede Centrale", "Via Marzabotto 1", "Milano");
        Postazione postazione1 = postazioneService.creaPostazione("P1", "Postazione privata", TipoPostazione.PRIVATO, 1, edificio1);

        try {
            Prenotazione prenotazione = prenotazioneService.prenota(utente1, postazione1.getId(), LocalDate.now().plusDays(1));
            log.info("Prenotazione effettuata con successo:" + prenotazione.getId());
        } catch (PrenotazioneException e) {
            log.info(e.getMessage());
        }

        try {
            log.info("provo a prenotare nella stessa data");
            prenotazioneService.prenota(utente1, postazione1.getId(), LocalDate.now().plusDays(1));
        } catch (PrenotazioneNonValidaException e) {
            log.info(e.getMessage());
        }


    }
}
