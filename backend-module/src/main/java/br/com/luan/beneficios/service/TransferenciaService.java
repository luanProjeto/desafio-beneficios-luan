package br.com.luan.beneficios.service;

import br.com.luan.beneficios.domain.Beneficio;
import br.com.luan.beneficios.repo.BeneficioRepository;
import br.com.luan.beneficios.ejb.BeneficioEjbClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransferenciaService {

    private final BeneficioRepository repo;
    private final Optional<BeneficioEjbClient> ejbClient;

    public TransferenciaService(BeneficioRepository repo,
                                @Value("${app.ejb.jndi:}") String jndi) {
        this.repo = repo;
        this.ejbClient = (jndi == null || jndi.isBlank())
                ? Optional.empty()
                : Optional.of(new BeneficioEjbClient(jndi));
    }

    @Transactional
    public void transferir(Long origemId, Long destinoId, BigDecimal valor) {
        if (ejbClient.isPresent()) {
            ejbClient.get().transferir(origemId, destinoId, valor);
            return;
        }
        // Fallback local (apenas para DEV)
        Beneficio origem = repo.findById(origemId).orElseThrow(() -> new IllegalArgumentException("Origem não encontrada"));
        Beneficio destino = repo.findById(destinoId).orElseThrow(() -> new IllegalArgumentException("Destino não encontrada"));
        if (valor == null || valor.signum() <= 0) throw new IllegalArgumentException("Valor inválido");
        if (origem.getSaldo().compareTo(valor) < 0) throw new IllegalArgumentException("Saldo insuficiente");

        origem.setSaldo(origem.getSaldo().subtract(valor));
        destino.setSaldo(destino.getSaldo().add(valor));
        repo.save(origem);
        repo.save(destino);
    }
}
