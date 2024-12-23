package org.erp.employee_management.DTO;

import lombok.Builder;
import lombok.Data;
import org.erp.employee_management.Enum.Role;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private Role role;
}
