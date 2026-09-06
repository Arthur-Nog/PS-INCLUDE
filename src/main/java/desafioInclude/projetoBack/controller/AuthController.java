package desafioInclude.projetoBack.controller;

import desafioInclude.projetoBack.dto.request.LoginRequestDTO;
import desafioInclude.projetoBack.dto.response.LoginResponseDTO;
import desafioInclude.projetoBack.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Autenticação para ter acesso ao sistema")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Autenticação para acesso")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDTO login(@RequestBody @Valid LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.login(), request.senha())
        );
        String token = tokenService.gerarToken(request.login());
        return new LoginResponseDTO(token);
    }
}
