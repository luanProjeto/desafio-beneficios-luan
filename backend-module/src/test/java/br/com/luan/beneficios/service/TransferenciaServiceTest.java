package br.com.luan.beneficios.service;

import br.com.luan.beneficios.domain.Beneficio;
import br.com.luan.beneficios.repo.BeneficioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class TransferenciaServiceTest {

    @Test
    void deveFalharSeSaldoInsuficiente() {
        BeneficioRepository repo = Mockito.mock(BeneficioRepository.class);
        TransferenciaService service = new TransferenciaService(repo, "");

        Beneficio origem = new Beneficio();
        origem.setId(1L);
        origem.setSaldo(new BigDecimal("100"));

        Beneficio destino = new Beneficio();
        destino.setId(2L);
        destino.setSaldo(new BigDecimal("50"));

        when(repo.findById(1L)).thenReturn(Optional.of(origem));
        when(repo.findById(2L)).thenReturn(Optional.of(destino));

        assertThrows(IllegalArgumentException.class,
                () -> service.transferir(1L, 2L, new BigDecimal("200")));
    }
}
