package ua.ellka.booking.resource.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.ellka.booking.resource.dto.PropertyCreateReq;
import ua.ellka.booking.resource.dto.PropertyResp;
import ua.ellka.booking.resource.service.PropertyService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyResp create(@RequestBody @Valid PropertyCreateReq req) {
        return propertyService.create(req);
    }

    @GetMapping("/{id}")
    public PropertyResp getById(@PathVariable Long id) {
        return propertyService.findById(id);
    }

    @GetMapping
    public List<PropertyResp> getAll() {
        return propertyService.findAll();
    }
}
