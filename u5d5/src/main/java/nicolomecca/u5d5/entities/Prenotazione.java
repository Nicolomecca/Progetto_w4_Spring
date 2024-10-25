package nicolomecca.u5d5.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;
    @ManyToOne
    private Utente utente;
    @ManyToOne
    private Postazione postazione;

    public Prenotazione(LocalDate data) {
        this.data = data;
    }
}

