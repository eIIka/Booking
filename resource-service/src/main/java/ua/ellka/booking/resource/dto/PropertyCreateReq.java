package ua.ellka.booking.resource.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ua.ellka.booking.resource.model.PropertyType;

import java.math.BigDecimal;

@Data
public class PropertyCreateReq {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String description;

    @NotBlank(message = "Location cannot be empty")
    private String location;

    @NotNull
    @Positive(message = "Price of the property must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Property type cannot be empty")
    private PropertyType type;

    @NotNull
    @Positive(message = "Max number of guests must be greater than 0")
    private Integer maxGuests;
}
