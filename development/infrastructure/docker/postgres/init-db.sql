-- Create the database (run as a superuser or with sufficient privileges)
CREATE DATABASE qualicharge
    WITH
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TEMPLATE = template0;

-- Create user and grant privileges (optional)
DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles WHERE rolname = 'qualicharge_user'
   ) THEN
      CREATE ROLE qualicharge_user LOGIN PASSWORD 'guv6cDugCPRzG7T';
   END IF;
END
$do$;

GRANT ALL PRIVILEGES ON DATABASE qualicharge TO qualicharge_user;

-- Connect to the qualicharge database before running the following:
--\c qualicharge

-- Create example table
--CREATE TABLE IF NOT EXISTS locks (
--    id BIGSERIAL PRIMARY KEY,
--    lock_key VARCHAR(255) NOT NULL UNIQUE,
--    lock_value VARCHAR(255) NOT NULL,
--    expiry_time TIMESTAMPTZ NOT NULL
--);
