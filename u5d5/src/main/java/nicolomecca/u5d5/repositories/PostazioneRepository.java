package nicolomecca.u5d5.repositories;

import nicolomecca.u5d5.entities.Postazione;
import nicolomecca.u5d5.entities.TipoPostazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostazioneRepository extends JpaRepository<Postazione, Long> {
    List<Postazione> findByTipoAndEdificio_Città(TipoPostazione tipo, String città);

    Optional<Postazione> findById(Long id);


}
