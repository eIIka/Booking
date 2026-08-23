package ua.ellka.booking.resource.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PropertyResp> create(@RequestBody @Valid PropertyCreateReq req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.create(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyResp> getById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PropertyResp>> getAll() {
        return ResponseEntity.ok(propertyService.findAll());
    }

    @GetMapping("/{location}")
    public ResponseEntity<List<PropertyResp>> getByLocation(@PathVariable String location) {
        return ResponseEntity.ok(propertyService.findAllByLocation(location));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PropertyResp> deactivate(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.deactivate(id));
    }
}
