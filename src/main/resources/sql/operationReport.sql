-- Insert into the operation types table
INSERT INTO operation_types (name, room_type, duration_hours)
VALUES
    ('Cataract Surgery', 'OPHTHALMOLOGY', 1.5),
    ('Knee Replacement', 'ORTHOPEDICS', 3)
ON CONFLICT DO NOTHING;

-- Insert into the assets table
INSERT INTO assets (id, type, name)
VALUES
    (20, 'SURGICAL_INSTRUMENT', 'PHACOEMULSIFIER'),
    (21, 'SURGICAL_INSTRUMENT', 'MICROFORCEPS'),
    (15, 'SURGICAL_INSTRUMENT', 'BONE SAW'),
    (16, 'MACHINE', 'SURGICAL_DRILL')
ON CONFLICT DO NOTHING;

-- Insert into operation providers table
INSERT INTO operation_providers (type)
VALUES
    ('ANESTHESIOLOGIST'),
    ('OPHTHALMIC_SURGEON'),
    ('ORTHOPEDIC_SURGEON')
ON CONFLICT DO NOTHING;

-- Insert into pre-operative assessments table
INSERT INTO pre_operative_assessments (name)
VALUES
    ('Eye Examination'),
    ('Vision Assessment'),
    ('Joint X-ray'),
    ('Blood Pressure Check')
ON CONFLICT DO NOTHING;

-- Insert into operation rooms table
INSERT INTO operation_rooms (id, room_nr, building_block, floor, type, state)
VALUES
    (10, 203, 'West Wing', '2', 'OPHTHALMOLOGY', 'AVAILABLE'),
    (9, 305, 'North Wing', '3', 'ORTHOPEDICS', 'AVAILABLE')
ON CONFLICT DO NOTHING;

-- Insert into patients table
INSERT INTO patients (id, name, nin)
VALUES
    (5, 'Elizabeth Brown', '2389017625'),
    (6, 'William Davis', '5938271045')
ON CONFLICT DO NOTHING;

-- Insert into team members table
INSERT INTO team_members (id, name, opprovider_id)
VALUES
    (8, 'Karen White', 'ANESTHESIOLOGIST'),
    (9, 'George Harris', 'OPHTHALMIC_SURGEON'),
    (4, 'David Miller', 'ORTHOPEDIC_SURGEON')
ON CONFLICT DO NOTHING;

-- Insert into operations table
INSERT INTO operations (id, type_id, room_id, patient_id, state, start_date)
VALUES
    (5, (SELECT id FROM operation_types WHERE name = 'Cataract Surgery'), 10, 5, 'SCHEDULED', '2024-10-12 14:00:00'),
    (6, (SELECT id FROM operation_types WHERE name = 'Knee Replacement'), 9, 6, 'SCHEDULED', '2024-10-12 15:00:00')
ON CONFLICT DO NOTHING;

-- Insert into operation team members table (to link team members with operations)
INSERT INTO optype_team_member (operation_id, team_member_id)
VALUES
    (5, 8), -- Karen White for Cataract Surgery
    (5, 9), -- George Harris for Cataract Surgery
    (6, 4), -- David Miller for Knee Replacement
    (6, 8)  -- Karen White for Knee Replacement
ON CONFLICT DO NOTHING;

-- Insert into operation assets table (to link assets with operations)
INSERT INTO op_type_assets (op_type_id, asset_id)
VALUES
    (5, 20), -- PHACOEMULSIFIER for Cataract Surgery
    (5, 21), -- MICROFORCEPS for Cataract Surgery
    (6, 15), -- BONE SAW for Knee Replacement
    (6, 16)  -- SURGICAL DRILL for Knee Replacement
ON CONFLICT DO NOTHING;

-- Insert into operation assessments table (to link pre-operative assessments with operations)
INSERT INTO op_type_assets (op_type_id, asset_id)
VALUES
    (5, (SELECT op_type_id FROM pre_operative_assessments WHERE name = 'Appendectomy')),
    (5, (SELECT op_type_id FROM pre_operative_assessments WHERE name = 'Cholecystectomy')),
    (6, (SELECT op_type_id FROM pre_operative_assessments WHERE name = 'Spinal Fusion')),
    (6, (SELECT op_type_id FROM pre_operative_assessments WHERE name = 'Gallbladder Removal'))
ON CONFLICT DO NOTHING;
