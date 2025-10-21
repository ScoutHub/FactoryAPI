package com.vaudoise.factory.service;

import com.vaudoise.factory.dto.request.ClientUpdateRequestDto;
import com.vaudoise.factory.dto.request.CompanyRequestDto;
import com.vaudoise.factory.dto.request.PersonRequestDto;
import com.vaudoise.factory.dto.response.ClientResponseDto;
import com.vaudoise.factory.entity.Client;
import com.vaudoise.factory.entity.Company;
import com.vaudoise.factory.entity.Contract;
import com.vaudoise.factory.entity.Person;
import com.vaudoise.factory.exception.EmailAlreadyExistsException;
import com.vaudoise.factory.exception.ResourceNotFoundException;
import com.vaudoise.factory.repository.ClientRepository;
import com.vaudoise.factory.repository.CompanyRepository;
import com.vaudoise.factory.repository.ContractRepository;
import com.vaudoise.factory.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final PersonRepository personRepository;
    private final CompanyRepository companyRepository;
    private final ContractRepository contractRepository;

    public ClientService(ClientRepository clientRepository,
                         PersonRepository personRepository,
                         CompanyRepository companyRepository,
                         ContractRepository contractRepository) {
        this.clientRepository = clientRepository;
        this.personRepository = personRepository;
        this.companyRepository = companyRepository;
        this.contractRepository = contractRepository;
    }

    public ClientResponseDto createPerson(PersonRequestDto requestDto) {
        if (clientRepository.existsByEmail(requestDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        Person person = new Person();
        person.setName(requestDto.getName());
        person.setEmail(requestDto.getEmail());
        person.setPhone(requestDto.getPhone());
        person.setBirthdate(requestDto.getBirthdate());

        Person savedPerson = personRepository.save(person);
        return mapToClientResponseDto(savedPerson);
    }

    public ClientResponseDto createCompany(CompanyRequestDto requestDto) {
        if (clientRepository.existsByEmail(requestDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        Company company = new Company();
        company.setName(requestDto.getName());
        company.setEmail(requestDto.getEmail());
        company.setPhone(requestDto.getPhone());
        company.setCompanyIdentifier(requestDto.getCompanyIdentifier());

        Company savedCompany = companyRepository.save(company);
        return mapToClientResponseDto(savedCompany);
    }

    @Transactional(readOnly = true)
    public ClientResponseDto getClientById(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        return mapToClientResponseDto(client);
    }

    public ClientResponseDto updateClient(UUID id, ClientUpdateRequestDto requestDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        Optional<Client> clientExistWithEmail = clientRepository.findByEmail(requestDto.getEmail());

        if (clientExistWithEmail.isPresent() && !clientExistWithEmail.get().getId().equals(client.getId())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        client.setName(requestDto.getName());
        client.setEmail(requestDto.getEmail());
        client.setPhone(requestDto.getPhone());

        Client updatedClient = clientRepository.save(client);
        return mapToClientResponseDto(updatedClient);
    }

    public void deleteClient(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        List<Contract> contracts = contractRepository.findByClientId(id);
        LocalDate currentDate = LocalDate.now();

        for (Contract contract : contracts) {
            contract.setEndDate(currentDate);
            contract.setClient(null);
        }

        contractRepository.saveAll(contracts);

        clientRepository.delete(client);
    }

    private ClientResponseDto mapToClientResponseDto(Client client) {
        ClientResponseDto dto = new ClientResponseDto();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setPhone(client.getPhone());

        if (client instanceof Person) {
            dto.setClientType("PERSON");
            dto.setBirthdate(((Person) client).getBirthdate());
        } else if (client instanceof Company) {
            dto.setClientType("COMPANY");
            dto.setCompanyIdentifier(((Company) client).getCompanyIdentifier());
        }

        return dto;
    }
}