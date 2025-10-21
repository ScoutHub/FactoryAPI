package com.vaudoise.factory.service;

import com.vaudoise.factory.dto.request.LoginRequestDto;
import com.vaudoise.factory.dto.request.RegisterRequestDto;
import com.vaudoise.factory.dto.response.AuthResponseDto;
import com.vaudoise.factory.entity.Client;
import com.vaudoise.factory.entity.Company;
import com.vaudoise.factory.entity.Person;
import com.vaudoise.factory.exception.EmailAlreadyExistsException;
import com.vaudoise.factory.exception.ResourceNotFoundException;
import com.vaudoise.factory.repository.ClientRepository;
import com.vaudoise.factory.repository.CompanyRepository;
import com.vaudoise.factory.repository.PersonRepository;
import com.vaudoise.factory.utils.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final ClientRepository clientRepository;
    private final PersonRepository personRepository;
    private final CompanyRepository companyRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(ClientRepository clientRepository,
                       PersonRepository personRepository,
                       CompanyRepository companyRepository,
                       JwtUtil jwtUtil) {
        this.clientRepository = clientRepository;
        this.personRepository = personRepository;
        this.companyRepository = companyRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public AuthResponseDto register(RegisterRequestDto requestDto) {
        if (clientRepository.existsByEmail(requestDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        Client client;

        if ("PERSON".equals(requestDto.getClientType())) {
            if (requestDto.getBirthdate() == null) {
                throw new IllegalArgumentException("Birthdate is required for PERSON type");
            }
            Person person = new Person();
            person.setName(requestDto.getName());
            person.setEmail(requestDto.getEmail());
            person.setPhone(requestDto.getPhone());
            person.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            person.setBirthdate(requestDto.getBirthdate());
            client = personRepository.save(person);

        } else if ("COMPANY".equals(requestDto.getClientType())) {
            if (requestDto.getCompanyIdentifier() == null) {
                throw new IllegalArgumentException("Company identifier is required for COMPANY type");
            }
            if (companyRepository.existsByCompanyIdentifier(requestDto.getCompanyIdentifier())) {
                throw new EmailAlreadyExistsException("Company identifier already exists");
            }
            Company company = new Company();
            company.setName(requestDto.getName());
            company.setEmail(requestDto.getEmail());
            company.setPhone(requestDto.getPhone());
            company.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            company.setCompanyIdentifier(requestDto.getCompanyIdentifier());
            client = companyRepository.save(company);

        } else {
            throw new IllegalArgumentException("Invalid client type. Must be PERSON or COMPANY");
        }

        String clientType = client instanceof Person ? "PERSON" : "COMPANY";
        String token = jwtUtil.generateToken(client.getId(), client.getEmail(), clientType);

        return new AuthResponseDto(token, client.getId(), clientType);
    }

    public AuthResponseDto login(LoginRequestDto requestDto) {
        Client client = clientRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(requestDto.getPassword(), client.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }

        String clientType = client instanceof Person ? "PERSON" : "COMPANY";
        String token = jwtUtil.generateToken(client.getId(), client.getEmail(), clientType);

        return new AuthResponseDto(token, client.getId(), clientType);
    }
}
