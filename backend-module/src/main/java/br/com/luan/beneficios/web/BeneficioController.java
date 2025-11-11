package br.com.luan.beneficios.web;

import br.com.luan.beneficios.domain.Beneficio;
import br.com.luan.beneficios.repo.BeneficioRepository;
import br.com.luan.beneficios.service.TransferenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/beneficios")
public class BeneficioController {

    private final BeneficioRepository repo;
    private final TransferenciaService transferenciaService;

    public BeneficioController(BeneficioRepository repo, TransferenciaService transferenciaService) {
        this.repo = repo;
        this.transferenciaService = transferenciaService;
    }

    @GetMapping
    public List<Beneficio> listar() {
        return repo.findAll();
    }

    @PostMapping
    public ResponseEntity<Beneficio> criar(@Valid @RequestBody Beneficio b) {
        Beneficio saved = repo.save(b);
        return ResponseEntity.created(URI.create("/api/beneficios/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public Beneficio atualizar(@PathVariable Long id, @Valid @RequestBody Beneficio b) {
        b.setId(id);
        return repo.save(b);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transferencia")
    @Operation(summary = "Realiza transferência entre benefícios",
        requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = TransferenciaDTO.class))),
        responses = {
          @ApiResponse(responseCode = "200", description = "Transferência realizada"),
          @ApiResponse(responseCode = "400", description = "Erro de validação",
          content = @io.swagger.v3.oas.annotations.media.Content(
            schema = @io.swagger.v3.oas.annotations.media.Schema(example = """
            {
              \"timestamp\": \"2025-11-11T18:04:22.847658Z\",
              \"status\": 400,
              \"error\": \"Bad Request\",
              \"path\": \"/api/beneficios/transferencia\",
              \"fields\": {\"valor\": \"O valor da transferência deve ser maior que zero.\"}
            }
            """)
          )),
          @ApiResponse(responseCode = "404", description = "Origem/Destino não encontrados"),
          @ApiResponse(responseCode = "409", description = "Conflito de concorrência")
        })
    public ResponseEntity<Void> transferir(@Valid @RequestBody TransferenciaDTO dto) {
        transferenciaService.transferir(dto.getOrigemId(), dto.getDestinoId(), dto.getValor());
        return ResponseEntity.ok().build();
    }
}
