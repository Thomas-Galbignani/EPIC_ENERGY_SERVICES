package energyservices.EPIC_ENERGY_SERVICES.payloads.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class JwtAuthResponse {
    private String token;

    public JwtAuthResponse(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }
}
