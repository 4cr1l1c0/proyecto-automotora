package cl.duoc.ms_suppliers.service;

import cl.duoc.ms_suppliers.dto.SupplierRequestDto;
import cl.duoc.ms_suppliers.dto.SupplierResponseDto;
import cl.duoc.ms_suppliers.model.Supplier;
import cl.duoc.ms_suppliers.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository repository;

    private SupplierResponseDto toDto(Supplier entity) {
        return new SupplierResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getRut(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getContactName(),
                entity.getActive()
        );
    }

    private Supplier toEntity(SupplierRequestDto dto) {
        return new Supplier(
                dto.getId(),
                dto.getName(),
                dto.getRut(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getAddress(),
                dto.getContactName(),
                dto.getActive()
        );
    }

    @Override
    public SupplierResponseDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<SupplierResponseDto> findAll() {
        log.info("Fetching all suppliers");
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public SupplierResponseDto create(SupplierRequestDto dto) {
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public SupplierResponseDto update(SupplierRequestDto dto) {
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
