package desafioInclude.projetoBack.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record AluguelRequestDTO(

        @NotBlank(message = "Nome do cliente é obrigatório!")
        String nomeCliente,

        @NotBlank(message = "O CPF do cliente é obrigatório")
        @CPF(message = "CPF inválido")
        String cpf,

        @NotBlank(message = "O contato é obrigatório!")
        String contato,

        @NotBlank(message = "O endereço é obrigatório!")
        String endereco,

        @NotNull(message = "A placa do veiculo é obrigatória!")
        @Pattern(
                regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$",
                message = "A placa deve seguir o padrão brasileiro (antigo ou Mercosul)")
        String placaVeiculo,

        LocalDate inicio,
        LocalDate fim,
        Long diasContratados
)
{}
