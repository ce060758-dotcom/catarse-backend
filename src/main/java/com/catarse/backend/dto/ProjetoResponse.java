package com.catarse.backend.dto;

import com.catarse.backend.model.Projeto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoResponse {

    private String id;
    private String titulo;
    private String descricao;
    private String categoria;
    private BigDecimal metaFinanceira;
    private BigDecimal valorArrecadado;
    private LocalDate dataLimite;
    private String criadorId;
    private List<String> tags;
    private Projeto.StatusProjeto status;
    private String imagemUrl;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public static ProjetoResponse fromEntity(Projeto projeto) {
        return ProjetoResponse.builder()
                .id(projeto.getId())
                .titulo(projeto.getTitulo())
                .descricao(projeto.getDescricao())
                .categoria(projeto.getCategoria())
                .metaFinanceira(projeto.getMetaFinanceira())
                .valorArrecadado(projeto.getValorArrecadado())
                .dataLimite(projeto.getDataLimite())
                .criadorId(projeto.getCriadorId())
                .tags(projeto.getTags())
                .status(projeto.getStatus())
                .imagemUrl(projeto.getImagemUrl())
                .dataCriacao(projeto.getDataCriacao())
                .dataAtualizacao(projeto.getDataAtualizacao())
                .build();
    }
}