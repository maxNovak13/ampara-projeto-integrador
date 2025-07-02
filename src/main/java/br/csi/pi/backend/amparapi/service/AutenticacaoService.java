package br.csi.pi.backend.amparapi.service;

import br.csi.pi.backend.amparapi.model.profissional.Profissional;
import br.csi.pi.backend.amparapi.model.profissional.ProfissionalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService  {
    private final ProfissionalRepository profissionalRepository;

    public AutenticacaoService(ProfissionalRepository profissionalRepository, PasswordEncoder passwordEncoder) {
        this.profissionalRepository = profissionalRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Profissional profissional = profissionalRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email ou senha incorretos"));

        return User.withUsername(profissional.getEmail())
                .password(profissional.getSenha())
                .authorities(profissional.getRole().name())
                .build();
    }
}
