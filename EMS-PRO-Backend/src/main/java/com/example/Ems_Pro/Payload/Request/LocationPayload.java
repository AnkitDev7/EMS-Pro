package com.example.Ems_Pro.Payload.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationPayload {

    private Long locationId;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z ]+$",
            message = "City can contain only letters and spaces"
    )
    private String city;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 300, message = "Address must be between 5 and 300 characters")
    private String address;

    @NotBlank(message = "State is required")
    @Size(min = 2, max = 50, message = "State must be between 2 and 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z ]+$",
            message = "State can contain only letters and spaces"
    )
    private String state;

    @NotBlank(message = "Company ID is required")
    @Size(min = 3, max = 20, message = "Company ID must be between 3 and 20 characters")
    @Pattern(
            regexp = "^[A-Z0-9]+$",
            message = "Company ID can contain only uppercase letters and numbers"
    )
    private String companyId;
}