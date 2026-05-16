package cl.duoc.ms_clients.service;

import cl.duoc.ms_clients.dto.ClientDTO;
import cl.duoc.ms_clients.mapper.ClientMapper;
import cl.duoc.ms_clients.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    public List<ClientDTO> listarClientes() {

        log.info("Listando clientes");

        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }
}