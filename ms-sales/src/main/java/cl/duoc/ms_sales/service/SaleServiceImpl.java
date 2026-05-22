package cl.duoc.ms_sales.service;

import cl.duoc.ms_sales.dto.SaleRequestDto;
import cl.duoc.ms_sales.dto.SaleResponseDto;
import cl.duoc.ms_sales.feign.ClientFeignClient;
import cl.duoc.ms_sales.model.SaleModel;
import cl.duoc.ms_sales.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaleServiceImpl implements SaleService {

    private final SaleRepository repository;
    private final ClientFeignClient clientFeignClient;

    private SaleResponseDto toDto(SaleModel entity) {
        return new SaleResponseDto(
                entity.getId(),
                entity.getSaleDate(),
                entity.getSubtotal(),
                entity.getIva(),
                entity.getTotal(),
                entity.getPaymentType(),
                entity.getClientId(),
                entity.getVehicleId(),
                entity.getEmployeeId(),
                null
        );
    }

    private SaleModel toEntity(SaleRequestDto dto) {
        return new SaleModel(
                dto.getId(),
                dto.getSaleDate(),
                dto.getSubtotal(),
                dto.getIva(),
                dto.getTotal(),
                dto.getPaymentType(),
                dto.getClientId(),
                dto.getVehicleId(),
                dto.getEmployeeId()
        );
    }

    @Override
    public SaleResponseDto findById(Long id) {
        return repository.findById(id).map(sale -> {
            SaleResponseDto dto = toDto(sale);
            try {
                dto.setClient(clientFeignClient.findById(sale.getClientId()));
            } catch (Exception e) {
                log.warn("Could not fetch client data for sale {}: {}", id, e.getMessage());
            }
            return dto;
        }).orElse(null);
    }

    @Override
    public List<SaleResponseDto> findAll() {
        log.info("Fetching all sales");
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public SaleResponseDto create(SaleRequestDto dto) {
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public SaleResponseDto update(SaleRequestDto dto) {
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
