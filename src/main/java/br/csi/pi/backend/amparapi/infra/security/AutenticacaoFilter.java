package br.csi.pi.backend.amparapi.infra.security;

import br.csi.pi.backend.amparapi.model.profissional.Profissional;
import br.csi.pi.backend.amparapi.service.AutenticacaoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AutenticacaoFilter extends OncePerRequestFilter {

    private final TokenServiceJWT tokenServiceJWT;
    private final AutenticacaoService autenticacaoService;

    public AutenticacaoFilter(@Lazy AutenticacaoService autenticacaoService, TokenServiceJWT tokenServiceJWT) {
        this.tokenServiceJWT = tokenServiceJWT;
        this.autenticacaoService = autenticacaoService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String tokenJWT = recuperarToken(request);
        if (tokenJWT != null) {
            String subject = this.tokenServiceJWT.getSubject(tokenJWT);

            ///UserDetails userDetails = this.autenticacaoService.loadUserByUsername(subject);
            Profissional profissional = (Profissional) this.autenticacaoService.loadUserByUsername(subject);

            System.out.println("[TOKEN OK] Usuário autenticado: " + subject);
            System.out.println("[TOKEN OK] Authorities: " + profissional.getAuthorities());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(profissional, null, profissional.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }


    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.toLowerCase().startsWith("bearer ")) {

            return token.replaceFirst("(?i)^bearer\\s+", "");
        }
        return null;
    }
}
