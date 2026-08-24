package com.example.Ems_Pro.Payload.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {

    private Integer locationId;
    private String address;
    private String city;
    private String state;

    private CompanyResponse company;
}
