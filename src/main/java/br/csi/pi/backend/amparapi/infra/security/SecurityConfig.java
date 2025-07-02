package br.csi.pi.backend.amparapi.infra.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    AutenticacaoFilter autenticacaoFilter;


    public SecurityConfig( AutenticacaoFilter filtro) {
        this.autenticacaoFilter = filtro;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf-> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/profissional").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/profissional/uuid/*").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PATCH, "/profissional/*/boolean").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/profissional/*/role").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/profissional/listar").hasAuthority("ADMIN")
                        .requestMatchers("/local", "/local/listar").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/local/buscar").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(
                                "/ampara/swagger-ui.html",
                                "/swagger-ui/**",
                                "/api-docs/**"
                        ).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(this.autenticacaoFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
