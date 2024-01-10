--INSERT INTO Fleet_Details (count, route)
--VALUES (1, 'Pune'),
--(1, 'Mumbai'),
--(2, 'Delhi'),
--(1, 'Kolkata');

--INSERT INTO Vehicle_Details (registration_number, model, style,fleet_id)
--VALUES ('MH 12 JH 2345', 'Mustang', 'Sports',1),
--('DL 01 AA 1111', 'Fiesta', 'Car',2),
--('KL 09 FD 8760', 'F150', 'Truck',3),
--('CG 08 HG 8737', 'Fig', 'Car',3),
--('WB 01 UH 7890', 'Fiesta', 'Car',4);


INSERT INTO Driver_Details (name, address, licence_number, phone_number)
VALUES ('John', 'Pune', 'MH06 20160034761', '9879563523'),
('Raja', 'Mumbai', 'MH02 87965427865', '1234567890'),
('Kamran', 'Mumbai', 'MH02 65378524678', '1234567890'),
('Suresh', 'Pune', 'MH05 87356976256', '1234567890');

--INSERT INTO telemetry_details (vehicle_id, driver_id, parameter, param_value, timestamp)
--VALUES (1, 1, 'current_speed', 100, CURRENT_TIMESTAMP),
--(2, 2, 'current_speed', 50, CURRENT_TIMESTAMP(2)),
--(2, 2, 'distance', 10, CURRENT_TIMESTAMP),
--(3, 3, 'current_speed', 80, CURRENT_TIMESTAMP(4)),
--(4, 4, 'seatbelt', 0, CURRENT_TIMESTAMP(4));
--
