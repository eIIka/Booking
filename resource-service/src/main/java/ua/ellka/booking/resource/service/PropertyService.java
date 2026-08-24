package ua.ellka.booking.resource.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ellka.booking.resource.mapper.PropertyMapper;
import ua.ellka.booking.resource.dto.PropertyCreateReq;
import ua.ellka.booking.resource.dto.PropertyResp;
import ua.ellka.booking.resource.exception.NotFoundServiceException;
import ua.ellka.booking.resource.model.Property;
import ua.ellka.booking.resource.repo.PropertyRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PropertyService {
    private final PropertyRepo propertyRepo;
    private final PropertyMapper propertyMapper;

    @Transactional
    public PropertyResp create(PropertyCreateReq req) {
        Property property = propertyMapper.toEntity(req);
        Property save = propertyRepo.save(property);
        return propertyMapper.toDto(save);
    }

    public PropertyResp findById(Long id) {
        Property property = propertyRepo.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Property with id " + id + " not found"));

        if (!property.getActive()) {
            throw new NotFoundServiceException("Property with id " + id + " not found");
        }

        return propertyMapper.toDto(property);
    }

    public List<PropertyResp> findAll() {
        return propertyRepo.findAllByActive(true).stream()
                .map(propertyMapper::toDto)
                .toList();
    }

    public List<PropertyResp> findAllByLocation(String location) {
        if (location.isBlank()) {
            throw new IllegalArgumentException("Location is blank");
        }

        return propertyRepo.findAllByLocationIgnoreCaseAndActive(location, true).stream()
                .map(propertyMapper::toDto)
                .toList();
    }

    @Transactional
    public PropertyResp deactivate(Long id) {
        Property property = propertyRepo.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Property with id " + id + " not found"));

        if (!property.getActive()) {
            throw new IllegalArgumentException("Property is already deactivated");
        }

        property.setActive(false);

        return propertyMapper.toDto(property);
    }
}
