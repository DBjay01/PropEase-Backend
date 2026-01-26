package com.property.propertybooking.dto;

import java.math.BigDecimal;
import com.property.propertybooking.entity.PropertyStatus;
import com.property.propertybooking.entity.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyRequest {
	
	@NotBlank
    private String title;

    private String description;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    private String pincode;
    
    @NotNull
    private PropertyType propertyType;

    private PropertyStatus status;
}
