package com.school.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.controllers.dto.address.AddressDto;
import com.school.controllers.dto.personaddress.PersonAddressCreateRequest;
import com.school.persistence.entities.PersonAddress;
import com.school.services.PersonAddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/persons")
@Validated
@SecurityRequirement(name = "oauth2")
@Tag(name = "PersonAddress", description = "PersonAddress management")
@RequiredArgsConstructor
public class PersonAddressController {

    private final PersonAddressService service;

    @GetMapping("/{id}/addresses")
    @Operation(summary = "Get addresses by person id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Addresses found"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public Page<AddressDto> getAddressesByPersonId(@PathVariable UUID id, Pageable pageable) {
        return service.getAddressesByPersonId(id, pageable);
    }

    @GetMapping("/{id}/addresses/current")
    @Operation(summary = "Get current address by person id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address found"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public AddressDto getCurrentAddressByPersonId(@PathVariable UUID id) {
        return service.getCurrentAddressByPersonId(id);
    }

    @GetMapping("/{id}/addresses/{typeAddress}")
    @Operation(summary = "Get address by person id and type address")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address found"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public AddressDto getAddressByPersonIdAndTypeAddress(@PathVariable UUID id,
            @PathVariable PersonAddress.TypeAddress typeAddress) {
        return service.findCurrentByPersonAndType(id, typeAddress);
    }

    @PutMapping("/{id}/addresses")
    @Operation(summary = "Update address by person id and type address")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address updated"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public AddressDto updateAddressByPersonIdAndTypeAddress(@PathVariable UUID id,
            @RequestBody PersonAddressCreateRequest request) {
        return service.updateCurrentByPersonAndType(id, request);
    }

    @DeleteMapping("/{id}/addresses/current")
    @Operation(summary = "Delete current address by person id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address deleted"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public void deleteCurrentAddressByPersonId(@PathVariable UUID id) {
        service.deleteCurrentAddressByPersonId(id);
    }

    @DeleteMapping("/{id}/addresses/{typeAddress}")
    @Operation(summary = "Delete address by person id and type address")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address deleted"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public void deleteAddressByPersonIdAndTypeAddress(@PathVariable UUID id,
            @PathVariable PersonAddress.TypeAddress typeAddress) {
        service.deleteCurrentByPersonAndType(id, typeAddress);
    }
}
