package energyservices.EPIC_ENERGY_SERVICES.runners;

import energyservices.EPIC_ENERGY_SERVICES.entities.Ruoli;
import energyservices.EPIC_ENERGY_SERVICES.entities.Stato;
import energyservices.EPIC_ENERGY_SERVICES.entities.Utente;
import energyservices.EPIC_ENERGY_SERVICES.importazione.CsvImportService;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.NuovoClientePayload;
import energyservices.EPIC_ENERGY_SERVICES.payloads.requests.RegisterUtentePayload;
import energyservices.EPIC_ENERGY_SERVICES.repositories.*;
import energyservices.EPIC_ENERGY_SERVICES.services.AuthService;
import energyservices.EPIC_ENERGY_SERVICES.services.ClienteService;
import energyservices.EPIC_ENERGY_SERVICES.services.FatturaService;
import energyservices.EPIC_ENERGY_SERVICES.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class AnagraficaCentralizzata implements CommandLineRunner {
    @Autowired
    RuoliRepo ruoliRepo;
    @Autowired
    private CsvImportService importService;
    @Autowired
    private ProvinciaRepo provRepo;
    @Autowired
    private ComuneRepo comRepo;
    @Autowired
    private AuthService authService;
    @Autowired
    private UtenteRepo utenteRepo;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ClienteRepo clienteRepo;
    @Autowired
    private StatoRepo statoRepo;
    @Autowired
    private FatturaService fatturaService;
    @Autowired
    private FatturaRepo fatturaRepo;
    @Autowired
    private MailService mailService;


    @Override
    public void run(String... args) throws Exception {
        Path provincePath = Paths.get("src/province-italiane.csv");
        Path comunePath = Paths.get("src/comuni-italiani.csv");

        if (provRepo.findAll().isEmpty()) {
            importService.salvaProvince(provincePath);
            System.out.println("tutto bene");
        }
        if (comRepo.findAll().isEmpty()) {
            importService.salvaComuni(comunePath);
            System.out.println("tutto bene comuni");
        }

        Ruoli user = new Ruoli("User");
        Ruoli admin = new Ruoli("Admin");
        if (ruoliRepo.findAll().isEmpty()) {
            ruoliRepo.save(user);
            ruoliRepo.save(admin);
            System.out.println("stati salvati");
        }

        List<Utente> uts = utenteRepo.findAll();
        int c = 0;
        if (!uts.isEmpty()) {

            for (int i = 0; i < uts.size(); i++) {
                if (uts.get(i).getRuolo().getNomeRuolo().equals("Admin")) {
                    c++;
                }
            }

        }
        if (c <= 0) {
            RegisterUtentePayload ad = new RegisterUtentePayload("admin", "admin@gmial.com", bcrypt.encode("1234"), "Thomas", "Galbignani");
            authService.salvaAdmin(ad);
        }

        NuovoClientePayload nuovoClientePayload = new NuovoClientePayload("SPA", "02108937465", "email@emailtest.com", "pippero@pippero.it", "3518867764", "pippero@azienda.it", "franco", "spesso", "027873648", "g.rossi", "21", "02122", "Bressanone");
        if (clienteRepo.findAll().isEmpty()) {
            clienteService.clienteSave(nuovoClientePayload);
        }

        Stato daPagare = new Stato("da pagare");
        Stato pagata = new Stato("pagata");
        if (statoRepo.findAll().isEmpty()) {
            statoRepo.save(daPagare);
            statoRepo.save(pagata);
        } else if (statoRepo.findAll().size() < 2) {
            statoRepo.save(pagata);
        }


        //  NewFatturaDTO fattura = new NewFatturaDTO(123, "b3ccb3c5-5c59-408f-a574-3cfb651d14f3");
        //fatturaService.salvaFattura(fattura);


    }


}
