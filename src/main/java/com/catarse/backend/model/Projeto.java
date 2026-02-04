package com.catarse.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public enum StatusProjeto {
        RASCUNHO,
        PUBLICADO,
        EM_ANDAMENTO,
        CONCLUIDO,
        CANCELADO,
        FINALIZADO_COM_SUCESSO,
        FINALIZADO_SEM_SUCESSO
    }
}