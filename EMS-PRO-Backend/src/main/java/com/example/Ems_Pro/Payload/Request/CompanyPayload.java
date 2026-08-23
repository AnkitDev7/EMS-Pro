package com.example.Ems_Pro.Payload.Request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyPayload {

    @NotBlank(message = "Company ID can not be empty or blank")
    @Size(max = 50, message = "Company ID must not exceed 50 characters")
    private String companyId;

    @NotBlank(message = "Company Name can not be empty or blank")
    @Size(min = 2, max = 50,
            message = "Company Name must be between 2 and 50 characters")
    private String companyName;


    @NotBlank(message = "Company Address can not be empty or blank")
    @Size(min = 5, max = 300,
            message = "Company Address must be between 5 and 300 characters")
    private String companyAddress;


    @NotBlank(message = "Company Email can not be empty or blank")
    @Email(message = "Please provide a valid company email")
    @Size(max = 150,
            message = "Company Email must not exceed 150 characters")
    private String companyEmail;


    @NotBlank(message = "Company Phone Number can not be empty or blank")
    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Company Phone Number must contain 10 to 15 digits"
    )
    private String companyPhoneNo;

}
