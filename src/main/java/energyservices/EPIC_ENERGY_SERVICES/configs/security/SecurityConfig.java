package energyservices.EPIC_ENERGY_SERVICES.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain secFilChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(form -> form.disable());
        httpSecurity.csrf(cs -> cs.disable());
        httpSecurity.sessionManagement(se -> se.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.authorizeHttpRequests(req -> req.requestMatchers("/**").permitAll());
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder getCryot() {
        return new BCryptPasswordEncoder(13);
    }
}