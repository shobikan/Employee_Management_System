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
            INSERT INTO employees (first_name, last_name, email, phone, department, salary)
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
            FROM employees
            ORDER BY id
            LIMIT ? OFFSET ?
            """;

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> mapToEmployeeResponse(rs),
                size, page * size
        );
    }

    public Optional<EmployeeResponse> findById(Long id) {
        String sql = """
            SELECT id, first_name, last_name, email, phone, department, salary
            FROM employees
            WHERE id = ?
            """;

        try {
            EmployeeResponse employee = jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> mapToEmployeeResponse(rs),
                    id
            );
            return Optional.ofNullable(employee);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<EmployeeResponse> update(Long id, EmployeeDTO employee) {
        String sql = """
            UPDATE employees
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
        String sql = "DELETE FROM employees WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
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
