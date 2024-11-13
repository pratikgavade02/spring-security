package com.maven.service;

import com.maven.module.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    Student createStudent(Student student);

    Student getStudentsById( Long id);

    List<Student> getAllStudent();

    void deleteStudent(Long id);

    Student updateStudent(Student student);

}
