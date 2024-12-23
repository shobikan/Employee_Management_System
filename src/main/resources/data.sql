-- Insert admin user (password is 'admin123' encrypted with BCrypt)
INSERT INTO users (username, password, role)
VALUES (
           'admin1',
           '$2a$12$DNY0klbxhv3ifSTokucEj.s3KsOdk4fYKLwlzvUXubis57rEPspp6',
           'ADMIN'
       ) ON CONFLICT DO NOTHING;

-- Insert regular user (password is 'user123' encrypted with BCrypt)
INSERT INTO users (username, password, role)
VALUES (
           'user1',
           '$2a$12$aRHCMSLLxsJ18q3RJ7JsaeJOYbFGflqP4p4RaFm9JCMUmefEBRwCK',
           'USER'
       ) ON CONFLICT DO NOTHING;

-- Insert sample employees
INSERT INTO employees (first_name, last_name, email, phone, department, salary)
VALUES
    ('John', 'Doe', 'john.doe@erp.com', '1234567890', 'IT', 75000.00),
    ('Jane', 'Smith', 'jane.smith@erp.com', '0987654321', 'HR', 65000.00)
    ON CONFLICT DO NOTHING;