package org.erp.employee_management.service;

import lombok.RequiredArgsConstructor;
import org.erp.employee_management.DTO.EmployeeDTO;
import org.erp.employee_management.DTO.EmployeeResponse;
import org.erp.employee_management.exception.ValidationException;
import org.erp.employee_management.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeResponse createEmployee(EmployeeDTO employee) {
        validateEmployee(employee);
        return employeeRepository.create(employee);
    }

    public List<EmployeeResponse> getAllEmployees(int page, int size) {
        return employeeRepository.findAll(page, size);
    }

    public Optional<EmployeeResponse> getEmployee(Long id) {
        return employeeRepository.findById(id);
    }

    public Optional<EmployeeResponse> updateEmployee(Long id, EmployeeDTO employee) {
        validateEmployee(employee);
        return employeeRepository.update(id, employee);
    }

    public boolean deleteEmployee(Long id) {
        return employeeRepository.delete(id);
    }

    private void validateEmployee(EmployeeDTO employee) {
        List<String> errors = new ArrayList<>();

        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
            errors.add("First name is required");
        }
        if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
            errors.add("Last name is required");
        }
        if (employee.getEmail() == null || !employee.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.add("Valid email is required");
        }
        if (employee.getPhone() == null || !employee.getPhone().matches("^\\d{10}$")) {
            errors.add("Valid phone number is required");
        }
        if (employee.getDepartment() == null || employee.getDepartment().trim().isEmpty()) {
            errors.add("Department is required");
        }
        if (employee.getSalary() != null && employee.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            errors.add("Salary cannot be negative");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(String.join(", ", errors));
        }
    }
}

