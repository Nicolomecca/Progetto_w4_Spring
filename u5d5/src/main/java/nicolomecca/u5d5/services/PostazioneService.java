package nicolomecca.u5d5.services;

import lombok.extern.slf4j.Slf4j;
import nicolomecca.u5d5.entities.Edificio;
import nicolomecca.u5d5.entities.Postazione;
import nicolomecca.u5d5.entities.TipoPostazione;
import nicolomecca.u5d5.repositories.PostazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PostazioneService {
    @Autowired
    private PostazioneRepository postazioneRepository;

    public List<Postazione> cercaPostazioni(TipoPostazione tipo, String città) {
        return postazioneRepository.findByTipoAndEdificio_Città(tipo, città);
    }

    public Postazione creaPostazione(String codice, String descrizione, TipoPostazione tipo, int maxOccupanti, Edificio edificio) {
        log.info("Creazione nuova postazione", codice);
        Postazione postazione = new Postazione();
        postazione.setCodice(codice);
        postazione.setDescrizione(descrizione);
        postazione.setTipo(tipo);
        postazione.setNumeroMassimoOccupanti(maxOccupanti);
        postazione.setEdificio(edificio);
        return postazioneRepository.save(postazione);
    }


}


