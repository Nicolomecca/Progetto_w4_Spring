package nicolomecca.u5d5.services;

import lombok.extern.slf4j.Slf4j;
import nicolomecca.u5d5.entities.Edificio;
import nicolomecca.u5d5.exceptions.RisorsaNonTrovataException;
import nicolomecca.u5d5.repositories.EdificioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EdificioService {
    @Autowired
    private EdificioRepository edificioRepository;

    public Edificio creaEdificio(String nome, String indirizzo, String città) {
        log.info("Creazione nuovo edificio :", nome);
        Edificio edificio = new Edificio();
        edificio.setNome(nome);
        edificio.setIndirizzo(indirizzo);
        edificio.setCittà(città);
        return edificioRepository.save(edificio);
    }

    public Edificio findById(Long id) {
        return edificioRepository.findById(id).orElseThrow(() -> new RisorsaNonTrovataException("Edificio non trovato con id: " + id));
    }

    public List<Edificio> findByCittà(String città) {
        return edificioRepository.findByCittà(città);
    }


}
