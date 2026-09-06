package desafioInclude.projetoBack.service;

import desafioInclude.projetoBack.dto.request.AluguelRequestDTO;
import desafioInclude.projetoBack.dto.response.AluguelResponseDTO;
import desafioInclude.projetoBack.entity.Aluguel;
import desafioInclude.projetoBack.entity.Veiculo;
import desafioInclude.projetoBack.entity.enums.Disponibilidade;
import desafioInclude.projetoBack.entity.enums.StatusAluguel;
import desafioInclude.projetoBack.exception.BusinessRuleException;
import desafioInclude.projetoBack.exception.ResourceNotFoundException;
import desafioInclude.projetoBack.repository.AluguelRepository;
import desafioInclude.projetoBack.repository.VeiculoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AluguelService {

    private final VeiculoRepository veiculoRepository;
    private final AluguelRepository aluguelRepository;

    public AluguelService(VeiculoRepository veiculoRepository, AluguelRepository aluguelRepository) {
        this.veiculoRepository = veiculoRepository;
        this.aluguelRepository = aluguelRepository;
    }

    public AluguelResponseDTO toResponse(Aluguel aluguel){
        Double valorFinal = aluguel.getVeiculo().getDiaria() * aluguel.getDiasContratados();

        return new AluguelResponseDTO(aluguel.getId(),aluguel.getNomeCliente(),aluguel.getContato(),
                aluguel.getVeiculo().getModelo(),aluguel.getVeiculo().getPlaca(),aluguel.getInicio(),
                aluguel.getFim(),aluguel.getDiasContratados(),valorFinal);
    }

    @Transactional
    public AluguelResponseDTO alugarVeiculo(AluguelRequestDTO aluguelRequestDTO){

        Veiculo veiculo = veiculoRepository.findByPlacaForUpdate(aluguelRequestDTO.placaVeiculo()).orElseThrow(()
                -> new ResourceNotFoundException("O veiculo não consta na base de dados"));

        if (Disponibilidade.ALUGADO == veiculo.getDisponibilidade()){
            throw new BusinessRuleException("O veículo já está alugado!");
        }

        Aluguel aluguel = new Aluguel();

        veiculo.setDisponibilidade(Disponibilidade.ALUGADO);
        aluguel.setStatusAluguel(StatusAluguel.ATIVO);
        aluguel.setVeiculo(veiculo);

        Long diasAlugados = ChronoUnit.DAYS.between(aluguelRequestDTO.inicio(),aluguelRequestDTO.fim());

        aluguel.setNomeCliente(aluguelRequestDTO.nomeCliente());
        aluguel.setCpf(aluguelRequestDTO.cpf());
        aluguel.setContato(aluguelRequestDTO.contato());
        aluguel.setEndereco(aluguelRequestDTO.endereco());
        aluguel.setInicio(aluguelRequestDTO.inicio());
        aluguel.setFim(aluguelRequestDTO.fim());
        aluguel.setDiasContratados(diasAlugados);


        Aluguel aluguelSalvo = aluguelRepository.save(aluguel);

        return toResponse(aluguelSalvo);
    }

    @Transactional
    public void cadastrarDevolucao(Long id) {
        Aluguel aluguel = aluguelRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Esse aluguel não existe na base de dados"));

        Veiculo veiculo = veiculoRepository.findByPlacaForUpdate(aluguel.getVeiculo().getPlaca()).orElseThrow(()
                -> new ResourceNotFoundException("Esse veículo não existe na nossa base de dados!"));

        if (aluguel.getStatusAluguel() == StatusAluguel.ENCERRADO){
            throw new BusinessRuleException("O aluguel já está encerrado!");
        }

        aluguel.setStatusAluguel(StatusAluguel.ENCERRADO);
        veiculo.setDisponibilidade(Disponibilidade.DISPONIVEL);
    }

    public List<AluguelResponseDTO> buscarAluguelStatus(StatusAluguel status){

        List<Aluguel> alugueis = aluguelRepository.findByStatusAluguel(status);
        if(alugueis.isEmpty()){
            throw new ResourceNotFoundException("Sem alugueis cadastrados!!");
        }

        return alugueis
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
