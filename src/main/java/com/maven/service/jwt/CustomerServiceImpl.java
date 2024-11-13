package com.maven.service.jwt;

import com.maven.module.Student;
import com.maven.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements UserDetailsService {
    @Autowired
    private final StudentRepository studentRepository;

    public CustomerServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> studentsByUsername = studentRepository.getStudentsByUsername(username);
        if(studentsByUsername.isEmpty()){
            System.out.println("username is not found");
            throw new UsernameNotFoundException("user not found");
        }
        System.out.println("successfull login");
        return new User(studentsByUsername.get().getUsername(),studentsByUsername.get().getPassword(),studentsByUsername.get().getAuthorities());

    }
}
