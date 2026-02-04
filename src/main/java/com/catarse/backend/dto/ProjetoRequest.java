package com.catarse.backend.dto;

import com.catarse.backend.model.Projeto;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProjetoRequest {

    @NotBlank(message = "Título é obrigatório")
    @Size(min = 3, max = 100, message = "Título deve ter entre 3 e 100 caracteres")
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 10, max = 5000, message = "Descrição deve ter entre 10 e 5000 caracteres")
    private String descricao;

    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;

    @NotNull(message = "Meta financeira é obrigatória")
    @DecimalMin(value = "100.00", message = "Meta financeira mínima é R$ 100,00")
    private BigDecimal metaFinanceira;

    @NotNull(message = "Data limite é obrigatória")
    @Future(message = "Data limite deve ser futura")
    private LocalDate dataLimite;

    @NotBlank(message = "ID do criador é obrigatório")
    private String criadorId;

    private List<@NotBlank String> tags;

    private String imagemUrl;

    public Projeto toEntity() {
        return Projeto.builder()
                .titulo(this.titulo)
                .descricao(this.descricao)
                .categoria(this.categoria)
                .metaFinanceira(this.metaFinanceira)
                .valorArrecadado(BigDecimal.ZERO)
                .dataLimite(this.dataLimite)
                .criadorId(this.criadorId)
                .tags(this.tags)
                .imagemUrl(this.imagemUrl)
                .status(Projeto.StatusProjeto.RASCUNHO)
                .build();
    }
}