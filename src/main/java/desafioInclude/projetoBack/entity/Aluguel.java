package desafioInclude.projetoBack.entity;

import desafioInclude.projetoBack.entity.enums.StatusAluguel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "tb_aluguel")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusAluguel statusAluguel;

    private String nomeCliente;
    private String cpf;
    private String contato;
    private String endereco;

    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    private LocalDate inicio;
    private LocalDate fim;
    private Long diasContratados;


}
