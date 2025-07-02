package br.csi.pi.backend.amparapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Ampara API",
                version = "1.0",
                description = "Documentação da API do sistema Ampara.",
                contact = @Contact(name = "Equipe Ampara", email = "suporte@ampara.org")
        ),
        servers = {
                @Server(url = "/ampara", description = "Servidor local com contexto /ampara")
        }
)
public class OpenAPIConfig {
}
