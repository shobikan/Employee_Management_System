package org.erp.employee_management.repository;

import lombok.RequiredArgsConstructor;
import org.erp.employee_management.DTO.EmployeeDTO;
import org.erp.employee_management.DTO.EmployeeResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;

    public EmployeeResponse create(EmployeeDTO employee) {
        String sql = """
            INSERT INTO Employees (first_name, last_name, email, phone, department, salary)
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING id, first_name, last_name, email, phone, department, salary
            """;

        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> mapToEmployeeResponse(rs),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getDepartment(),
                employee.getSalary()
        );
    }

    public List<EmployeeResponse> findAll(int page, int size) {
        String sql = """
            SELECT id, first_name, last_name, email, phone, department, salary
            FROM Employees
            ORDER BY id
            LIMIT ? OFFSET ?
            """;

        List<EmployeeResponse> responses = jdbcTemplate.query(sql,
                (rs, rowNum) -> mapToEmployeeResponse(rs),
                size, (page-1) * size
        );
        System.out.println("Responses: " + responses);
        return responses;
    }

    public Optional<EmployeeResponse> findById(Long id) {
        String sql = """
            SELECT id, first_name, last_name, email, phone, department, salary
            FROM Employees
            WHERE id = ?
            """;

        try {
            EmployeeResponse employeeResponse = jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> mapToEmployeeResponse(rs),
                    id
            );
            return Optional.ofNullable(employeeResponse);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<EmployeeResponse> update(Long id, EmployeeDTO employee) {
        String sql = """
            UPDATE Employees
            SET first_name = ?, last_name = ?, email = ?, phone = ?, department = ?, salary = ?
            WHERE id = ?
            RETURNING id, first_name, last_name, email, phone, department, salary
            """;

        try {
            EmployeeResponse updatedEmployee = jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> mapToEmployeeResponse(rs),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getEmail(),
                    employee.getPhone(),
                    employee.getDepartment(),
                    employee.getSalary(),
                    id
            );
            return Optional.ofNullable(updatedEmployee);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM Employees WHERE id = ?";
        try {
            return jdbcTemplate.update(sql, id) > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM Employees WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }


    private EmployeeResponse mapToEmployeeResponse(ResultSet rs) throws SQLException {
        return EmployeeResponse.builder()
                .id(rs.getLong("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .email(rs.getString("email"))
                .phone(rs.getString("phone"))
                .department(rs.getString("department"))
                .salary(rs.getBigDecimal("salary"))
                .build();
    }
}
