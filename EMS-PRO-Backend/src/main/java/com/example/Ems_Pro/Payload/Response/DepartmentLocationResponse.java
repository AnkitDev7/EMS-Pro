package com.example.Ems_Pro.Payload.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentLocationResponse {

    private String departmentId;

    private Integer locationId;

    private String address;

    private String city;

    private String state;
}
