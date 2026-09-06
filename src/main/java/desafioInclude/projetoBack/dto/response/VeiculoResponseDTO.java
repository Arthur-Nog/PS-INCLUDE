package desafioInclude.projetoBack.dto.response;

import desafioInclude.projetoBack.entity.enums.Disponibilidade;

public record VeiculoResponseDTO(

        Long id,
        String marca,
        String modelo,
        Long ano,
        String placa,
        Double diaria,
        Disponibilidade disponibilidade
) {
}
