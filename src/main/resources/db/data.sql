-- Insert admin user (password is 'admin' encrypted with BCrypt)
INSERT INTO users (username, password, role)
VALUES (
           'admin',
           '$2a$12$Ku0DIlwY2J8MN/VUmd7c..OXkt9BkGEdo/jXy4UcC8VJDpYu8npd2',
           'ADMIN'
       ) ON CONFLICT DO NOTHING;

