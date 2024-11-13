package com.maven.repository;

import com.maven.module.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {

    Optional<Student> getStudentsByUsername(String username);

    Student getStudentsById( Long id);



}
