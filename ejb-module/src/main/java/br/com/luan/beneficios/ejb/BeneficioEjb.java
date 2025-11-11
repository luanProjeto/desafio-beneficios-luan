package br.com.luan.beneficios.ejb;

import jakarta.ejb.Remote;
import java.math.BigDecimal;

@Remote
public interface BeneficioEjb {
    void transferir(Long origemId, Long destinoId, BigDecimal valor);
}
