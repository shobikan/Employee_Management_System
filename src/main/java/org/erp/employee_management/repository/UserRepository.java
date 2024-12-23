package org.erp.employee_management.repository;

import lombok.RequiredArgsConstructor;
import org.erp.employee_management.DTO.UserRequest;
import org.erp.employee_management.DTO.UserResponse;
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

    public UserResponse create(UserRequest user) {
        String sql = """
            INSERT INTO users (username, password, role)
            VALUES (?, ?, ?)
            RETURNING id, username, role
            """;

        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> UserResponse.builder()
                        .id(rs.getLong("id"))
                        .username(rs.getString("username"))
                        .role(Role.valueOf(rs.getString("role")))
                        .build(),
                user.getUsername(),
                user.getPassword(),
                user.getRole().name()
        );
    }

    public Optional<UserResponse> update(String username, UserRequest user) {
        String sql = """
            UPDATE users
            SET username = ?, password = ?, role = ?
            WHERE username = ?
            RETURNING id, username, role
            """;

        try {
            UserResponse updatedUser = jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> UserResponse.builder()
                            .id(rs.getLong("id"))
                            .username(rs.getString("username"))
                            .role(Role.valueOf(rs.getString("role")))
                            .build(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole().name(),
                    username
            );
            return Optional.ofNullable(updatedUser);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Boolean delete(String username) {
        String sql = "DELETE FROM users WHERE username = ? ";

        try {
            return jdbcTemplate.update(sql, username) > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }

    }

    public Boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

}

