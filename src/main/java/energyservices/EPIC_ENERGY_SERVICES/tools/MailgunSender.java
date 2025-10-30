package energyservices.EPIC_ENERGY_SERVICES.tools;


import energyservices.EPIC_ENERGY_SERVICES.entities.Cliente;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailgunSender {
    private String domain;
    private String apiKey;

    public MailgunSender(@Value("${mailgun.domain}") String domain, @Value("${mailgun.apiKey}") String apiKey) {
        this.domain = domain;
        this.apiKey = apiKey;
    }

    public void sendRegistrationEmail(Cliente recipient, String og, String mes) {

        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domain + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", "Mailgun Sandbox <postmaster@sandboxa972eb2a3a6d4cb284dac2d2f1bd68c9.mailgun.org>")//in caso cambiare la mail.
                .queryString("to", recipient.getEmail()) // Qua potrà esserci solo uno degli indirizzi autorizzati precedentemente sulla dashboard di Mailgun
                .queryString("subject", og)
                .queryString("text", "Ciao, " + recipient.getNomeContatto() + " " + recipient.getCognomeContatto() + mes)
                .asJson();
        System.out.println(response.getBody()); // <-- Consiglio questo log per debuggare eventuali problemi
    }

    public void sendBillingEmail(Cliente recipient) {
    }

}
