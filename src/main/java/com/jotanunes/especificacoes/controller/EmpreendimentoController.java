package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.controller.openapi.EmpreendimentoControllerOpenApi;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.CombinacaoEMMResponse;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.dto.documento.DocumentoGeradoDTO;
import com.jotanunes.especificacoes.dto.empreendimento.*;
import com.jotanunes.especificacoes.dto.revisaoItens.RevisaoItemResponse;
import com.jotanunes.especificacoes.model.RevisaoItem;
import com.jotanunes.especificacoes.service.AmbienteService;
import com.jotanunes.especificacoes.service.EmpreendimentoService;
import com.jotanunes.especificacoes.service.RelatorioService;
import com.jotanunes.especificacoes.service.RevisaoItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Empreendimentos", description = "Operações relacionadas a empreendimentos.")
@RestController
@RequestMapping("/empreendimentos")
public class EmpreendimentoController implements EmpreendimentoControllerOpenApi {

    private final EmpreendimentoService empreendimentoService;
    private final AmbienteService ambienteService;
    private final RevisaoItemService revisaoItemService;

    private final RelatorioService relatorioService;

    private static final Logger logger = LoggerFactory.getLogger(EmpreendimentoController.class);

    public EmpreendimentoController(EmpreendimentoService empreendimentoService, AmbienteService ambienteService, RevisaoItemService revisaoItemService,
                                    RelatorioService relatorioService) {
        this.empreendimentoService = empreendimentoService;
        this.ambienteService = ambienteService;
        this.revisaoItemService = revisaoItemService;
        this.relatorioService = relatorioService;
    }

    @Override
    @GetMapping
    public List<EmpreendimentoResponse> findAll() {
        return empreendimentoService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EmpreendimentoResponse> findById(@PathVariable Integer id) {
        EmpreendimentoResponse response = empreendimentoService.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping("/{id}/documento")
    public ResponseEntity<EspecificacaTecnicaDTO> findByIdAsDocument(@PathVariable Integer id) {
        EspecificacaTecnicaDTO response = empreendimentoService.getDadosParaRelatorio(id);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping("/{id}/ambientes")
    public List<AmbienteResponse> listAmbientes(@PathVariable Integer id) {
        return ambienteService.listByEmpreendimento(id);
    }

    @Override
    @GetMapping("/{id}/combinacoes")
    public List<CombinacaoEMMResponse> findCombinacoes(@PathVariable Integer id) {
        return empreendimentoService.findCombinacoes(id);
    }

    @Override
    @GetMapping("/{id}/docx")
    public ResponseEntity<byte[]> downloadAsDocx(@PathVariable Integer id) {
        DocumentoGeradoDTO documento = relatorioService.gerarEspecificacaoTecnica(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documento.filename()
                        + "\"")
                .contentType(documento.contentType())
                .body(documento.bytes());
    }

    @GetMapping("/{id}/revisoes")
    public List<RevisaoItemResponse> findRevisoes(@PathVariable Integer id) {
        return revisaoItemService.findRevisoesByEmpreendimentoId(id);
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('CRIAR_EMPREENDIMENTOS')")
    public ResponseEntity<EmpreendimentoResponse> create(@RequestBody @Valid EmpreendimentoRequest data) {
        EmpreendimentoResponse response = empreendimentoService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PostMapping("/copiar/{id}")
    @PreAuthorize("hasAuthority('CRIAR_EMPREENDIMENTOS')")
    public ResponseEntity<EmpreendimentoResponse> copy(@RequestBody @Valid EmpreendimentoRequest data,
                                                       @PathVariable Integer id) {
        EmpreendimentoResponse response = empreendimentoService.copy(data, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PatchMapping("/{id}/submeter")
    public ResponseEntity<Void> submeter(@PathVariable Integer id) {
        empreendimentoService.enviarParaRevisao(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @PatchMapping("/{id}/aprovar")
    @PreAuthorize("hasAuthority('REVISAR_EMPREENDIMENTOS')")
    public ResponseEntity<Void> aprovar(@PathVariable Integer id) {
        empreendimentoService.aprovar(id);
        return ResponseEntity.ok().build();
    }
    @Override
    @PatchMapping("/{id}/reprovar")
    @PreAuthorize("hasAuthority('REVISAR_EMPREENDIMENTOS')")
    public ResponseEntity<Void> reprovar(@PathVariable Integer id) {
        empreendimentoService.reprovar(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDITAR_EMPREENDIMENTOS')")
     public ResponseEntity<EmpreendimentoResponse> update(@PathVariable Integer id,
                                                          @RequestBody @Valid EmpreendimentoUpdate data) {
        EmpreendimentoResponse response = empreendimentoService.update(id, data);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @PutMapping("/observacao/{id}")
    @PreAuthorize("hasAuthority('EDITAR_EMPREENDIMENTOS')")
    public ResponseEntity<EmpreendimentoResponse> adicionarObservacao(@PathVariable Integer id,
                                                                      @RequestBody @Valid EmpreendimentoObservacao data) {
        EmpreendimentoResponse response = empreendimentoService.adicionarObservacao(id, data);
        return ResponseEntity.ok(response);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/aprovar/{id}")
    public ResponseEntity<Void> forceAprovacao(@PathVariable Integer id) {
        empreendimentoService.forceAprovacao(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        empreendimentoService.delete(id);
        logger.info("User: {} deletou o empreendimento {}", auth.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{id}/observacoes/{index}")
    @PreAuthorize("hasAuthority('EDITAR_EMPREENDIMENTOS')")
    public ResponseEntity<EmpreendimentoResponse> atualizarObservacao(
            @PathVariable Integer id,
            @PathVariable int index,
            @RequestBody @Valid EmpreendimentoObservacao data) {

        EmpreendimentoResponse response = empreendimentoService.atualizarObservacao(id, index, data);
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/{id}/observacoes/{index}")
    @PreAuthorize("hasAuthority('EDITAR_EMPREENDIMENTOS')")
    public ResponseEntity<EmpreendimentoResponse> removerObservacao(
            @PathVariable Integer id,
            @PathVariable int index) {

        EmpreendimentoResponse response = empreendimentoService.removerObservacao(id, index);
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/{id}/observacoes")
    @PreAuthorize("hasAuthority('EDITAR_EMPREENDIMENTOS')")
    public ResponseEntity<EmpreendimentoResponse> limparObservacoes(@PathVariable Integer id) {
        EmpreendimentoResponse response = empreendimentoService.limparObservacoes(id);
        return ResponseEntity.ok(response);
    }


}
