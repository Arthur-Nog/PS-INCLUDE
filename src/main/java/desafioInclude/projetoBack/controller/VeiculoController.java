package desafioInclude.projetoBack.controller;

import desafioInclude.projetoBack.dto.request.VeiculoRequestDTO;
import desafioInclude.projetoBack.dto.response.VeiculoResponseDTO;
import desafioInclude.projetoBack.service.AluguelService;
import desafioInclude.projetoBack.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/veiculo")
@Validated
@Tag(name = "Veículo", description = "Operações para gerenciamento de veículos")
public class VeiculoController {

    private final VeiculoService veiculoService;
    private final AluguelService aluguelService;

    public VeiculoController(VeiculoService veiculoService, AluguelService aluguelService) {
        this.veiculoService = veiculoService;
        this.aluguelService = aluguelService;
    }

    //Add veiculo

    @Operation(summary = "Criação de novo veículo", description = "Cria um novo veículo na base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoResponseDTO cadastrarVeiculo(@RequestBody @Valid VeiculoRequestDTO requestDTO){
        return veiculoService.cadastroVeiculo(requestDTO);
    }

    //Alterar veiculo

    @Operation(summary = "Alterar veículo", description = "Altera informações do veículo")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VeiculoResponseDTO editarVeiculo(@PathVariable Long id,@RequestBody @Valid VeiculoRequestDTO requestDTO){
        return veiculoService.alterarVeiculo(id,requestDTO);
    }
    //Remover veiculo

    @Operation(summary = "Deletar veículo", description = "Deleta o veículo da base de dados")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerVeiculo(@PathVariable Long id){
        veiculoService.removeVeiculo(id);
    }

    //Listar todos

    @Operation(summary = "Listar veículos", description = "Lista todos os veículos do sistema")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VeiculoResponseDTO> listarVeiculos(){
        return veiculoService.listarVeiculos();
    }


    //Buscar por placa
    @Operation(summary = "Buscar veículo por placa", description = "Buscar um veículo pela placa")
    @GetMapping("/placa")
    @ResponseStatus(HttpStatus.OK)
    public VeiculoResponseDTO buscarPlaca(@RequestParam String placa){
        return veiculoService.buscarPorPlaca(placa);
    }

    //Buscar por id
    @Operation(summary = "Buscar veículo por id", description = "Buscar um veículo pelo id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VeiculoResponseDTO buscarId(@PathVariable Long id){
        return veiculoService.buscarPorId(id);
    }




}
