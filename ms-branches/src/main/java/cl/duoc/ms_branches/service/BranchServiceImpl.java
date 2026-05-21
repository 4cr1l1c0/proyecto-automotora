package cl.duoc.ms_branches.service;

import cl.duoc.ms_branches.dto.BranchRequestDto;
import cl.duoc.ms_branches.dto.BranchResponseDto;
import cl.duoc.ms_branches.model.Branch;
import cl.duoc.ms_branches.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchServiceImpl implements BranchService {

    private final BranchRepository repository;

    private BranchResponseDto toDto(Branch entity) {
        return new BranchResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getRegion(),
                entity.getCity(),
                entity.getActive()
        );
    }

    private Branch toEntity(BranchRequestDto dto) {
        return new Branch(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getPhone(),
                dto.getRegion(),
                dto.getCity(),
                dto.getActive()
        );
    }

    @Override
    public BranchResponseDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<BranchResponseDto> findAll() {
        log.info("Fetching all branches");
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public BranchResponseDto create(BranchRequestDto dto) {
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public BranchResponseDto update(BranchRequestDto dto) {
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
