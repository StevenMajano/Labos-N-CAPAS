package org.example.laboratorio3.mappers;

import org.example.laboratorio3.models.dtos.CreateSpecimenRequest;
import org.example.laboratorio3.models.dtos.SpecimenResponse;
import org.example.laboratorio3.models.dtos.UpdateSpecimenRequest;
import org.example.laboratorio3.models.entities.Specimen;
import org.example.laboratorio3.models.dtos.PageableResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component; // <--- AGREGA ESTE IMPORT
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SpecimenMapper {

    public Specimen toEntityCreate(CreateSpecimenRequest request) {
        return Specimen.builder()
                .name(request.getName())
                .region(request.getRegion())
                .dangerLevel(request.getDangerLevel())
                .isFriendly(request.getIsFriendly())
                .build();
    }

    public Specimen toEntityUpdate(UpdateSpecimenRequest request, UUID id) {
        return Specimen.builder()
                .id(id)
                .name(request.getName())
                .region(request.getRegion())
                .dangerLevel(request.getDangerLevel())
                .isFriendly(request.getIsFriendly())
                .build();
    }

    public SpecimenResponse toDto(Specimen specimen) {
        return SpecimenResponse.builder()
                .id(specimen.getId())
                .name(specimen.getName())
                .region(specimen.getRegion())
                .dangerLevel(specimen.getDangerLevel())
                .isFriendly(specimen.getIsFriendly())
                .build();
    }

    public PageableResponse<SpecimenResponse> toPageableResponse(Page<Specimen> page) {
        List<SpecimenResponse> content = page.getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        PageableResponse<SpecimenResponse> response = new PageableResponse<>();
        response.setContent(content);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());

        return response;
    }
}
