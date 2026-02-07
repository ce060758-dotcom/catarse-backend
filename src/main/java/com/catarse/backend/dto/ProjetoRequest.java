package com.catarse.backend.dto;

import com.catarse.backend.model.Projeto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    private List<String> tags;

    private String imagemUrl;

    // Getters e Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public BigDecimal getMetaFinanceira() { return metaFinanceira; }
    public void setMetaFinanceira(BigDecimal metaFinanceira) { this.metaFinanceira = metaFinanceira; }

    public LocalDate getDataLimite() { return dataLimite; }
    public void setDataLimite(LocalDate dataLimite) { this.dataLimite = dataLimite; }

    public String getCriadorId() { return criadorId; }
    public void setCriadorId(String criadorId) { this.criadorId = criadorId; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }

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