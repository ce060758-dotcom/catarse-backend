package com.catarse.backend.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document(collection = "projetos")
public class Projeto {

    @Id
    private String id;

    private String titulo;
    private String descricao;
    private String categoria;

    @Field("meta_financeira")
    private BigDecimal metaFinanceira;

    @Field("valor_arrecadado")
    private BigDecimal valorArrecadado;

    @Field("data_limite")
    private LocalDate dataLimite;

    @Field("criador_id")
    private String criadorId;

    private List<String> tags;
    private StatusProjeto status;

    @Field("imagem_url")
    private String imagemUrl;

    @CreatedDate
    @Field("data_criacao")
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    @Field("data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Construtores
    public Projeto() {
    }

    public Projeto(String id, String titulo, String descricao, String categoria,
                   BigDecimal metaFinanceira, BigDecimal valorArrecadado,
                   LocalDate dataLimite, String criadorId, List<String> tags,
                   StatusProjeto status, String imagemUrl,
                   LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.metaFinanceira = metaFinanceira;
        this.valorArrecadado = valorArrecadado;
        this.dataLimite = dataLimite;
        this.criadorId = criadorId;
        this.tags = tags;
        this.status = status;
        this.imagemUrl = imagemUrl;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    // Builder estático
    public static Builder builder() {
        return new Builder();
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public BigDecimal getMetaFinanceira() { return metaFinanceira; }
    public void setMetaFinanceira(BigDecimal metaFinanceira) { this.metaFinanceira = metaFinanceira; }

    public BigDecimal getValorArrecadado() { return valorArrecadado; }
    public void setValorArrecadado(BigDecimal valorArrecadado) { this.valorArrecadado = valorArrecadado; }

    public LocalDate getDataLimite() { return dataLimite; }
    public void setDataLimite(LocalDate dataLimite) { this.dataLimite = dataLimite; }

    public String getCriadorId() { return criadorId; }
    public void setCriadorId(String criadorId) { this.criadorId = criadorId; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public StatusProjeto getStatus() { return status; }
    public void setStatus(StatusProjeto status) { this.status = status; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    // Builder Pattern manual
    public static class Builder {
        private String id;
        private String titulo;
        private String descricao;
        private String categoria;
        private BigDecimal metaFinanceira;
        private BigDecimal valorArrecadado;
        private LocalDate dataLimite;
        private String criadorId;
        private List<String> tags;
        private StatusProjeto status;
        private String imagemUrl;
        private LocalDateTime dataCriacao;
        private LocalDateTime dataAtualizacao;

        public Builder id(String id) { this.id = id; return this; }
        public Builder titulo(String titulo) { this.titulo = titulo; return this; }
        public Builder descricao(String descricao) { this.descricao = descricao; return this; }
        public Builder categoria(String categoria) { this.categoria = categoria; return this; }
        public Builder metaFinanceira(BigDecimal metaFinanceira) { this.metaFinanceira = metaFinanceira; return this; }
        public Builder valorArrecadado(BigDecimal valorArrecadado) { this.valorArrecadado = valorArrecadado; return this; }
        public Builder dataLimite(LocalDate dataLimite) { this.dataLimite = dataLimite; return this; }
        public Builder criadorId(String criadorId) { this.criadorId = criadorId; return this; }
        public Builder tags(List<String> tags) { this.tags = tags; return this; }
        public Builder status(StatusProjeto status) { this.status = status; return this; }
        public Builder imagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; return this; }
        public Builder dataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; return this; }
        public Builder dataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; return this; }

        public Projeto build() {
            return new Projeto(id, titulo, descricao, categoria, metaFinanceira,
                    valorArrecadado, dataLimite, criadorId, tags,
                    status, imagemUrl, dataCriacao, dataAtualizacao);
        }
    }

    // Enum
    public enum StatusProjeto {
        RASCUNHO,
        PUBLICADO,
        EM_ANDAMENTO,
        CONCLUIDO,
        CANCELADO,
        FINALIZADO_COM_SUCESSO,
        FINALIZADO_SEM_SUCESSO
    }

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projeto projeto = (Projeto) o;
        return Objects.equals(id, projeto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Projeto{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", status=" + status +
                '}';
    }
}