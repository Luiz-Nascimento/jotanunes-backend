package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.item.ItemDocResponse;
import com.jotanunes.especificacoes.dto.item.ItemRequest;
import com.jotanunes.especificacoes.dto.item.ItemResponse;
import com.jotanunes.especificacoes.dto.item.ItemUpdate;
import com.jotanunes.especificacoes.dto.revisaoItens.RevisaoItemRequest;
import com.jotanunes.especificacoes.dto.revisaoItens.RevisaoItemResponse;
import com.jotanunes.especificacoes.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Itens", description = "Operações relacionadas a itens de um ambiente")
@RestController
@RequestMapping("/itens")
public class ItemController {

    @Autowired
    private ItemService service;

    @Operation(
            summary = "Retornar dados de todos itens",
            description = "Retorna dados de todos itens cadastrados"
    )
    @GetMapping
    public List<ItemResponse> listAllItens() {
        return service.getAllItens();
    }
    @Operation(
            summary = "Retornar dados de um item",
            description = "Retorna dados do item com ID especificado"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable Integer id) {
        ItemResponse response = service.getItemById(id);
        return ResponseEntity.ok(response);
    }
    @Operation(
            summary = "Retornar dados de um item formatados para documento",
            description = "Retorna dados do item com ID especificado formatados para documento"
    )
    @GetMapping("/doc/{id}")
    public ResponseEntity<ItemDocResponse> getItemDocResponse(@PathVariable Integer id) {
        ItemDocResponse response = service.getItemDocResponse(id);
        return ResponseEntity.ok(response);
    }
    @Operation(
            summary = "Criar um novo item",
            description = "Cria um novo item associado ao ambiente com ID especificado"
    )
    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody @Valid ItemRequest data) {
        ItemResponse response = service.createItem(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Integer id, @RequestBody @Valid ItemUpdate data) {
        ItemResponse response = service.updateItem(id, data);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasRole('GESTOR')")
    @PutMapping("/revisarItem")
    public ResponseEntity<RevisaoItemResponse> reviewItem(@RequestBody @Valid RevisaoItemRequest request) {
        RevisaoItemResponse response = service.reviewItem(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('GESTOR')")
    @PutMapping("/revisarItens")
    public ResponseEntity<List<RevisaoItemResponse>> reviewItems(@RequestBody @Valid List<RevisaoItemRequest> requests) {
        List<RevisaoItemResponse> responses = service.reviewItemsBulk(requests);
        return ResponseEntity.ok(responses);
    }
    @Operation(
            summary = "Deletar um item",
            description = "Deleta o item com ID especificado"
    )
                                              @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Integer id) {
        service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
