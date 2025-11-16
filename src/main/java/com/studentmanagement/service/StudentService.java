package com.studentmanagement.service;

import com.studentmanagement.dto.StudentRequest;
import com.studentmanagement.dto.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    
    StudentResponse createStudent(StudentRequest studentRequest);
    
    Page<StudentResponse> getAllStudents(Pageable pageable);
    
    StudentResponse getStudentById(Long id);
    
    StudentResponse updateStudent(Long id, StudentRequest studentRequest);
    
    void deleteStudent(Long id);
    
    Page<StudentResponse> searchStudents(String keyword, Pageable pageable);
}

