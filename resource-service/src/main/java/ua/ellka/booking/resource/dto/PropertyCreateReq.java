package ua.ellka.booking.resource.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ua.ellka.booking.resource.model.PropertyType;

import java.math.BigDecimal;

@Data
public class PropertyCreateReq {
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    private String description;

    @NotEmpty(message = "Location cannot be empty")
    private String location;

    @Positive(message = "Price of the property must be greater than 0")
    private BigDecimal price;

    @NotEmpty(message = "Property type cannot be empty")
    private PropertyType type;

    @Positive(message = "Max number of guests must be greater than 0")
    private Integer maxGuests;
}
