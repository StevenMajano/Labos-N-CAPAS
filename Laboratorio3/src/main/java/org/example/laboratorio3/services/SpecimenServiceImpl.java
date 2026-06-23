package org.example.laboratorio3.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.laboratorio3.exceptions.ResourceNotFoundException;
import org.example.laboratorio3.mappers.SpecimenMapper;
import org.example.laboratorio3.models.entities.Specimen;
import org.example.laboratorio3.models.dtos.CreateSpecimenRequest;
import org.example.laboratorio3.models.dtos.SpecimenResponse;
import org.example.laboratorio3.models.dtos.UpdateSpecimenRequest;
import org.example.laboratorio3.repositories.SpecimenRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecimenServiceImpl implements SpecimenService {

    private final SpecimenRepository specimenRepository;
    private final SpecimenMapper specimenMapper;

    @Override
    @Transactional
    public SpecimenResponse createSpecimen(CreateSpecimenRequest request) {
        return specimenMapper.toDto(
                specimenRepository.save(specimenMapper.toEntityCreate(request))
        );
    }

    @Override
    public Page<Specimen> getAllSpecimens(int page, int size, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.DESC.name())
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Specimen> specimenPage = specimenRepository.findAll(pageable);

        if (specimenPage.isEmpty()) {
            throw new ResourceNotFoundException("No specimens are registered in Hyrule");
        }

        return specimenPage;
    }

    @Override
    public SpecimenResponse getSpecimenById(UUID id) {
        return specimenMapper.toDto(specimenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specimen not found in Hyrule Records"))
        );
    }

    @Override
    @Transactional
    public SpecimenResponse updateSpecimen(UUID id, UpdateSpecimenRequest request) {
        this.getSpecimenById(id);
        return specimenMapper.toDto(specimenRepository.save(specimenMapper.toEntityUpdate(request, id)));
    }

    @Override
    @Transactional
    public SpecimenResponse deleteSpecimen(UUID id) {
        SpecimenResponse existSpecimen = this.getSpecimenById(id);
        specimenRepository.deleteById(id);
        return existSpecimen;
    }
}
