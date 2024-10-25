package nicolomecca.u5d5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String nomeCompleto;
    private String email;

    @OneToMany(mappedBy = "utente")
    private List<Prenotazione> prenotazioni = new ArrayList<>();

    public Utente(String userName, String nomeCompleto, String email) {
        this.userName = userName;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
    }
}