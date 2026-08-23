package ua.ellka.booking.resource.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import ua.ellka.booking.resource.mapper.PropertyMapper;
import ua.ellka.booking.resource.dto.PropertyCreateReq;
import ua.ellka.booking.resource.dto.PropertyResp;
import ua.ellka.booking.resource.exception.NotFoundServiceException;
import ua.ellka.booking.resource.model.Property;
import ua.ellka.booking.resource.repo.PropertyRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepo propertyRepo;
    private final PropertyMapper propertyMapper;

    public PropertyResp create(PropertyCreateReq req) {
        Property property = propertyMapper.toEntity(req);
        Property save = propertyRepo.save(property);
        return propertyMapper.toDto(save);
    }

    public PropertyResp findById (Long id) {
        Property property = propertyRepo.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Property with id " + id + " not found"));
        return propertyMapper.toDto(property);
    }

    public List<PropertyResp> findAll() {
        return propertyRepo.findAllByActive(true).stream()
                .map(propertyMapper::toDto)
                .toList();
    }

    public List<PropertyResp> findAllByLocation(String location) {
        if (location.isBlank()) {
            throw new ValidationException("Location is blank");
        }

        return propertyRepo.findAllByLocationAndActive(location, true).stream()
                .map(propertyMapper::toDto)
                .toList();
    }

    public PropertyResp deactivate(Long id) {
        Property property = propertyRepo.findById(id)
                .orElseThrow(() -> new NotFoundServiceException("Property with id " + id + " not found"));

        if(property.getActive().equals(false)) {
            throw new ValidationException("Property is already deactivated");
        }

        property.setActive(false);
        Property save = propertyRepo.save(property);
        return propertyMapper.toDto(save);
    }
}
