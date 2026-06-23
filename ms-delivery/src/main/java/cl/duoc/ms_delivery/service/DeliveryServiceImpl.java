package cl.duoc.ms_delivery.service;

import cl.duoc.ms_delivery.dto.DeliveryRequestDto;
import cl.duoc.ms_delivery.dto.DeliveryResponseDto;
import cl.duoc.ms_delivery.exception.ResourceNotFoundException;
import cl.duoc.ms_delivery.exception.ServiceUnavailableException;
import cl.duoc.ms_delivery.feign.ClientDto;
import cl.duoc.ms_delivery.feign.ClientFeignClient;
import cl.duoc.ms_delivery.feign.SaleDto;
import cl.duoc.ms_delivery.feign.SaleFeignClient;
import cl.duoc.ms_delivery.model.Delivery;
import cl.duoc.ms_delivery.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository repository;
    private final SaleFeignClient saleFeignClient;
    private final ClientFeignClient clientFeignClient;

    private DeliveryResponseDto toDto(Delivery entity) {
        return new DeliveryResponseDto(
                entity.getId(),
                entity.getSaleId(),
                entity.getClientId(),
                entity.getDeliveryDate(),
                entity.getDeliveryAddress(),
                entity.getStatus(),
                entity.getNotes(),
                null,
                null
        );
    }

    private Delivery toEntity(DeliveryRequestDto dto) {
        return new Delivery(
                dto.getId(),
                dto.getSaleId(),
                dto.getClientId(),
                dto.getDeliveryDate(),
                dto.getDeliveryAddress(),
                dto.getStatus(),
                dto.getNotes()
        );
    }

    private void validateForeignKeys(DeliveryRequestDto dto) {
        SaleDto sale = saleFeignClient.findById(dto.getSaleId());
        if (sale == null) throw new ResourceNotFoundException("Venta con id " + dto.getSaleId() + " no existe");

        ClientDto client = clientFeignClient.findById(dto.getClientId());
        if (client == null) throw new ResourceNotFoundException("Cliente con id " + dto.getClientId() + " no existe");
    }

    @Override
    public DeliveryResponseDto findById(Long id) {
        return repository.findById(id).map(delivery -> {
            DeliveryResponseDto dto = toDto(delivery);
            try {
                dto.setSale(saleFeignClient.findById(delivery.getSaleId()));
            } catch (ServiceUnavailableException e) {
                log.warn("Servicio de ventas no disponible para delivery {}: {}", id, e.getMessage());
            }
            try {
                dto.setClient(clientFeignClient.findById(delivery.getClientId()));
            } catch (ServiceUnavailableException e) {
                log.warn("Servicio de clientes no disponible para delivery {}: {}", id, e.getMessage());
            }
            return dto;
        }).orElse(null);
    }

    @Override
    public List<DeliveryResponseDto> findAll() {
        log.info("Fetching all deliveries");
        return repository.findAll().stream().map(delivery -> {
            DeliveryResponseDto dto = toDto(delivery);
            try {
                dto.setSale(saleFeignClient.findById(delivery.getSaleId()));
            } catch (ServiceUnavailableException e) {
                log.warn("Servicio de ventas no disponible para delivery {}: {}", delivery.getId(), e.getMessage());
            }
            try {
                dto.setClient(clientFeignClient.findById(delivery.getClientId()));
            } catch (ServiceUnavailableException e) {
                log.warn("Servicio de clientes no disponible para delivery {}: {}", delivery.getId(), e.getMessage());
            }
            return dto;
        }).toList();
    }

    @Override
    public DeliveryResponseDto create(DeliveryRequestDto dto) {
        validateForeignKeys(dto);
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public DeliveryResponseDto update(DeliveryRequestDto dto) {
        validateForeignKeys(dto);
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
