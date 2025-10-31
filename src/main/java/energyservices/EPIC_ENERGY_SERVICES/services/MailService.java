package energyservices.EPIC_ENERGY_SERVICES.services;

import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import energyservices.EPIC_ENERGY_SERVICES.tools.MailgunSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MailService {
    @Autowired
    private MailgunSender mailgunSender;
    @Autowired
    private ClienteService clienteService;

    public String inviaMail(UUID id, String og, String mes) {
        Cliente c = clienteService.findById(id);
        mailgunSender.sendRegistrationEmail(c, og, mes);
        return mes;
    }
}
