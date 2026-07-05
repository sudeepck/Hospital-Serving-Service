INSERT INTO patient (name,birth_date, email, gender, blood_group)
VALUES
('rohit', '1987-04-30', 'rohit@example.com', 'Male', 'A_POSITIVE'),
('Sudeep', '2002-05-29', 'sudeep@example.com', 'Male', 'B_POSITIVE'),
('viral', '1990-09-05', 'virat@example.com', 'Male', 'O_NEGATIVE'),
('sudarshan', '2006-12-06', 'sudarshan@example.com', 'Male', 'B_POSITIVE'),
('RAm', '1000-09-05', 'ram@example.com', 'Male', 'B_NEGATIVE');




INSERT INTO doctor (name, specilazation, email)
VALUES
    ('Dr. Rakesh Metha', 'Cardiology', 'rakesh.metha@example,com'),
    ('Dr. Sneha Kappor', 'Dermatology', 'Sneha.Kappor@example,com'),
    ('Dr. Arju Nair', 'Cardiology', 'Arjun.nair@example,com');


INSERT INTO appointment(appointment_time, reason, doctor_id, patient_id)
VALUES
    ('2025-08-31 13:30:03', 'General CheckUp', 1,1),
    ('2025-08-28 13:30:03', 'Skin Rash', 2,2),
    ('2025-08-2 13:30:03', 'Keen Pain', 3,3),
    ('2025-08-3 13:30:03', 'Follow UP Visit', 1,1),
    ('2025-08-5 13:30:03', 'Consulation', 1,4),
    ('2025-08-1 13:30:03', 'Allergy TreatMent', 2,5);
