package desafioInclude.projetoBack.service;

import desafioInclude.projetoBack.dto.request.VeiculoRequestDTO;
import desafioInclude.projetoBack.dto.response.VeiculoResponseDTO;
import desafioInclude.projetoBack.entity.Veiculo;
import desafioInclude.projetoBack.exception.BusinessRuleException;
import desafioInclude.projetoBack.exception.ResourceNotFoundException;
import desafioInclude.projetoBack.repository.VeiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    //Função  para transformar em um Response
    public VeiculoResponseDTO toResponse(Veiculo veiculo){

        return new VeiculoResponseDTO(
                veiculo.getId(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getAno(),
                veiculo.getPlaca(),
                veiculo.getDiaria(),
                veiculo.getDisponibilidade());
    }


    //Cadastro de veículo
    public VeiculoResponseDTO cadastroVeiculo(VeiculoRequestDTO veiculoRequestDTO){

        if (veiculoRepository.existsByPlaca(veiculoRequestDTO.placa())){
             throw new BusinessRuleException("Veículo já existe na base de dados!");
         }

        Veiculo veiculoNovo = new Veiculo();

        veiculoNovo.setMarca(veiculoRequestDTO.marca());
        veiculoNovo.setModelo(veiculoRequestDTO.modelo());
        veiculoNovo.setAno(veiculoRequestDTO.ano());
        veiculoNovo.setPlaca(veiculoRequestDTO.placa());
        veiculoNovo.setDiaria(veiculoRequestDTO.diaria());

        Veiculo veiculoSalvo = veiculoRepository.save(veiculoNovo);
        return toResponse(veiculoSalvo);
    }

    //Edição de veículo

    public VeiculoResponseDTO alterarVeiculo(Long id,VeiculoRequestDTO veiculoRequest){
        Veiculo veiculo = veiculoRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Veículo não encontrado!"));

        veiculo.setMarca(veiculoRequest.marca());
        veiculo.setModelo(veiculoRequest.modelo());
        veiculo.setAno(veiculoRequest.ano());
        veiculo.setPlaca(veiculoRequest.placa());
        veiculo.setDiaria(veiculoRequest.diaria());

        Veiculo veiculoAtualizado = veiculoRepository.save(veiculo);
        return toResponse(veiculoAtualizado);
    }

    //Remoção de veículo

    public void removeVeiculo(Long id){
        if(!veiculoRepository.existsById(id)){
            throw new BusinessRuleException("O veículo não existe");
        }
        veiculoRepository.deleteById(id);
    }

    //Listar todos os veículos

    @Transactional(readOnly = true)
    public List<VeiculoResponseDTO> listarVeiculos(){
        List<Veiculo> veiculos = veiculoRepository.findAll();
        if (veiculos.isEmpty()){
            throw new ResourceNotFoundException("Não existem veículos cadastrados na base de dados!");
        }

        return veiculos
                .stream()
                .map(this :: toResponse)
                .toList();
    }


    //Buscar por id

    public VeiculoResponseDTO buscarPorId(Long id){
        return toResponse(veiculoRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("O veículo não consta na base de dados!")));
    }

    //Buscar por placa

    public VeiculoResponseDTO buscarPorPlaca(String placa){
        return toResponse(veiculoRepository.findByPlaca(placa).orElseThrow(()
                -> new ResourceNotFoundException("O veículo não consta na base de dados!")));
    }
}
