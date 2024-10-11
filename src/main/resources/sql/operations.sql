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

-- Insert operation providers (surgeons, nurses, etc.) for each operation
-- Assume that operation_id is the auto-generated key in the operations table and provider_id references the providers table
INSERT INTO optype_opproviders (optype_id, opprovider_id)
VALUES
    -- Appendectomy
    (1, 1),  -- SURGEON
    (1, 2),  -- ANESTHESIOLOGIST

    -- Cholecystectomy
    (2, 1),  -- SURGEON
    (2, 3),  -- CERTIFIED_NURSE

    -- Hernia Repair
    (3, 3),  -- CERTIFIED_NURSE
    (3, 5),  -- SURGICAL_TECH

    -- Spinal Fusion
    (4, 2),  -- ANESTHESIOLOGIST
    (4, 1),  -- SURGEON

    -- Gallbladder Removal
    (5, 1),  -- SURGEON
    (5, 3),  -- CERTIFIED_NURSE

    -- Plastic Surgery
    (6, 5),  -- SURGICAL_TECH
    (6, 1),  -- SURGEON

    -- Coronary Bypass
    (7, 1),  -- SURGEON
    (7, 2),  -- ANESTHESIOLOGIST

    -- Laparoscopic Surgery
    (8, 3),  -- CERTIFIED_NURSE
    (8, 1);  -- SURGEON

-- Insert pre-operative assessments
INSERT INTO optype_pre_op_a (optype_id, pre_op_a_id)
VALUES
    -- Appendectomy
    (1, 'Medical History Review'),
    (1, 'Anesthetic Assessment'),

    -- Cholecystectomy
    (2, 'Physical Examination'),
    (2, 'Blood Tests'),

    -- Hernia Repair
    (3, 'Infection Screening'),
    (3, 'Blood Tests'),

    -- Spinal Fusion
    (4, 'Nutritional Assessment'),
    (4, 'Renal Function Tests'),

    -- Gallbladder Removal
    (5, 'Medical History Review'),
    (5, 'Infection Screening'),

    -- Plastic Surgery
    (6, 'Physical Examination'),
    (6, 'Psychological and Social Assessment'),

    -- Coronary Bypass
    (7, 2),  -- SCISSORS
    (7, 5),  -- MONITOR

    -- Laparoscopic Surgery
    (8, 8),  -- OPERATIONAL LIGHT
    (8, 3);  -- FORCEPS

-- Update operation rooms based on the current room states
UPDATE operations
SET room_id = (
    SELECT id FROM operation_rooms WHERE type = operations.type_id AND state = 'STERILE' LIMIT 1
)
WHERE operations.type_id IN ('GENERAL_SURGERY', 'NEURO_SURGERY', 'CARDIAC_SURGERY', 'PLASTIC_SURGERY');