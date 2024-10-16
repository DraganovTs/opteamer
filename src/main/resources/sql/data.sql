INSERT INTO assets (type, name)
VALUES
    ('SURGICAL_INSTRUMENT', 'SCALPEL'),
    ('SURGICAL_INSTRUMENT', 'SCISSORS'),
    ('SURGICAL_INSTRUMENT', 'FORCEPS'),
    ('SURGICAL_INSTRUMENT', 'CLAMPS'),
    ('MACHINE', 'MONITOR'),
    ('MACHINE', 'ANESTESIA MACHINE'),
    ('EQUIPMENT', 'TABLE'),
    ('EQUIPMENT', 'OPERATIONAL LIGHT'),
    ('EQUIPMENT', 'ELECTROSURGICAL UNIT'),
    ('OTHER_ESSENTIAL_TOOL', 'THERMOMETER'),
    ('OTHER_ESSENTIAL_TOOL', 'HYPODERMIC NEEDLE'),
    ('MACHINE', 'STERILIZER'),
    ('OTHER_ESSENTIAL_TOOL', 'PULSE OXIMETRY');

INSERT INTO inventories (asset_id, count)
VALUES
    (1, 150),
    (2, 120),
    (3, 100),
    (4, 55),
    (5, 255),
    (6, 25),
    (7, 150),
    (8, 350),
    (9, 50),
    (10, 160),
    (11, 560),
    (12, 22);

INSERT INTO pre_operative_assessments
VALUES
('Medical History Review'),
('Physical Examination'),
('Blood Tests'),
('Cardiac Assessment'),
('Nutritional Assessment'),
('Anesthetic Assessment'),
('Infection Screening'),
('Psychological and Social Assessment');

INSERT INTO patients (name, nin)
VALUES
    ('John Smith', '7534628190'),
    ('Emily Johnson', '9283746150'),
    ('Michael Williams', '6342908571'),
    ('Sophia Brown', '8176452309'),
    ('Daniel Davis', '4521368792'),
    ('Olivia Wilson', '3102895476'),
    ('James Martinez', '5201984637'),
    ('Isabella Anderson', '7498231056'),
    ('Benjamin Taylor', '1948372650'),
    ('Charlotte Thomas', '8361947205');


INSERT INTO operation_providers (type)
VALUES
    ('SURGEON'),
    ('ANESTHESIOLOGIST'),
    ('CERTIFIED_NURSE'),
    ('CP_ROOM_NURSE'),
    ('SURGICAL_TECH');


INSERT INTO operation_rooms
(room_nr, building_block, floor, type, state) VALUES

    (101, 'Main Block', '1', 'GENERAL_SURGERY', 'STERILE'),
    (102, 'Main Block', '2', 'CARDIAC_SURGERY', 'MAINTENANCE'),
    (103, 'East Wing', '1', 'NEURO_SURGERY', 'UNDER_STERILISATION'),
    (104, 'West Wing', '2', 'PLASTIC_SURGERY', 'AWAITING_CLEANING'),
    (105, 'Main Block', '1', 'EMERGENCY_SURGERY', 'NOT_STERILE'),
    (106, 'Main Block', '3', 'GENERAL_SURGERY', 'MAINTENANCE'),
    (107, 'South Wing', '1', 'CARDIAC_SURGERY', 'STERILE'),
    (108, 'Main Block', '2', 'NEURO_SURGERY', 'AWAITING_CLEANING'),
    (109, 'East Wing', '3', 'PLASTIC_SURGERY', 'UNDER_STERILISATION'),
    (110, 'West Wing', '2', 'EMERGENCY_SURGERY', 'NOT_STERILE');

INSERT INTO team_members (id, name, opprovider_id) VALUES
    (1, 'Alice Johnson', 'SURGEON'),
    (2, 'Robert Smith', 'ANESTHESIOLOGIST'),
    (3, 'Emily Davis', 'CERTIFIED_NURSE'),
    (4, 'Michael Brown', 'CP_ROOM_NURSE'),
    (5, 'Jessica Taylor', 'SURGICAL_TECH'),
    (6, 'James Wilson', 'SURGEON'),
    (7, 'Linda Martinez', 'ANESTHESIOLOGIST'),
    (8, 'David Garcia', 'CERTIFIED_NURSE'),
    (9, 'Susan Anderson', 'CP_ROOM_NURSE'),
    (10, 'Charles Rodriguez', 'SURGICAL_TECH');


INSERT INTO room_inventories (room_id,  asset_id, count)
VALUES
    (1,  1, 5),
    (2,  2, 10),
    (3,  3, 12),
    (4,  4, 8),
    (5,  5, 4),
    (6, 6, 3),
    (7, 7, 6),
    (8, 8, 2),
    (9, 9, 1),
    (10, 10, 15)
;


INSERT INTO operation_types (name, room_type, duration_hours)
VALUES
    ('Appendectomy', 'GENERAL_SURGERY', 2),
    ('Cholecystectomy', 'GENERAL_SURGERY', 1),
    ('Hernia Repair', 'GENERAL_SURGERY', 1),
    ('Spinal Fusion', 'NEURO_SURGERY', 5),
    ('Gallbladder Removal', 'GENERAL_SURGERY', 2),
    ('Plastic Surgery', 'PLASTIC_SURGERY', 3),
    ('Coronary Bypass', 'CARDIAC_SURGERY', 4),
    ('Laparoscopic Surgery', 'GENERAL_SURGERY', 3);


-- Insert Operation Type Assets
INSERT INTO op_type_assets (op_type_id, asset_id)
VALUES

    (1, 2),
    (1, 1),
    (2, 3),
    (2, 4),
    (3, 10),
    (3, 9),
    (4, 12),
    (4, 13),
    (5, 4),
    (5, 1),
    (6, 5),
    (6, 6),
    (7, 5),
    (7, 2),
    (8, 3),
    (8, 8);


INSERT INTO optype_pre_op_a (optype_id, pre_op_a_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 4),
    (2, 3),
    (3, 3),
    (3, 5),
    (4, 7),
    (4, 6),
    (5, 1),
    (5, 5),
    (6, 4),
    (6, 8),
    (7, 2),
    (7, 3),
    (8, 5),
    (8, 9);


-- Insert operations
INSERT INTO operations (type_id, room_id, start_date, state)
VALUES
    ('Appendectomy', 'GENERAL_SURGERY', '2024-10-15 08:00:00', 'SCHEDULED'),
    ('Cholecystectomy', 'GENERAL_SURGERY', '2024-10-16 09:00:00', 'READY_TO_BEGIN'),
    ('Hernia Repair', 'GENERAL_SURGERY', '2024-10-17 10:00:00', 'SCHEDULED'),
    ('Spinal Fusion', 'NEURO_SURGERY', '2024-10-18 11:00:00', 'IN_PROGRESS'),
    ('Gallbladder Removal', 'GENERAL_SURGERY', '2024-10-19 08:00:00', 'COMPLETED'),
    ('Plastic Surgery', 'PLASTIC_SURGERY', '2024-10-20 09:00:00', 'CANCELLED'),
    ('Coronary Bypass', 'CARDIAC_SURGERY', '2024-10-21 07:00:00', 'READY_TO_BEGIN'),
    ('Laparoscopic Surgery', 'GENERAL_SURGERY', '2024-10-22 08:30:00', 'SCHEDULED');


INSERT INTO optype_opproviders (optype_id, opprovider_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (2, 3),
    (3, 3),
    (3, 5),
    (4, 2),
    (4, 1),
    (5, 1),
    (5, 3),
    (6, 5),
    (6, 1),
    (7, 1),
    (7, 2),


    (8, 3),
    (8, 1);


INSERT INTO optype_pre_op_a (optype_id, pre_op_a_id)
VALUES
    (1, 'Medical History Review'),
    (1, 'Anesthetic Assessment'),
    (2, 'Physical Examination'),
    (2, 'Blood Tests'),
    (3, 'Infection Screening'),
    (3, 'Blood Tests'),
    (4, 'Nutritional Assessment'),
    (4, 'Renal Function Tests'),
    (5, 'Medical History Review'),
    (5, 'Infection Screening'),
    (6, 'Physical Examination'),
    (6, 'Psychological and Social Assessment'),
    (7, 2),
    (7, 5),
    (8, 8),
    (8, 3);


UPDATE operations
SET room_id = (
    SELECT id FROM operation_rooms WHERE type = operations.type_id AND state = 'STERILE' LIMIT 1
)
WHERE operations.type_id IN ('GENERAL_SURGERY', 'NEURO_SURGERY', 'CARDIAC_SURGERY', 'PLASTIC_SURGERY');



INSERT INTO operation_types (name, room_type, duration_hours)
VALUES
    ('Cataract Surgery', 'OPHTHALMOLOGY', 1.5),
    ('Knee Replacement', 'ORTHOPEDICS', 3)
ON CONFLICT DO NOTHING;


INSERT INTO assets (id, type, name)
VALUES
    (20, 'SURGICAL_INSTRUMENT', 'PHACOEMULSIFIER'),
    (21, 'SURGICAL_INSTRUMENT', 'MICROFORCEPS'),
    (15, 'SURGICAL_INSTRUMENT', 'BONE SAW'),
    (16, 'MACHINE', 'SURGICAL_DRILL')
ON CONFLICT DO NOTHING;


INSERT INTO operation_providers (type)
VALUES
    ('ANESTHESIOLOGIST'),
    ('OPHTHALMIC_SURGEON'),
    ('ORTHOPEDIC_SURGEON')
ON CONFLICT DO NOTHING;


INSERT INTO pre_operative_assessments (name)
VALUES
    ('Eye Examination'),
    ('Vision Assessment'),
    ('Joint X-ray'),
    ('Blood Pressure Check')
ON CONFLICT DO NOTHING;


INSERT INTO operation_rooms (id, room_nr, building_block, floor, type, state)
VALUES
    (10, 203, 'West Wing', '2', 'OPHTHALMOLOGY', 'AVAILABLE'),
    (9, 305, 'North Wing', '3', 'ORTHOPEDICS', 'AVAILABLE')
ON CONFLICT DO NOTHING;


INSERT INTO patients (id, name, nin)
VALUES
    (5, 'Elizabeth Brown', '2389017625'),
    (6, 'William Davis', '5938271045')
ON CONFLICT DO NOTHING;


INSERT INTO team_members (id, name, opprovider_id)
VALUES
    (8, 'Karen White', 'ANESTHESIOLOGIST'),
    (9, 'George Harris', 'OPHTHALMIC_SURGEON'),
    (4, 'David Miller', 'ORTHOPEDIC_SURGEON')
ON CONFLICT DO NOTHING;


INSERT INTO operations (id, type_id, room_id, patient_id, state, start_date)
VALUES
    (5, (SELECT id FROM operation_types WHERE name = 'Cataract Surgery'), 10, 5, 'SCHEDULED', '2024-10-12 14:00:00'),
    (6, (SELECT id FROM operation_types WHERE name = 'Knee Replacement'), 9, 6, 'SCHEDULED', '2024-10-12 15:00:00')
ON CONFLICT DO NOTHING;


INSERT INTO optype_team_member (operation_id, team_member_id)
VALUES
    (5, 8),
    (5, 9),
    (6, 4),
    (6, 8)
ON CONFLICT DO NOTHING;


INSERT INTO op_type_assets (op_type_id, asset_id)
VALUES
    (5, 20),
    (5, 21),
    (6, 15),
    (6, 16)
ON CONFLICT DO NOTHING;

INSERT INTO assessments (team_member_id, pre_op_a_id, patient_id, start_date)
VALUES
    (1, 'Medical History Review', 1, '2024-10-12 10:00:00'),
    (2, 'Physical Examination', 2, '2024-10-12 11:00:00'),
    (3, 'Blood Tests', 3, '2024-10-12 12:00:00'),
    (4, 'Cardiac Assessment', 4, '2024-10-12 13:00:00');


INSERT INTO roles  (id,name)
VALUES
    (1,'ROLE_USER'),
    (2,'ROLE_ADMIN'),
    (3,'ROLE_BUSINESS_ADMIN');

INSERT INTO privileges (id,name)
VALUES
    (1,'WRITE'),
    (2,'READ'),
    (3,'UPDATE'),
    (4,'DELETE'),
    (5,'CRUD')


INSERT INTO users (email, password, username)
VALUES
    ('admin@gmail.com','1234','adminUser'),
    ('test@gmail.com','1234','testUser'),
    ('business@gmail.com','1234','businessUser');