-- Create the database (run as a superuser or with sufficient privileges)
CREATE DATABASE carbon_relay
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
      SELECT FROM pg_catalog.pg_roles WHERE rolname = 'carbon_relay_user'
   ) THEN
      CREATE ROLE carbon_relay_user LOGIN PASSWORD 'Bql796q98CKdUrF';
   END IF;
END
$do$;

GRANT ALL PRIVILEGES ON DATABASE carbon_relay TO carbon_relay_user;

-- Connect to the carbon_relay database before running the following:
--\c carbon_relay

-- Create example table
--CREATE TABLE IF NOT EXISTS locks (
--    id BIGSERIAL PRIMARY KEY,
--    lock_key VARCHAR(255) NOT NULL UNIQUE,
--    lock_value VARCHAR(255) NOT NULL,
--    expiry_time TIMESTAMPTZ NOT NULL
--);
