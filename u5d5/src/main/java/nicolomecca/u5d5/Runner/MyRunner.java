package nicolomecca.u5d5.Runner;

import lombok.extern.slf4j.Slf4j;
import nicolomecca.u5d5.repositories.EdificioRepository;
import nicolomecca.u5d5.repositories.PostazioneRepository;
import nicolomecca.u5d5.repositories.UtenteRepository;
import nicolomecca.u5d5.services.PostazioneService;
import nicolomecca.u5d5.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyRunner implements CommandLineRunner {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private EdificioRepository edificioRepository;
    @Autowired
    private PostazioneRepository postazioneRepository;
    @Autowired
    private PrenotazioneService prenotazioneService;
    @Autowired
    private PostazioneService postazioneService;

    @Override
    public void run(String... args) throws Exception {
        log.info("inizializzazione");


    }
}
