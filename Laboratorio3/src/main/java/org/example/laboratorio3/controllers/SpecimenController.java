package org.example.laboratorio3.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.laboratorio3.models.dtos.CreateSpecimenRequest;
import org.example.laboratorio3.models.dtos.SpecimenResponse;
import org.example.laboratorio3.models.dtos.UpdateSpecimenRequest;
import org.example.laboratorio3.models.dtos.PageableResponse;
import org.example.laboratorio3.models.dtos.GeneralResponse;
import org.example.laboratorio3.models.entities.Specimen;
import org.example.laboratorio3.mappers.SpecimenMapper;
import org.example.laboratorio3.services.SpecimenService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/specimens")
@RequiredArgsConstructor
public class SpecimenController {

    private final SpecimenService specimenService;
    private final SpecimenMapper specimenMapper;

    @GetMapping
    public ResponseEntity<GeneralResponse<PageableResponse<SpecimenResponse>>> getAllSpecimens(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            HttpServletRequest request) {

        Page<Specimen> specimenPage = specimenService.getAllSpecimens(page, size, sortBy, sortOrder);
        PageableResponse<SpecimenResponse> data = specimenMapper.toPageableResponse(specimenPage);

        return buildResponse(HttpStatus.OK, "Specimens fetched successfully from Hyrule", data, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<SpecimenResponse>> getSpecimenById(
            @PathVariable UUID id, HttpServletRequest request) {
        SpecimenResponse data = specimenService.getSpecimenById(id);
        return buildResponse(HttpStatus.OK, "Specimen found in Sheikah Slate records", data, request);
    }

    @PostMapping
    public ResponseEntity<GeneralResponse<SpecimenResponse>> createSpecimen(
            @Valid @RequestBody CreateSpecimenRequest body, HttpServletRequest request) {
        SpecimenResponse data = specimenService.createSpecimen(body);
        return buildResponse(HttpStatus.CREATED, "Specimen registered successfully in Hyrule", data, request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse<SpecimenResponse>> updateSpecimen(
            @PathVariable UUID id, @Valid @RequestBody UpdateSpecimenRequest body, HttpServletRequest request) {
        SpecimenResponse data = specimenService.updateSpecimen(id, body);
        return buildResponse(HttpStatus.OK, "Specimen updated successfully in Hyrule Records", data, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse<SpecimenResponse>> deleteSpecimen(
            @PathVariable UUID id, HttpServletRequest request) {
        SpecimenResponse data = specimenService.deleteSpecimen(id);
        return buildResponse(HttpStatus.OK, "Specimen deleted successfully from Hyrule Records", data, request);
    }

    private <T> ResponseEntity<GeneralResponse<T>> buildResponse(
            HttpStatus status, String message, T data, HttpServletRequest request) {

        GeneralResponse<T> response = GeneralResponse.<T>builder()
                .message(message)
                .status(status)
                .timestamp(LocalDateTime.now())
                .uri(request.getRequestURI())
                .data(data)
                .build();

        return new ResponseEntity<>(response, status);
    }
}
