package ua.ellka.booking.resource.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.ellka.booking.resource.dto.PropertyCreateReq;
import ua.ellka.booking.resource.dto.PropertyResp;
import ua.ellka.booking.resource.model.Property;

@Mapper(componentModel = "spring")
public interface PropertyMapper {
    PropertyResp toDto(Property dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "averageRating", constant = "0.0")
    @Mapping(target = "reviewCount", constant = "0")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Property toEntity(PropertyCreateReq req);
}
