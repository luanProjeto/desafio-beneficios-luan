package br.com.luan.beneficios.ejb;

import br.com.luan.beneficios.domain.Beneficio;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;

@Stateless(name = "BeneficioEjb")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BeneficioEjbService implements BeneficioEjb {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void transferir(Long origemId, Long destinoId, BigDecimal valor) {
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException("Valor inválido.");
        }

        Beneficio origem = em.find(Beneficio.class, origemId, LockModeType.OPTIMISTIC);
        Beneficio destino = em.find(Beneficio.class, destinoId, LockModeType.OPTIMISTIC);

        if (origem == null || destino == null) {
            throw new IllegalArgumentException("Conta origem/destino não encontrada.");
        }

        if (origem.getSaldo().compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }

        origem.setSaldo(origem.getSaldo().subtract(valor));
        destino.setSaldo(destino.getSaldo().add(valor));

        em.merge(origem);
        em.merge(destino);
        // commit/rollback controlado pelo container via REQUIRED
    }
}
