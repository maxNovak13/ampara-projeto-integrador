package br.csi.pi.backend.amparapi.infra.security;

import br.csi.pi.backend.amparapi.model.profissional.Profissional;
import br.csi.pi.backend.amparapi.model.profissional.ProfissionalRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServiceJWT {

    private final ProfissionalRepository profissionalRepository;

    public TokenServiceJWT(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }


    public String gerarToken(User user) {
        try{
            Profissional profissional = profissionalRepository.findByEmail(user.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Profissional não encontrado"));

            Algorithm algorithm = Algorithm.HMAC256("PI");
            return JWT.create()
                    .withIssuer("API Ampara")
                    .withSubject(user.getUsername())
                    .withClaim("ROLE", user.getAuthorities().stream().toList().get(0).toString())
                    .withClaim("uuid", profissional.getUuid().toString())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token", e);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256("PI");
            return JWT.require(algorithm)
                    .withIssuer("API Ampara")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }
}