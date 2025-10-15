package com.vaudoise.factory.controller;

import com.vaudoise.factory.dto.request.ContractRequestDto;
import com.vaudoise.factory.dto.request.ContractUpdateDto;
import com.vaudoise.factory.dto.response.ContractResponseDto;
import com.vaudoise.factory.service.ContractService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity<ContractResponseDto> createContract(@Valid @RequestBody ContractRequestDto requestDto) {
        ContractResponseDto response = contractService.createContract(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContractResponseDto> updateContractCost(@PathVariable UUID id, @Valid @RequestBody ContractUpdateDto updateDto) {
        ContractResponseDto response = contractService.updateContractCost(id, updateDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ContractResponseDto>> getActiveContractsByClient(
            @PathVariable UUID clientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate updateDate) {
        List<ContractResponseDto> contracts = contractService.getActiveContractsByClient(clientId, updateDate);
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/client/{clientId}/total-cost")
    public ResponseEntity<Map<String, Object>> getTotalActiveCost(@PathVariable UUID clientId) {
        float totalCost = contractService.getTotalActiveCostByClient(clientId);

        Map<String, Object> response = new HashMap<>();
        response.put("totalCost", totalCost);

        return ResponseEntity.ok(response);
    }
}