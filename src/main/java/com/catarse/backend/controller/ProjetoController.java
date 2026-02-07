package com.catarse.backend.controller;

import com.catarse.backend.dto.ProjetoRequest;
import com.catarse.backend.dto.ProjetoResponse;
import com.catarse.backend.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Projetos", description = "API de Gerenciamento de Projetos")
@RestController
@RequestMapping("/projetos")
@RequiredArgsConstructor
public class ProjetoController {

    private final ProjetoService projetoService;

    @Operation(summary = "Criar um novo projeto")
    @PostMapping
    public ResponseEntity<ProjetoResponse> criarProjeto(
            @Valid @RequestBody ProjetoRequest request) {
        ProjetoResponse response = projetoService.criarProjeto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Buscar projeto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponse> buscarPorId(@PathVariable String id) {
        ProjetoResponse response = projetoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar todos os projetos")
    @GetMapping
    public ResponseEntity<Page<ProjetoResponse>> listarTodos(
            @PageableDefault(size = 10, sort = "dataCriacao") Pageable pageable) {
        Page<ProjetoResponse> response = projetoService.listarTodos(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar projeto")
    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponse> atualizarProjeto(
            @PathVariable String id,
            @Valid @RequestBody ProjetoRequest request) {
        ProjetoResponse response = projetoService.atualizarProjeto(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deletar projeto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProjeto(@PathVariable String id) {
        projetoService.deletarProjeto(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Publicar projeto (mudar de rascunho para publicado)")
    @PostMapping("/{id}/publicar")
    public ResponseEntity<ProjetoResponse> publicarProjeto(@PathVariable String id) {
        ProjetoResponse response = projetoService.publicarProjeto(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Adicionar doação a um projeto")
    @PostMapping("/{id}/doacao")
    public ResponseEntity<ProjetoResponse> adicionarDoacao(
            @PathVariable String id,
            @RequestParam BigDecimal valor) {
        ProjetoResponse response = projetoService.adicionarDoacao(id, valor);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar projetos por categoria")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<Page<ProjetoResponse>> buscarPorCategoria(
            @PathVariable String categoria,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ProjetoResponse> response = projetoService.buscarPorCategoria(categoria, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar projetos por criador")
    @GetMapping("/criador/{criadorId}")
    public ResponseEntity<Page<ProjetoResponse>> buscarPorCriador(
            @PathVariable String criadorId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ProjetoResponse> response = projetoService.buscarPorCriador(criadorId, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar projetos por tags")
    @GetMapping("/tags")
    public ResponseEntity<Page<ProjetoResponse>> buscarPorTags(
            @RequestParam List<String> tags,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ProjetoResponse> response = projetoService.buscarPorTags(tags, pageable);
        return ResponseEntity.ok(response);
    }
}