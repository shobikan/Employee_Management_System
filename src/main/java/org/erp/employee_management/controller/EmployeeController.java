package org.erp.employee_management.controller;

import lombok.RequiredArgsConstructor;
import org.erp.employee_management.DTO.EmployeeDTO;
import org.erp.employee_management.DTO.EmployeeResponse;
import org.erp.employee_management.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeDTO employee) {
        return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(employeeService.getAllEmployees(page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id) {
        return employeeService.getEmployee(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeDTO employee) {
        return employeeService.updateEmployee(id, employee)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable Long id) {
        if (employeeService.deleteEmployee(id)) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
    }
}
