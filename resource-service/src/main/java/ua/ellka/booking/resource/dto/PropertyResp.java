package ua.ellka.booking.resource.dto;

import lombok.Data;
import ua.ellka.booking.resource.model.PropertyType;

import java.math.BigDecimal;

@Data
public class PropertyResp {
    private Long id;
    private String name;
    private String location;
    private PropertyType type;
    private BigDecimal price;
    private Integer maxGuests;
    private Double averageRating;
    private Integer reviewCount;
}
