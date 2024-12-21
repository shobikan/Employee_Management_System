package org.erp.employee_management.repository;

import lombok.RequiredArgsConstructor;
import org.erp.employee_management.Enum.Role;
import org.erp.employee_management.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<User> findByUsername(String username) {
        String sql = """
            SELECT id, username, password, role 
            FROM users 
            WHERE username = ?
            """;

        try {
            User user = jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> User.builder()
                            .id(rs.getLong("id"))
                            .username(rs.getString("username"))
                            .password(rs.getString("password"))
                            .role(Role.valueOf(rs.getString("role")))
                            .build(),
                    username
            );
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
