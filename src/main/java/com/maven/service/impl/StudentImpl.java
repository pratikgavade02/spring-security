package com.maven.service.impl;

import com.maven.module.Student;
import com.maven.repository.StudentRepository;
import com.maven.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentsById(Long id) {
        return studentRepository.getStudentsById(id);
    }

    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public void deleteStudent(Long id) {
        Student studentsById = studentRepository.getStudentsById(id);
        studentRepository.delete(studentsById);
    }

    @Override
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }
}
