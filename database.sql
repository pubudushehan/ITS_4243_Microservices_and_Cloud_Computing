CREATE DATABASE IF NOT EXISTS student_management;
USE student_management;

DROP TABLE IF EXISTS students;

CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    course VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    CONSTRAINT chk_age CHECK (age >= 18 AND age <= 100),
    INDEX idx_email (email),
    INDEX idx_name (name),
    INDEX idx_course (course)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO students (name, email, course, age) VALUES
('John Doe', 'john.doe@example.com', 'Computer Science', 20),
('Jane Smith', 'jane.smith@example.com', 'Mathematics', 22),
('Bob Johnson', 'bob.johnson@example.com', 'Physics', 19),
('Alice Williams', 'alice.williams@example.com', 'Computer Science', 21),
('Charlie Brown', 'charlie.brown@example.com', 'Chemistry', 23),
('Diana Prince', 'diana.prince@example.com', 'Biology', 20),
('Edward Norton', 'edward.norton@example.com', 'Mathematics', 24),
('Fiona Green', 'fiona.green@example.com', 'Computer Science', 19);

SELECT * FROM students;

SELECT COUNT(*) AS total_students FROM students;

SELECT course, COUNT(*) AS student_count 
FROM students 
GROUP BY course;

