package com.example.Ems_Pro.Payload.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationPayload {

    private Long locationId;

    private String city;

    private String address;

    private  String state;

    private String companyId;;
}
