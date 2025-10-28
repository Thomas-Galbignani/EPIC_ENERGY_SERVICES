package energyservices.EPIC_ENERGY_SERVICES.importazione;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import energyservices.EPIC_ENERGY_SERVICES.entities.Comune;
import energyservices.EPIC_ENERGY_SERVICES.entities.Provincia;
import energyservices.EPIC_ENERGY_SERVICES.repositories.ComuneRepo;
import energyservices.EPIC_ENERGY_SERVICES.repositories.ProvinciaRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CsvImportService {
    private ComuneRepo comuneRepo;
    private ProvinciaRepo provinciaRepo;

    public CsvImportService(ComuneRepo comuneRepo, ProvinciaRepo provinciaRepo) {
        this.comuneRepo = comuneRepo;
        this.provinciaRepo = provinciaRepo;
    }

    @Transactional
    public void salvaProvince(Path pathFile) throws IOException, CsvValidationException {
        try (CSVReader reader = new CSVReader(new FileReader(pathFile.toFile()))) {
            String[] nextLine;
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                String sigla = nextLine[0].split(";")[0];
                String provincia = nextLine[0].split(";")[1];
                String regione = nextLine[0].split(";")[2];

                switch (provincia) {
                    case "Verbania":
                        provincia = "Verbano-Cusio-Ossola";
                        break;
                    case "Aosta":
                        provincia = "Valle d'Aosta/Vallée d'Aoste";
                        break;
                    case "Monza-Brianza":
                        provincia = "Monza e della Brianza";
                        break;
                    case "Bolzano":
                        provincia = "Bolzano/Bozen";
                        break;
                    case "La-Spezia":
                        provincia = "La Spezia";
                        break;
                    case "Pesaro-Urbino":
                        provincia = "Pesaro e Urbino";
                        break;
                    case "Ascoli-Piceno":
                        provincia = "Ascoli Piceno";
                        break;
                    case "Reggio-Calabria":
                        provincia = "Reggio Calabria";
                        break;
                    case "Vibo-Valentia":
                        provincia = "Vibo Valentia";
                        break;
                    case "Reggio-Emilia":
                        provincia = "Reggio nell'Emilia";
                        break;
                    case "Forli-Cesena":
                        provincia = "Forlì-Cesena";
                        break;

                }

                Provincia p = new Provincia(provincia, sigla, regione);
                provinciaRepo.save(p);
            }

            Provincia p2 = new Provincia("Sud Sardegna", "SU", "Sardegna");
            provinciaRepo.save(p2);


        }
    }

    @Transactional
    public void salvaComuni(Path filePath) throws IOException, CsvValidationException, Exception {
        try (CSVReader reader = new CSVReader(new FileReader(filePath.toFile()))) {
            String[] nextLine;
            reader.readNext();
            int count = 1;
            while ((nextLine = reader.readNext()) != null) {

                long codProvincia = Long.parseLong(nextLine[0].split(";")[0]);
                String progComuneStr = nextLine[0].split(";")[1];
                long progCom = 0;
                if (progComuneStr.equals("#RIF!")) {
                    progCom = count;
                    count++;
                } else {
                    progCom = Long.parseLong(progComuneStr);
                }
                String nomeProv = nextLine[0].split(";")[3];

                /*
                if (!foundProv.isPresent()) {
                    // throw new RuntimeException("nome provincia non valido");
                    switch (nomeProv) {
                        case "Verbano-Cusio-Ossola":
                            nomeProv = "Verbania";
                            break;
                        case "Valle d'Aosta/Vallée d'Aoste":
                            nomeProv = "Aosta";
                            break;
                        case "Monza e della Brianza":
                            nomeProv = "Monza-Brianza";
                            break;
                        case "Bolzano/Bozen":
                            nomeProv = "Bolzano";
                            break;
                        case "La Spezia":
                            nomeProv = "La-Spezia";
                            break;
                        case "Pesaro e Urbino":
                            nomeProv = "Pesaro-Urbino";
                        case "Ascoli Piceno":
                            nomeProv = "Ascoli-Piceno";
                            break;
                        case "Reggio Calabria":
                            nomeProv = "Reggio-Calabria";
                            break;
                        case "Vibo Valentia":
                            nomeProv = "Vibo-Valentia";
                            break;
                        case "Reggio nell'Emilia":
                            nomeProv = "Reggio-Emilia";
                            break;
                        case "Forlì-Cesena":
                            nomeProv = "Forli-Cesena";
                            break;
                        case "Sud Sardegna":
                            nomeProv = "Cagliari";
                            break;
                    }
                } */
                Optional<Provincia> foundProv = provinciaRepo.findById(nomeProv);
                if (foundProv.isPresent()) {
                    Provincia prov = foundProv.get();
                    String denom = nextLine[0].split(";")[2];

                    Comune c = new Comune(denom, progCom, codProvincia, prov);
                    comuneRepo.save(c);
                } else {
                    throw new RuntimeException(nomeProv);
                }


            }
        }
    }
}
