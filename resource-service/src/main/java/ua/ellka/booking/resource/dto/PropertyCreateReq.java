package ua.ellka.booking.resource.dto;

import lombok.Data;
import ua.ellka.booking.resource.model.PropertyType;

import java.math.BigDecimal;

@Data
public class PropertyCreateReq {
    private String name;
    private String description;
    private String location;
    private BigDecimal price;
    private PropertyType type;
    private Integer maxGuests;
}
