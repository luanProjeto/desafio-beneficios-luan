package br.com.luan.beneficios.web;

import br.com.luan.beneficios.domain.Beneficio;
import br.com.luan.beneficios.repo.BeneficioRepository;
import br.com.luan.beneficios.service.TransferenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<Beneficio> criar(@RequestBody Beneficio b) {
        Beneficio saved = repo.save(b);
        return ResponseEntity.created(URI.create("/api/beneficios/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public Beneficio atualizar(@PathVariable Long id, @RequestBody Beneficio b) {
        b.setId(id);
        return repo.save(b);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Void> transferir(@RequestBody TransferenciaDTO dto) {
        transferenciaService.transferir(dto.getOrigemId(), dto.getDestinoId(), dto.getValor());
        return ResponseEntity.ok().build();
    }
}
