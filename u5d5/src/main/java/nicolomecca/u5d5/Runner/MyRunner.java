package nicolomecca.u5d5.Runner;

import nicolomecca.u5d5.entities.*;
import nicolomecca.u5d5.exceptions.PrenotazioneException;
import nicolomecca.u5d5.exceptions.PrenotazioneNonValidaException;
import nicolomecca.u5d5.exceptions.RisorsaGiaEsistenteException;
import nicolomecca.u5d5.exceptions.RisorsaNonTrovataException;
import nicolomecca.u5d5.services.EdificioService;
import nicolomecca.u5d5.services.PostazioneService;
import nicolomecca.u5d5.services.PrenotazioneService;
import nicolomecca.u5d5.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
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
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean continua = true;

        while (continua) {
            try {
                System.out.println("\n=== GESTIONE PRENOTAZIONI ===");
                System.out.println("1. Crea nuovo utente");
                System.out.println("2. Crea nuovo edificio");
                System.out.println("3. Crea nuova postazione");
                System.out.println("4. Effettua prenotazione");
                System.out.println("5. Cerca postazioni per tipo e città");
                System.out.println("6. Visualizza prenotazioni utente");
                System.out.println("7. Cancella prenotazione");
                System.out.println("0. Esci");
                System.out.print("Scelta: ");

                int scelta = scanner.nextInt();
                scanner.nextLine();

                switch (scelta) {
                    case 1:
                        creaUtente(scanner);
                        break;
                    case 2:
                        creaEdificio(scanner);
                        break;
                    case 3:
                        creaPostazione(scanner);
                        break;
                    case 4:
                        effettuaPrenotazione(scanner);
                        break;
                    case 5:
                        cercaPostazioni(scanner);
                        break;
                    case 6:
                        visualizzaPrenotazioniUtente(scanner);
                        break;
                    case 7:
                        cancellaPrenotazione(scanner);
                        break;
                    case 0:
                        continua = false;
                        System.out.println("Arrivederci!");
                        break;
                    default:
                        System.out.println("Scelta non valida!");
                        break;
                }
            } catch (PrenotazioneException e) {
                System.out.println("Errore nell'operazione: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void creaUtente(Scanner scanner) {
        try {
            System.out.print("Inserisci username: ");
            String username = scanner.nextLine();
            System.out.print("Inserisci nome completo: ");
            String nomeCompleto = scanner.nextLine();
            System.out.print("Inserisci email: ");
            String email = scanner.nextLine();

            Utente utente = utenteService.creaUtente(username, nomeCompleto, email);
            System.out.println("Utente creato con successo! ID: " + utente.getId());
        } catch (RisorsaGiaEsistenteException e) {
            System.out.println("Username o email già in uso: " + e.getMessage());
        }
    }

    private void creaEdificio(Scanner scanner) {
        try {
            System.out.print("Inserisci nome edificio: ");
            String nome = scanner.nextLine();
            System.out.print("Inserisci indirizzo: ");
            String indirizzo = scanner.nextLine();
            System.out.print("Inserisci città: ");
            String città = scanner.nextLine();

            Edificio edificio = edificioService.creaEdificio(nome, indirizzo, città);
            System.out.println("Edificio creato con successo! ID: " + edificio.getId());
        } catch (RisorsaGiaEsistenteException e) {
            System.out.println("Edificio già esistente: " + e.getMessage());
        }
    }

    private void creaPostazione(Scanner scanner) {
        try {
            System.out.print("Inserisci codice postazione: ");
            String codice = scanner.nextLine();
            System.out.print("Inserisci descrizione: ");
            String descrizione = scanner.nextLine();
            System.out.print("Inserisci tipo (PRIVATO/OPENSPACE/SALA_RIUNIONI): ");
            TipoPostazione tipo = TipoPostazione.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Inserisci numero massimo occupanti: ");
            int maxOccupanti = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Inserisci ID edificio: ");
            Long edificioId = scanner.nextLong();

            Edificio edificio = edificioService.findById(edificioId);
            Postazione postazione = postazioneService.creaPostazione(codice, descrizione, tipo, maxOccupanti, edificio);
            System.out.println("Postazione creata con successo! ID: " + postazione.getId());
        } catch (RisorsaNonTrovataException e) {
            System.out.println("Edificio non trovato: " + e.getMessage());
        } catch (RisorsaGiaEsistenteException e) {
            System.out.println("Postazione già esistente: " + e.getMessage());
        }
    }

    private void effettuaPrenotazione(Scanner scanner) {
        try {
            System.out.print("Inserisci ID utente: ");
            Long utenteId = scanner.nextLong();
            System.out.print("Inserisci ID postazione: ");
            Long postazioneId = scanner.nextLong();
            System.out.print("Tra quanti giorni vuoi prenotare? (1 o più): ");
            int giorni = scanner.nextInt();

            Utente utente = utenteService.findById(utenteId);
            LocalDate dataPrenotazione = LocalDate.now().plusDays(giorni);
            Prenotazione prenotazione = prenotazioneService.prenota(utente, postazioneId, dataPrenotazione);
            System.out.println("Prenotazione effettuata con successo! ID: " + prenotazione.getId());
        } catch (RisorsaNonTrovataException e) {
            System.out.println("Risorsa non trovata: " + e.getMessage());
        } catch (PrenotazioneNonValidaException e) {
            System.out.println("Prenotazione non valida: " + e.getMessage());
        }
    }

    private void cercaPostazioni(Scanner scanner) {
        try {
            System.out.println("\nTipi di postazione disponibili:");
            System.out.println("1. PRIVATO");
            System.out.println("2. OPENSPACE");
            System.out.println("3. SALA_RIUNIONI");
            System.out.print("Seleziona il tipo (1-3): ");

            int tipoScelta = scanner.nextInt();
            scanner.nextLine();

            TipoPostazione tipo;
            switch (tipoScelta) {
                case 1:
                    tipo = TipoPostazione.PRIVATO;
                    break;
                case 2:
                    tipo = TipoPostazione.OPENSPACE;
                    break;
                case 3:
                    tipo = TipoPostazione.SALA_RIUNIONI;
                    break;
                default:
                    System.out.println("Tipo non valido");
                    return;
            }

            System.out.print("Inserisci città: ");
            String città = scanner.nextLine();

            List<Postazione> postazioni = postazioneService.cercaPostazioni(tipo, città);
            if (postazioni.isEmpty()) {
                System.out.println("Nessuna postazione trovata per i criteri specificati.");
            } else {
                System.out.println("Postazioni trovate:");
                postazioni.forEach(p -> System.out.println(
                        "ID: " + p.getId() +
                                " - Tipo: " + p.getTipo() +
                                " - Edificio: " + p.getEdificio().getNome() +
                                " - Città: " + p.getEdificio().getCittà() +
                                " - Max occupanti: " + p.getNumeroMassimoOccupanti() +
                                " - Descrizione: " + p.getDescrizione()
                ));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo postazione non valido: " + e.getMessage());
        }
    }

    private void visualizzaPrenotazioniUtente(Scanner scanner) {
        try {
            System.out.print("Inserisci ID utente: ");
            Long utenteId = scanner.nextLong();

            Utente utente = utenteService.findById(utenteId);
            List<Prenotazione> prenotazioni = prenotazioneService.getPrenotazioniUtente(utente);
            if (prenotazioni.isEmpty()) {
                System.out.println("Nessuna prenotazione trovata per questo utente.");
            } else {
                System.out.println("Prenotazioni trovate:");
                prenotazioni.forEach(p -> System.out.println(
                        "ID: " + p.getId() +
                                " - Data: " + p.getData() +
                                " - Postazione: " + p.getPostazione().getCodice() +
                                " - Tipo: " + p.getPostazione().getTipo() +
                                " - Città: " + p.getPostazione().getEdificio().getCittà()
                ));
            }
        } catch (RisorsaNonTrovataException e) {
            System.out.println("Utente non trovato: " + e.getMessage());
        }
    }

    private void cancellaPrenotazione(Scanner scanner) {
        try {
            System.out.print("Inserisci ID prenotazione da cancellare: ");
            Long prenotazioneId = scanner.nextLong();

            prenotazioneService.cancellaPrenotazione(prenotazioneId);
            System.out.println("Prenotazione cancellata con successo!");
        } catch (RisorsaNonTrovataException e) {
            System.out.println("Prenotazione non trovata: " + e.getMessage());
        }
    }
}