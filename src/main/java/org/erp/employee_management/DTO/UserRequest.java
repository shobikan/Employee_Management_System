package org.erp.employee_management.DTO;

import lombok.Builder;
import lombok.Data;
import org.erp.employee_management.Enum.Role;

@Data
@Builder
public class UserRequest {
    private String username;
    private String password;
    private Role role;
}
