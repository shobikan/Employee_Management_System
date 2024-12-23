-- Insert admin user (password is 'admin123' encrypted with BCrypt)
INSERT INTO users (username, password, role)
VALUES (
           'admin',
           '$2a$10$ZU.P0mFHFZ5KVXXw8yqyKeBHXf6ixDxGt.RzYT9UZVtVA5fZ5K6Y2',
           'ADMIN'
       ) ON CONFLICT DO NOTHING;

-- Insert regular user (password is 'user123' encrypted with BCrypt)
INSERT INTO users (username, password, role)
VALUES (
           'user',
           '$2a$10$ZryI0B7ZU.P0mFHFZ5KVXXw8yqyKeBHXf6ixDxGt.RzYT9UZVtVA5',
           'USER'
       ) ON CONFLICT DO NOTHING;

-- Insert sample employees
INSERT INTO employees (first_name, last_name, email, phone, department, salary)
VALUES
    ('John', 'Doe', 'john.doe@erp.com', '1234567890', 'IT', 75000.00),
    ('Jane', 'Smith', 'jane.smith@erp.com', '0987654321', 'HR', 65000.00)
    ON CONFLICT DO NOTHING;