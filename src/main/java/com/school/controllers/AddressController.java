package com.school.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.school.controllers.dto.address.AddressCreateRequest;
import com.school.controllers.dto.address.AddressDto;
import com.school.services.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/addresses")
@Validated
@RequiredArgsConstructor
@Tag(name = "Address", description = "Address management")
@SecurityRequirement(name = "oauth2")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    @Operation(summary = "Create a new address", description = "Create a new address")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request") })
    @ResponseStatus(HttpStatus.OK)
    public AddressDto createAddress(@RequestBody AddressCreateRequest addressCreateRequest) {
        return addressService.createAddress(addressCreateRequest);
    }

    @GetMapping
    @Operation(summary = "Get all addresses", description = "Get all addresses")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Addresses found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request") })
    @ResponseStatus(HttpStatus.OK)
    public Page<AddressDto> getAllAddresses(@PathVariable Pageable pageable) {
        return addressService.getAllAddresses(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an address by ID", description = "Get an address by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request") })
    @ResponseStatus(HttpStatus.OK)
    public AddressDto getAddressById(@PathVariable UUID id) {
        return addressService.getAddressById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an address by ID", description = "Update an address by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request") })
    @ResponseStatus(HttpStatus.OK)
    public AddressDto updateAddress(@PathVariable UUID id,
            @RequestBody AddressCreateRequest addressCreateRequest) {
        return addressService.updateAddress(id, addressCreateRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an address by ID", description = "Delete an address by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Address deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request") })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
    }

}
