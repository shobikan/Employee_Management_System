package org.erp.employee_management.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String department;
    private BigDecimal salary;
}
