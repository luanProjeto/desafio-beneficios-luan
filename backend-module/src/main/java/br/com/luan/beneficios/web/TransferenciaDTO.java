package br.com.luan.beneficios.web;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Dados de uma transferência entre benefícios")
public class TransferenciaDTO {

    @NotNull(message = "{TransferenciaDTO.origemId.NotNull}")
    @Schema(example = "1", description = "ID do benefício de origem")
    private Long origemId;

    @NotNull(message = "{TransferenciaDTO.destinoId.NotNull}")
    @Schema(example = "2", description = "ID do benefício de destino")
    private Long destinoId;

    @NotNull(message = "{TransferenciaDTO.valor.NotNull}")
    @Positive(message = "{TransferenciaDTO.valor.Positive}")
    @Schema(example = "150.00", description = "Valor da transferência (maior que zero)")
    private BigDecimal valor;

    public Long getOrigemId() { return origemId; }
    public void setOrigemId(Long origemId) { this.origemId = origemId; }

    public Long getDestinoId() { return destinoId; }
    public void setDestinoId(Long destinoId) { this.destinoId = destinoId; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
}
