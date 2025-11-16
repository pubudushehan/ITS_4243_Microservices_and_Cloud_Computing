package com.studentmanagement.service;

import com.studentmanagement.dto.StudentRequest;
import com.studentmanagement.dto.StudentResponse;
import com.studentmanagement.entity.Student;
import com.studentmanagement.exception.ResourceNotFoundException;
import com.studentmanagement.exception.DuplicateResourceException;
import com.studentmanagement.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentResponse createStudent(StudentRequest studentRequest) {
        // Check if email already exists
        if (studentRepository.existsByEmail(studentRequest.getEmail())) {
            throw new DuplicateResourceException("Student with email " + studentRequest.getEmail() + " already exists");
        }

        Student student = new Student();
        student.setName(studentRequest.getName());
        student.setEmail(studentRequest.getEmail());
        student.setCourse(studentRequest.getCourse());
        student.setAge(studentRequest.getAge());

        Student savedStudent = studentRepository.save(student);
        return convertToResponse(savedStudent);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponse> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(this::convertToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return convertToResponse(student);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest studentRequest) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        // Check if email is being changed and if the new email already exists
        if (!student.getEmail().equals(studentRequest.getEmail()) &&
            studentRepository.existsByEmail(studentRequest.getEmail())) {
            throw new DuplicateResourceException("Student with email " + studentRequest.getEmail() + " already exists");
        }

        student.setName(studentRequest.getName());
        student.setEmail(studentRequest.getEmail());
        student.setCourse(studentRequest.getCourse());
        student.setAge(studentRequest.getAge());

        Student updatedStudent = studentRepository.save(student);
        return convertToResponse(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        studentRepository.delete(student);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponse> searchStudents(String keyword, Pageable pageable) {
        return studentRepository.searchByNameOrCourse(keyword, pageable)
                .map(this::convertToResponse);
    }

    private StudentResponse convertToResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setEmail(student.getEmail());
        response.setCourse(student.getCourse());
        response.setAge(student.getAge());
        return response;
    }
}

