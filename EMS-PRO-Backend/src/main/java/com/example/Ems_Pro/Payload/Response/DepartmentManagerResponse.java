package com.example.Ems_Pro.Payload.Response;
import lombok.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DepartmentManagerResponse {

    private String departmentId;
    private String departmentName;

    private String userId;
    private String userName;

    private LocalDate assignedDate;
    private LocalDate endDate;
}
