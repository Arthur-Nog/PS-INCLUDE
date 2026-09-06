package desafioInclude.projetoBack.entity;

import desafioInclude.projetoBack.entity.enums.Disponibilidade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_veiculo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Veiculo {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String marca;
        private String modelo;
        private Long ano;
        private String placa;
        private Double diaria;

        @Enumerated(EnumType.STRING)
        private Disponibilidade disponibilidade = Disponibilidade.DISPONIVEL;


}
