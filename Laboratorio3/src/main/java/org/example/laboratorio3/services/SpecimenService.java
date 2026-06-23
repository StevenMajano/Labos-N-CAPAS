package org.example.laboratorio3.services;

import org.example.laboratorio3.models.dtos.CreateSpecimenRequest;
import org.example.laboratorio3.models.dtos.SpecimenResponse;
import org.example.laboratorio3.models.dtos.UpdateSpecimenRequest;
import org.example.laboratorio3.models.entities.Specimen;
import org.springframework.data.domain.Page;
import java.util.UUID;

public interface SpecimenService {
    SpecimenResponse createSpecimen(CreateSpecimenRequest request);
    Page<Specimen> getAllSpecimens(int page, int size, String sortBy, String sortOrder);
    SpecimenResponse getSpecimenById(UUID id);
    SpecimenResponse updateSpecimen(UUID id, UpdateSpecimenRequest request);
    SpecimenResponse deleteSpecimen(UUID id);
}
