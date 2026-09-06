package desafioInclude.projetoBack.dto.request;


import jakarta.validation.constraints.*;

public record VeiculoRequestDTO(

         @NotBlank(message = "Marca do veiculo é obrigatória!")
         String marca,

         @NotBlank(message = "Modelo do veiculo é obrigatória!")
         String modelo,

         @NotNull(message = "O ano do veículo é obrigatório!")
         @Positive(message = "O ano deve ser positivo!")
         Long ano,

         @NotNull(message = "A placa do veiculo é obrigatória!")
         @Pattern(
                 regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$",
                 message = "A placa deve seguir o padrão brasileiro (antigo ou Mercosul) em letras maiúsculas e sem traço (ex: ABC1D23 ou ABC1234)")
         String placa,

         @NotNull(message = "O valor da diária é obrigatória!")
         @Positive(message = "O valor deve ser positivo!")
         Double diaria
)
{}
