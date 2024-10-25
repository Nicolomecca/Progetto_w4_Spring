package nicolomecca.u5d5.repositories;

import nicolomecca.u5d5.entities.Edificio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EdificioRepository extends JpaRepository<Edificio, Long> {

    List<Edificio> findByCittà(String città);
}
