package com.niewhic.vetclinic.controller;

import com.niewhic.vetclinic.model.office.Office;
import com.niewhic.vetclinic.model.office.OfficeDto;
import com.niewhic.vetclinic.service.OfficeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Gets all Offices", description = "Retrieves a list of all offices available in the system with default pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of offices",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OfficeDto.class)))}),
            @ApiResponse(responseCode = "204", description = "No offices found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<OfficeDto>> getAllOffices() {
        List<OfficeDto> offices = officeService.findAll(PageRequest.of(0, 5)).stream()
                .map(office -> modelMapper.map(office, OfficeDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(offices);
    }
    @Operation(summary = "Gets Office by ID", description = "Office must exist")
    @Parameters(value = {
            @Parameter(name = "id", description = "ID of the office that needs to be fetched")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the office",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OfficeDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Office not found", content = @Content)
    })

    @GetMapping("/{id}")
    public ResponseEntity<OfficeDto> getOfficeById(@PathVariable long id) {
        Office office = officeService.findById(id);
        return ResponseEntity.ok(modelMapper.map(office, OfficeDto.class));
    }
    @Operation(summary = "Add a new Office", description = "Creates a new office entry in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Office created",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OfficeDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Office details supplied", content = @Content)
    })
    @PostMapping
    public ResponseEntity<OfficeDto> addOffice(@RequestBody Office office) {
        Office savedOffice = officeService.save(office);
        return new ResponseEntity<>(modelMapper.map(savedOffice, OfficeDto.class), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete an existing Office", description = "Deletes an office entry from the system by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Office deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid Office ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Office not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffice(@PathVariable long id) {
        officeService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Update an existing Office", description = "Updates an existing office entry in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Office updated",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OfficeDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Office details supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Office not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<OfficeDto> updateOffice(@PathVariable long id, @RequestBody Office office) {
        office.setId(id);
        Office updatedOffice = officeService.save(office);
        return ResponseEntity.ok(modelMapper.map(updatedOffice, OfficeDto.class));
    }
    @Operation(summary = "Partially update an existing Office", description = "Partially updates an existing office entry in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Office partially updated",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OfficeDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Office details supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Office not found", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Office> editOfficePartially(@PathVariable long id, @RequestBody Office updatedOffice) {
        Office editedOffice = officeService.editPartially(id, updatedOffice);
        return ResponseEntity.ok(editedOffice);
    }
}
