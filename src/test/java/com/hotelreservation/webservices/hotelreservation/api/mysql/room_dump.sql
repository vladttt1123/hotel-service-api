-- Rooms for Hotel One
INSERT INTO rooms (id, room_number, room_type, is_available, hotel_id)
VALUES (101, 101, 'SINGLE', true, 1000);
INSERT INTO rooms (id, room_number, room_type, is_available, hotel_id)
VALUES (102, 102, 'DOUBLE', true, 1000);
INSERT INTO rooms (id, room_number, room_type, is_available, hotel_id)
VALUES (103, 103, 'SUITE', false, 1000);

-- Rooms for Hotel Two
INSERT INTO rooms (id, room_number, room_type, is_available, hotel_id)
VALUES (104, 201, 'SINGLE', true, 1001);
INSERT INTO rooms (id, room_number, room_type, is_available, hotel_id)
VALUES (105, 202, 'DOUBLE', false, 1001);
INSERT INTO rooms (id, room_number, room_type, is_available, hotel_id)
VALUES (106, 203, 'SUITE', true, 1001);

-- Rooms for Hotel Three
INSERT INTO rooms (id, room_number, room_type, is_available, hotel_id)
VALUES (107, 301, 'SINGLE', true, 1002);
INSERT INTO rooms (id, room_number, room_type, is_available, hotel_id)
VALUES (108, 302, 'DOUBLE', true, 1002);
INSERT INTO rooms (id, room_number, room_type, is_available, hotel_id)
VALUES (109, 303, 'SUITE', false, 1002);