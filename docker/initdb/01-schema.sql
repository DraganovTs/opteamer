-- Drop tables if they exist
DROP TABLE IF EXISTS opType_assets CASCADE;  -- Ensures existing tables are dropped
DROP TABLE IF EXISTS assessments CASCADE;
DROP TABLE IF EXISTS operation_reports CASCADE;
DROP TABLE IF EXISTS optype_pre_op_a CASCADE;
DROP TABLE IF EXISTS optype_opproviders CASCADE;
DROP TABLE IF EXISTS operations CASCADE;
DROP TABLE IF EXISTS room_inventories CASCADE;
DROP TABLE IF EXISTS team_members CASCADE;
DROP TABLE IF EXISTS operation_rooms CASCADE;
DROP TABLE IF EXISTS inventories CASCADE;
DROP TABLE IF EXISTS operation_types CASCADE;
DROP TABLE IF EXISTS operation_providers CASCADE;
DROP TABLE IF EXISTS patients CASCADE;
DROP TABLE IF EXISTS pre_operative_assessments CASCADE;
DROP TABLE IF EXISTS assets CASCADE;
DROP TABLE IF EXISTS privileges CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS role_privileges CASCADE;
DROP TABLE IF EXISTS optype_team_member CASCADE;
DROP TABLE IF EXISTS op_type_assets CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;

-- Table for Assets
CREATE TABLE IF NOT EXISTS assets (
                                      id BIGSERIAL PRIMARY KEY,
                                      type VARCHAR(50),
                                      name VARCHAR(100) NOT NULL
);

-- Table for Operation Types
CREATE TABLE IF NOT EXISTS operation_types (
                                               name VARCHAR(50) PRIMARY KEY,
                                               room_type VARCHAR(50),
                                               duration_hours INT
);

-- Table for Pre-Operative Assessments
CREATE TABLE IF NOT EXISTS pre_operative_assessments (
                                                         name VARCHAR(50) PRIMARY KEY
);

-- Table for Patients
CREATE TABLE IF NOT EXISTS patients (
                                        id BIGSERIAL PRIMARY KEY,
                                        name VARCHAR(255),
                                        nin VARCHAR(20) UNIQUE NOT NULL
);

-- Table for Operation Providers
CREATE TABLE IF NOT EXISTS operation_providers (
                                                   type VARCHAR(50) PRIMARY KEY
);

-- Table for Inventories
CREATE TABLE IF NOT EXISTS inventories (
                                           asset_id BIGINT PRIMARY KEY,
                                           count INT,
                                           FOREIGN KEY (asset_id) REFERENCES assets (id) ON DELETE CASCADE
);

-- Table for Operation Rooms
CREATE TABLE IF NOT EXISTS operation_rooms (
                                               id BIGSERIAL PRIMARY KEY,
                                               room_nr INT,
                                               building_block VARCHAR(100),
                                               floor VARCHAR(50),
                                               type VARCHAR(50),
                                               state VARCHAR(50)
);

-- Table for Team Members
CREATE TABLE IF NOT EXISTS team_members (
                                            id BIGSERIAL PRIMARY KEY,
                                            name VARCHAR(255),
                                            opprovider_id VARCHAR,
                                            FOREIGN KEY (opprovider_id) REFERENCES operation_providers (type)
);

-- Table for Room Inventories
CREATE TABLE IF NOT EXISTS room_inventories (
                                                room_id BIGINT,
                                                asset_id BIGINT,
                                                count INT,
                                                PRIMARY KEY (room_id, asset_id),
                                                FOREIGN KEY (room_id) REFERENCES operation_rooms (id),
                                                FOREIGN KEY (asset_id) REFERENCES assets (id)
);

-- Table for Operations
CREATE TABLE IF NOT EXISTS operations (
                                          id BIGSERIAL PRIMARY KEY,
                                          type_id VARCHAR(50),
                                          room_id BIGINT,
                                          patient_id BIGINT,
                                          state VARCHAR(50),
                                          start_date TIMESTAMP,
                                          FOREIGN KEY (type_id) REFERENCES operation_types (name),
                                          FOREIGN KEY (room_id) REFERENCES operation_rooms (id),
                                          FOREIGN KEY (patient_id) REFERENCES patients (id)
);

-- Table for Operation Type Providers Mapping
CREATE TABLE IF NOT EXISTS optype_opproviders (
                                                  optype_id VARCHAR(50),
                                                  opprovider_id VARCHAR(50),
                                                  PRIMARY KEY (optype_id, opprovider_id),
                                                  FOREIGN KEY (optype_id) REFERENCES operation_types (name),
                                                  FOREIGN KEY (opprovider_id) REFERENCES operation_providers (type)
);

-- Table for Operation Type Pre-Operative Assessments Mapping
CREATE TABLE IF NOT EXISTS optype_pre_op_a (
                                               optype_id VARCHAR(50),
                                               pre_op_a_id VARCHAR(50),
                                               PRIMARY KEY (optype_id, pre_op_a_id),
                                               FOREIGN KEY (optype_id) REFERENCES operation_types (name),
                                               FOREIGN KEY (pre_op_a_id) REFERENCES pre_operative_assessments (name)
);

-- Table for Operation Reports
CREATE TABLE IF NOT EXISTS operation_reports (
                                                 team_member_id BIGINT,
                                                 operation_id BIGINT,
                                                 report TEXT,
                                                 PRIMARY KEY (team_member_id, operation_id),
                                                 FOREIGN KEY (team_member_id) REFERENCES team_members (id),
                                                 FOREIGN KEY (operation_id) REFERENCES operations (id)
);

-- Table for Assessments
CREATE TABLE IF NOT EXISTS assessments (
                                           team_member_id BIGINT,
                                           patient_id BIGINT,
                                           pre_op_a_id VARCHAR(50),
                                           start_date TIMESTAMP,
                                           PRIMARY KEY (team_member_id, patient_id, pre_op_a_id),
                                           FOREIGN KEY (team_member_id) REFERENCES team_members (id),
                                           FOREIGN KEY (patient_id) REFERENCES patients (id),
                                           FOREIGN KEY (pre_op_a_id) REFERENCES pre_operative_assessments (name)
);

-- Table for Roles
CREATE TABLE IF NOT EXISTS roles (
                                     id BIGSERIAL PRIMARY KEY,
                                     name VARCHAR(20) UNIQUE
);

-- Table for Privileges
CREATE TABLE IF NOT EXISTS privileges (
                                          id BIGSERIAL PRIMARY KEY,
                                          name VARCHAR(255)
);

-- Table for Role-Privileges Mapping
CREATE TABLE IF NOT EXISTS role_privileges (
                                               role_id BIGINT,
                                               privilege_id BIGINT,
                                               PRIMARY KEY (role_id, privilege_id),
                                               FOREIGN KEY (role_id) REFERENCES roles (id),
                                               FOREIGN KEY (privilege_id) REFERENCES privileges (id)
);

-- Table for Operation Type Assets Mapping
CREATE TABLE IF NOT EXISTS opType_assets (
                                             optype_id VARCHAR NOT NULL,
                                             asset_id BIGINT NOT NULL,
                                             PRIMARY KEY (optype_id, asset_id),
                                             FOREIGN KEY (optype_id) REFERENCES operation_types(name),  -- Make sure this matches the primary key in operation_types
                                             FOREIGN KEY (asset_id) REFERENCES assets(id)               -- Make sure this matches the primary key in assets
);

CREATE TABLE optype_team_member (
                                    operation_id INT NOT NULL,
                                    team_member_id INT NOT NULL,
                                    PRIMARY KEY (operation_id, team_member_id),
                                    FOREIGN KEY (operation_id) REFERENCES operations(id) ON DELETE CASCADE,
                                    FOREIGN KEY (team_member_id) REFERENCES team_members(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS op_type_assets (
                                              op_type_id VARCHAR(50) REFERENCES operation_types(name) ON DELETE CASCADE,
                                              asset_id BIGINT REFERENCES assets(id) ON DELETE CASCADE,
                                              PRIMARY KEY (op_type_id, asset_id)
);

CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     email VARCHAR(100) UNIQUE NOT NULL,
                                     password VARCHAR(100) NOT NULL,
                                     username VARCHAR(50) UNIQUE NOT NULL
);


CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id BIGINT,
                                          role_id BIGINT,
                                          PRIMARY KEY (user_id, role_id),
                                          FOREIGN KEY (user_id) REFERENCES users (id),  -- Assuming you have a users table
                                          FOREIGN KEY (role_id) REFERENCES roles (id)
);
