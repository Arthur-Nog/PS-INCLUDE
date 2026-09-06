package desafioInclude.projetoBack.dto.response;

import desafioInclude.projetoBack.entity.enums.StatusAluguel;

import java.time.LocalDate;

public record AluguelResponseDTO(

        Long id,
        String nomeCliente,
        String contato,
        String nomeVeiculo,
        String placaVeiculo,
        LocalDate inicio,
        LocalDate fim,
        Long diasContratados,
        Double valorFinal

)
{}
