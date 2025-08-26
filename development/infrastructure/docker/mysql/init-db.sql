-- Create the database
CREATE DATABASE IF NOT EXISTS qualicharge CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create a user with privileges (optional, if you want to avoid using root)
CREATE USER IF NOT EXISTS 'qualicharge_user'@'%' IDENTIFIED BY 'guv6cDugCPRzG7T';
GRANT ALL PRIVILEGES ON qualicharge.* TO 'qualicharge_user'@'%';
FLUSH PRIVILEGES;

-- Use the database
USE qualicharge;

-- Example table (you can add your actual schema here)
CREATE TABLE IF NOT EXISTS locks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lock_key VARCHAR(255) NOT NULL UNIQUE,
    lock_value VARCHAR(255) NOT NULL,
    expiry_time TIMESTAMP NOT NULL
);

-- You can add other table creation or insert statements here
