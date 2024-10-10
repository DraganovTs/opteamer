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
    -- Appendectomy assets
    (1, 2),  -- SCISSORS
    (1, 1),  -- SCALPEL
    -- Cholecystectomy assets
    (2, 3),  -- FORCEPS
    (2, 4),  -- CLAMPS
    -- Hernia Repair assets
    (3, 10), -- THERMOMETER
    (3, 9),  -- ELECTROSURGICAL UNIT
    -- Spinal Fusion assets
    (4, 12), -- STERILIZER
    (4, 13), -- PULSE OXIMETRY
    -- Gallbladder Removal assets
    (5, 4),  -- CLAMPS
    (5, 1),  -- SCALPEL
    -- Plastic Surgery assets
    (6, 5),  -- MONITOR
    (6, 6),  -- ANESTESIA MACHINE
    -- Coronary Bypass assets
    (7, 5),  -- MONITOR
    (7, 2),  -- SCISSORS
    -- Laparoscopic Surgery assets
    (8, 3),  -- FORCEPS
    (8, 8);  -- OPERATIONAL LIGHT

-- Insert Operation Type Pre-Operative Assessments
INSERT INTO optype_pre_op_a (optype_id, pre_op_a_id)
VALUES
    -- Appendectomy assessments
    (1, 1),  -- Medical History Review
    (1, 2),  -- Anesthetic Assessment
    -- Cholecystectomy assessments
    (2, 4),  -- Physical Examination
    (2, 3),  -- Blood Tests
    -- Hernia Repair assessments
    (3, 3),  -- Blood Tests
    (3, 5),  -- Infection Screening
    -- Spinal Fusion assessments
    (4, 7),  -- Nutritional Assessment
    (4, 6),  -- Renal Function Tests
    -- Gallbladder Removal assessments
    (5, 1),  -- Medical History Review
    (5, 5),  -- Infection Screening
    -- Plastic Surgery assessments
    (6, 4),  -- Physical Examination
    (6, 8),  -- Psychological and Social Assessment
    -- Coronary Bypass assessments
    (7, 2),  -- Cardiac Assessment
    (7, 3),  -- Blood Tests
    -- Laparoscopic Surgery assessments
    (8, 5),  -- Infection Screening
    (8, 9);  -- Respiratory Function Tests
