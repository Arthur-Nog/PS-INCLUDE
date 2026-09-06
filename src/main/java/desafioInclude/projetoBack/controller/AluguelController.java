package desafioInclude.projetoBack.controller;

import desafioInclude.projetoBack.dto.request.AluguelRequestDTO;
import desafioInclude.projetoBack.dto.response.AluguelResponseDTO;
import desafioInclude.projetoBack.entity.enums.StatusAluguel;
import desafioInclude.projetoBack.service.AluguelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aluguel")
@Validated
@Tag(name = "Aluguel", description = "Operações de controle dos aluguéis")
public class AluguelController {

    private final AluguelService aluguelService;

    public AluguelController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }

    @Operation(summary = "Cadastro aluguel", description = "Cadastro de novos alugueis")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AluguelResponseDTO alugarVeiculo(@RequestBody @Valid AluguelRequestDTO aluguelRequest){
        return aluguelService.alugarVeiculo(aluguelRequest);
    }

    @Operation(summary = "Encerrar aluguel", description = "Encerrar aluguel do veículo")
    @PostMapping("/{id}/encerrar")
    @ResponseStatus(HttpStatus.OK)
    public void cadastrarDevolucao(@PathVariable @Valid Long id){
        aluguelService.cadastrarDevolucao(id);
    }

    @Operation(summary = "Buscar aluguel", description = "Buscar aluguel por status")
    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public List<AluguelResponseDTO> buscarAlugueisStatus(@RequestParam StatusAluguel status){
        return aluguelService.buscarAluguelStatus(status);
    }

}
