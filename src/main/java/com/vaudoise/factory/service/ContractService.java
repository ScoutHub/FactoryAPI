package com.vaudoise.factory.service;

import com.vaudoise.factory.dto.request.ContractRequestDto;
import com.vaudoise.factory.dto.request.ContractUpdateDto;
import com.vaudoise.factory.dto.response.ContractResponseDto;
import com.vaudoise.factory.entity.Client;
import com.vaudoise.factory.entity.Contract;
import com.vaudoise.factory.exception.ResourceNotFoundException;
import com.vaudoise.factory.repository.ClientRepository;
import com.vaudoise.factory.repository.ContractRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContractService {

    private final ContractRepository contractRepository;
    private final ClientRepository clientRepository;

    public ContractService(ContractRepository contractRepository, ClientRepository clientRepository) {
        this.contractRepository = contractRepository;
        this.clientRepository = clientRepository;
    }

    public ContractResponseDto createContract(ContractRequestDto requestDto) {
        Client client = clientRepository.findById(requestDto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + requestDto.getClientId()));

        Contract contract = new Contract();
        contract.setClient(client);

        contract.setStartDate(requestDto.getStartDate() != null ? requestDto.getStartDate() : LocalDate.now());
        contract.setEndDate(requestDto.getEndDate());
        contract.setCostAmount(requestDto.getCostAmount());
        contract.setUpdateDate(LocalDate.now());

        Contract savedContract = contractRepository.save(contract);
        return mapToContractResponseDto(savedContract);
    }

    public ContractResponseDto updateContractCost(UUID id, ContractUpdateDto updateDto) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));

        contract.setCostAmount(updateDto.getCostAmount());
        contract.setUpdateDate(LocalDate.now());

        Contract updatedContract = contractRepository.save(contract);
        return mapToContractResponseDto(updatedContract);
    }

    @Transactional(readOnly = true)
    public List<ContractResponseDto> getActiveContractsByClient(UUID clientId, LocalDate updateDate) {
        clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientId));

        LocalDate currentDate = LocalDate.now();
        List<Contract> contracts;

        if (updateDate != null) {
            contracts = contractRepository.findActiveContractsByClientIdAndUpdateDate(clientId, currentDate, updateDate);
        } else {
            contracts = contractRepository.findActiveContractsByClientId(clientId, currentDate);
        }

        return contracts.stream()
                .map(this::mapToContractResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public float getTotalActiveCostByClient(UUID clientId) {
        clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientId));

        LocalDate currentDate = LocalDate.now();
        return contractRepository.sumActiveContractsCostByClientId(clientId, currentDate);
    }

    private ContractResponseDto mapToContractResponseDto(Contract contract) {
        ContractResponseDto dto = new ContractResponseDto();
        dto.setId(contract.getId());
        dto.setStartDate(contract.getStartDate());
        dto.setEndDate(contract.getEndDate());
        dto.setCostAmount(contract.getCostAmount());
        dto.setClientId(contract.getClient().getId());
        return dto;
    }
}

