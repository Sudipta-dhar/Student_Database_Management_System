create database college_db;
CREATE TABLE students (
  id VARCHAR(10) PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  sex ENUM('male', 'female') NOT NULL,
  entrance_age INT NOT NULL,
  entrance_year INT NOT NULL,
  class VARCHAR(50) NOT NULL
);
CREATE TABLE courses (
  id VARCHAR(7) PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  teacher_id VARCHAR(5) NOT NULL,
  credit INT NOT NULL,
  grade INT NOT NULL,
  canceled_year INT
);
CREATE TABLE teachers (
  id VARCHAR(5) PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  courses VARCHAR(100) NOT NULL
);
CREATE TABLE course_choosing (
  student_id VARCHAR(10),
  course_id VARCHAR(7),
  teacher_id VARCHAR(5),
  chosen_year INT,
  score INT,
  FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
  FOREIGN KEY (course_id) REFERENCES courses(id),
  FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);

CREATE TABLE stregistration (
STusername  VARCHAR(50),
usertype  VARCHAR(8),
STuserId  VARCHAR(10),
Password  VARCHAR(8),
FOREIGN KEY (STusername) REFERENCES students(name) ON DELETE CASCADE,
FOREIGN KEY (STuserId) REFERENCES students(id)
);
CREATE TABLE tregistration (
Tusername  VARCHAR(50),
usertype  VARCHAR(8),
TuserId  VARCHAR(10),
Password  VARCHAR(8),
FOREIGN KEY (Tusername) REFERENCES teachers(name) ON DELETE CASCADE,
FOREIGN KEY (TuserId) REFERENCES teachers(id)
);
CREATE TABLE admin (
AdminId VARCHAR(50),
Adminpasword VARCHAR(10)
);