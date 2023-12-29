create database SSID;
use ssid;
create table students(
student_id int primary key,
first_name varchar(20),
last_name varchar(20),
date_of_birth date,
email varchar(50),
phone_number varchar(15));


INSERT INTO students VALUES
  (1, 'John', 'Doe', '1995-05-15', 'john.doe@email.com', '555-1234'),
  (2, 'Jane', 'Smith', '1998-08-22', 'jane.smith@email.com', '555-5678'),
  (3, 'Robert', 'Johnson', '1990-03-10', 'robert.j@email.com', '555-9876'),
  (4, 'Emily', 'Williams', '1993-11-28', 'emily.w@email.com', '555-4321'),
  (5, 'Michael', 'Davis', '1987-07-04', 'michael.d@email.com', '555-8765'),
  (6, 'Amanda', 'Taylor', '1996-01-19', 'amanda.t@email.com', '555-2345'),
  (7, 'Christopher', 'Brown', '1989-09-12', 'chris.b@email.com', '555-8765'),
  (8, 'Olivia', 'Anderson', '1992-06-30', 'olivia.a@email.com', '555-3456'),
  (9, 'Daniel', 'Thomas', '1997-04-08', 'daniel.t@email.com', '555-7890'),
  (10, 'Sophia', 'Miller', '1985-12-03', 'sophia.m@email.com', '555-1234'),
  (11, 'William', 'Wilson', '1994-02-17', 'william.w@email.com', '555-5678');

create table teacher(
teacher_id int primary key,
first_name varchar(20),
last_name varchar(20),
email varchar(50));


INSERT INTO teacher VALUES
  (101, 'Jennifer', 'Smith', 'jennifer.smith@email.com'),
  (102, 'Matthew', 'Johnson', 'matthew.j@email.com'),
  (103, 'Emil', 'Clark', 'emil.c@email.com'),
  (104, 'Andrew', 'Miller', 'andrew.m@email.com'),
  (105, 'Sarah', 'Jones', 'sarah.j@email.com'),
  (106, 'David', 'Brown', 'david.b@email.com'),
  (107, 'Jessica', 'Wilson', 'jessica.w@email.com'),
  (108, 'Ryan', 'Anderson', 'ryan.a@email.com'),
  (109, 'Lauren', 'Taylor', 'lauren.t@email.com'),
  (110, 'Kevin', 'Davis', 'kevin.d@email.com'),
  (111, 'Emma', 'Thomas', 'emma.t@email.com');



create table courses(
course_id int primary key,
course_name varchar(20),
credits int,
teacher_id int references teacher(teacher_id));

INSERT INTO courses VALUES
  (501, 'Biology', 3, 101),
  (502, 'Algebra', 4, 104),
  (503, 'English', 3, 107),
  (504, 'Computer Science', 4, 110),
  (505, 'History', 3, 103),
  (506, 'Physics', 4, 106),
  (507, 'Business', 3, 109),
  (508, 'Psychology', 4, 102),
  (509, 'Spanish', 3, 105),
  (510, 'Chemistry', 4, 108),
  (511, 'Economics', 3, 111);


create table enrollments(
enrollment_id int primary key,
student_id int references students(student_id),
course_id int references courses(course_id),
enrollment_date varchar(30));


INSERT INTO enrollments VALUES
  (601, 1, 501, '2023-01-15'),
  (602, 2, 502, '2023-02-22'),
  (603, 3, 503, '2023-03-10'),
  (604, 4, 504, '2023-04-28'),
  (605, 5, 505, '2023-07-04'),
  (606, 6, 506, '2023-01-19'),
  (607, 7, 507, '2023-09-12'),
  (608, 8, 508, '2023-06-30'),
  (609, 9, 509, '2023-04-08'),
  (610, 10, 510, '2023-12-03'),
  (611, 11, 511, '2023-02-17');


create table payments(
payment_id int primary key,
student_id int references students(student_id),
amount float,
payment_date varchar(30));


INSERT INTO payments VALUES
  (701, 1, 100.00, '2023-01-05'),
  (702, 2, 150.00, '2023-02-12'),
  (703, 3, 120.00, '2023-03-20'),
  (704, 4, 200.00, '2023-04-18'),
  (705, 5, 180.00, '2023-07-01'),
  (706, 6, 130.00, '2023-01-16'),
  (707, 7, 110.00, '2023-09-08'),
  (708, 8, 160.00, '2023-06-25'),
  (709, 9, 140.00, '2023-04-05'),
  (710, 10, 190.00, '2023-11-30'),
  (711, 11, 170.00, '2023-02-14');


select * from students;
select * from courses;
select * from enrollments;
select * from teacher;
select * from payments;

-- First task
alter table Students modify column student_id int auto_increment;
insert into Students(first_name,last_name,date_of_birth,email,phone_number)
values('John','Doe','1995-08-15','john.doe@example.com','1234567890');
select * from students;

-- Second Task
INSERT INTO students (first_name, last_name, date_of_birth, email, phone_number)
VALUES ('Adarsh', 'Doe', '2000-08-21', 'adarsh.doe@email.com', '123-1234');

INSERT INTO enrollments (enrollment_id,student_id, course_id, enrollment_date)
VALUES (612,13, 508, '2023-12-09');

-- Third Task
update teacher set email='sarahjones@gmail.com' where teacher_id=105;

-- Fourth task
delete from enrollments where enrollment_id=609;

 -- Fifth task
insert into teacher(teacher_id,first_name,last_name,email)
values(112,'Adarsh','Srungarapu','adarsh@gmail.com'); 
update courses set teacher_id=112 where course_id=510;  

 -- Sixth Task
DELETE students, enrollments
FROM students
JOIN enrollments ON students.student_id = enrollments.student_id
WHERE students.first_name = 'Adarsh';
select * from enrollments;
select * from students;
select * from payments;


-- Seventh task
select * from payments;
update payments set amount=250 where payment_id=701;
select * from payments;

use SSID;
INSERT INTO enrollments (enrollment_id,student_id, course_id, enrollment_date)
 VALUES (613,11,501,now());
 select * from enrollments;
 delete from enrollments where enrollment_id=614;
 
 ALTER TABLE enrollments
MODIFY enrollment_id INT AUTO_INCREMENT; 

ALTER TABLE students
drop column outstanding_balance;
ALTER TABLE students
add column outstanding_balance INT default 2000;
desc payments;
select  * from students;
select  * from enrollments;
select  * from courses;
SELECT enrollment_id FROM enrollments WHERE enrollment_id=601 ORDER BY enrollment_id desc;
SELECT * FROM enrollments WHERE enrollment_id=601 ORDER BY enrollment_id desc;
