package energyservices.EPIC_ENERGY_SERVICES.runners;

import energyservices.EPIC_ENERGY_SERVICES.entities.Ruoli;
import energyservices.EPIC_ENERGY_SERVICES.importazione.CsvImportService;
import energyservices.EPIC_ENERGY_SERVICES.repositories.ComuneRepo;
import energyservices.EPIC_ENERGY_SERVICES.repositories.ProvinciaRepo;
import energyservices.EPIC_ENERGY_SERVICES.repositories.RuoliRepo;
import energyservices.EPIC_ENERGY_SERVICES.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

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
        }


    }
}
