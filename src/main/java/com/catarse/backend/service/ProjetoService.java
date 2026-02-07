package com.catarse.backend.service;

import com.catarse.backend.dto.ProjetoRequest;
import com.catarse.backend.dto.ProjetoResponse;
import com.catarse.backend.exception.ResourceNotFoundException;
import com.catarse.backend.model.Projeto;
import com.catarse.backend.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    @Transactional
    public ProjetoResponse criarProjeto(ProjetoRequest request) {
        log.info("Criando novo projeto: {}", request.getTitulo());

        if (projetoRepository.findByTitulo(request.getTitulo()).isPresent()) {
            throw new IllegalArgumentException("Já existe um projeto com este título");
        }

        Projeto projeto = request.toEntity();
        projeto = projetoRepository.save(projeto);

        log.info("Projeto criado com sucesso: {}", projeto.getId());
        return ProjetoResponse.fromEntity(projeto);
    }

    public ProjetoResponse buscarPorId(String id) {
        log.debug("Buscando projeto por ID: {}", id);

        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado com ID: " + id));

        return ProjetoResponse.fromEntity(projeto);
    }

    public Page<ProjetoResponse> listarTodos(Pageable pageable) {
        log.debug("Listando todos os projetos");

        return projetoRepository.findAll(pageable)
                .map(ProjetoResponse::fromEntity);
    }

    @Transactional
    public ProjetoResponse atualizarProjeto(String id, ProjetoRequest request) {
        log.info("Atualizando projeto: {}", id);

        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado com ID: " + id));

        // Verifica se outro projeto já usa este título
        projetoRepository.findByTitulo(request.getTitulo())
                .filter(p -> !p.getId().equals(id))
                .ifPresent(p -> {
                    throw new IllegalArgumentException("Já existe outro projeto com este título");
                });

        projeto.setTitulo(request.getTitulo());
        projeto.setDescricao(request.getDescricao());
        projeto.setCategoria(request.getCategoria());
        projeto.setMetaFinanceira(request.getMetaFinanceira());
        projeto.setDataLimite(request.getDataLimite());
        projeto.setTags(request.getTags());
        projeto.setImagemUrl(request.getImagemUrl());

        projeto = projetoRepository.save(projeto);
        log.info("Projeto atualizado com sucesso: {}", id);

        return ProjetoResponse.fromEntity(projeto);
    }

    @Transactional
    public void deletarProjeto(String id) {
        log.info("Deletando projeto: {}", id);

        if (!projetoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Projeto não encontrado com ID: " + id);
        }

        projetoRepository.deleteById(id);
        log.info("Projeto deletado com sucesso: {}", id);
    }

    @Transactional
    public ProjetoResponse publicarProjeto(String id) {
        log.info("Publicando projeto: {}", id);

        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado com ID: " + id));

        if (projeto.getStatus() != Projeto.StatusProjeto.RASCUNHO) {
            throw new IllegalStateException("Apenas projetos em rascunho podem ser publicados");
        }

        projeto.setStatus(Projeto.StatusProjeto.PUBLICADO);
        projeto = projetoRepository.save(projeto);

        log.info("Projeto publicado com sucesso: {}", id);
        return ProjetoResponse.fromEntity(projeto);
    }

    @Transactional
    public ProjetoResponse adicionarDoacao(String id, BigDecimal valor) {
        log.info("Adicionando doação ao projeto: {}", id);

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da doação deve ser positivo");
        }

        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado com ID: " + id));

        if (projeto.getStatus() != Projeto.StatusProjeto.PUBLICADO &&
                projeto.getStatus() != Projeto.StatusProjeto.EM_ANDAMENTO) {
            throw new IllegalStateException("Projeto não está aberto para doações");
        }

        if (LocalDate.now().isAfter(projeto.getDataLimite())) {
            throw new IllegalStateException("Prazo para doações já expirou");
        }

        BigDecimal novoValor = projeto.getValorArrecadado().add(valor);
        projeto.setValorArrecadado(novoValor);

        // Atualiza status se necessário
        if (novoValor.compareTo(projeto.getMetaFinanceira()) >= 0) {
            projeto.setStatus(Projeto.StatusProjeto.CONCLUIDO);
        } else if (projeto.getStatus() == Projeto.StatusProjeto.PUBLICADO) {
            projeto.setStatus(Projeto.StatusProjeto.EM_ANDAMENTO);
        }

        projeto = projetoRepository.save(projeto);
        log.info("Doação adicionada ao projeto: {}", id);

        return ProjetoResponse.fromEntity(projeto);
    }

    public Page<ProjetoResponse> buscarPorCategoria(String categoria, Pageable pageable) {
        log.debug("Buscando projetos por categoria: {}", categoria);

        // Implementação temporária - filtra manualmente
        Page<Projeto> todos = projetoRepository.findAll(pageable);
        List<ProjetoResponse> filtrados = todos.getContent().stream()
                .filter(p -> p.getCategoria() != null && p.getCategoria().equalsIgnoreCase(categoria))
                .map(ProjetoResponse::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(filtrados, pageable, todos.getTotalElements());
    }

    public Page<ProjetoResponse> buscarPorCriador(String criadorId, Pageable pageable) {
        log.debug("Buscando projetos por criador: {}", criadorId);

        // Implementação temporária - filtra manualmente
        Page<Projeto> todos = projetoRepository.findAll(pageable);
        List<ProjetoResponse> filtrados = todos.getContent().stream()
                .filter(p -> p.getCriadorId() != null && p.getCriadorId().equals(criadorId))
                .map(ProjetoResponse::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(filtrados, pageable, todos.getTotalElements());
    }

    public Page<ProjetoResponse> buscarAtivos(Pageable pageable) {
        log.debug("Buscando projetos ativos");

        // Implementação temporária - filtra manualmente
        Page<Projeto> todos = projetoRepository.findAll(pageable);
        List<ProjetoResponse> filtrados = todos.getContent().stream()
                .filter(p -> p.getStatus() == Projeto.StatusProjeto.PUBLICADO)
                .map(ProjetoResponse::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(filtrados, pageable, todos.getTotalElements());
    }

    public Page<ProjetoResponse> buscarPorTags(List<String> tags, Pageable pageable) {
        log.debug("Buscando projetos por tags: {}", tags);

        // Implementação temporária - filtra manualmente
        Page<Projeto> todos = projetoRepository.findAll(pageable);
        List<ProjetoResponse> filtrados = todos.getContent().stream()
                .filter(p -> p.getTags() != null &&
                        p.getTags().stream().anyMatch(tag -> tags.contains(tag)))
                .map(ProjetoResponse::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(filtrados, pageable, todos.getTotalElements());
    }
}