package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.office.Office;
import com.niewhic.vetclinic.model.office.OfficeDto;
import com.niewhic.vetclinic.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/offices")
public class OfficeController {

    private final OfficeService officeService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<OfficeDto>> getAllOffices() {
        List<OfficeDto> offices = officeService.findAll(PageRequest.of(0, 5)).stream()
                .map(office -> modelMapper.map(office, OfficeDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(offices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeDto> getOfficeById(@PathVariable long id) {
        Office office = officeService.findById(id);
        return ResponseEntity.ok(modelMapper.map(office, OfficeDto.class));
    }

    @PostMapping
    public ResponseEntity<OfficeDto> addOffice(@RequestBody Office office) {
        Office savedOffice = officeService.save(office);
        return new ResponseEntity<>(modelMapper.map(savedOffice, OfficeDto.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffice(@PathVariable long id) {
        officeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeDto> updateOffice(@PathVariable long id, @RequestBody Office office) {
        office.setId(id);
        Office updatedOffice = officeService.save(office);
        return ResponseEntity.ok(modelMapper.map(updatedOffice, OfficeDto.class));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Office> editOfficePartially(@PathVariable long id, @RequestBody Office updatedOffice) {
        Office editedOffice = officeService.editPartially(id, updatedOffice);
        return ResponseEntity.ok(editedOffice);
    }
}
