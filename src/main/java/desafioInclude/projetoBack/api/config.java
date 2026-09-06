package desafioInclude.projetoBack.api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class config {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API para gestão de aluguel de veículos")
                        .description("Documentação do projeto BackEnd para desafio da INCLUDE."));
    }
}

