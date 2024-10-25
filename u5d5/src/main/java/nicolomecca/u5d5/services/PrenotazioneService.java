package nicolomecca.u5d5.services;

import lombok.extern.slf4j.Slf4j;
import nicolomecca.u5d5.entities.Postazione;
import nicolomecca.u5d5.entities.Prenotazione;
import nicolomecca.u5d5.entities.Utente;
import nicolomecca.u5d5.exceptions.PrenotazioneNonValidaException;
import nicolomecca.u5d5.exceptions.RisorsaNonTrovataException;
import nicolomecca.u5d5.repositories.PostazioneRepository;
import nicolomecca.u5d5.repositories.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private PostazioneRepository postazioneRepository;

    public Prenotazione prenota(Utente utente, Long postazioneId, LocalDate data) {
        List<Prenotazione> prenotazioniUtente = prenotazioneRepository.findByUtenteAndData(utente, data);
        if (prenotazioniUtente.isEmpty()) {
            Postazione postazione = postazioneRepository.findById(postazioneId).orElseThrow(() -> new RisorsaNonTrovataException(
                    "Postazione non trovata con id: " + postazioneId));

            List<Prenotazione> prenotazioniPostazione = prenotazioneRepository.findByPostazioneAndData(postazione, data);
            if (prenotazioniPostazione.isEmpty()) {
                Prenotazione prenotazione = new Prenotazione();
                prenotazione.setUtente(utente);
                prenotazione.setPostazione(postazione);
                prenotazione.setData(data);
                return prenotazioneRepository.save(prenotazione);
            } else {
                throw new PrenotazioneNonValidaException("Postazione " + postazione.getCodice() + " già prenotata per la data " + data);
            }
        } else {
            throw new PrenotazioneNonValidaException("Utente " + utente.getUsername() + " ha già una prenotazione per la data " + data);
        }
    }

    public void cancellaPrenotazione(Long id) {
        Prenotazione prenotazione = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("Prenotazione non trovata con id: " + id));
        prenotazioneRepository.delete(prenotazione);
    }

    public List<Prenotazione> getPrenotazioniUtente(Utente utente) {
        return prenotazioneRepository.findByUtente(utente);
    }

    public Prenotazione findById(Long id) {
        return prenotazioneRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("Prenotazione non trovata con id: " + id));
    }

}