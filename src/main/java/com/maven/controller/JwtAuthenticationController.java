package com.maven.controller;

import com.maven.module.Student;
import com.maven.module.dtos.JwtRequest;
import com.maven.module.dtos.JwtResponse;
import com.maven.repository.StudentRepository;
import com.maven.service.StudentService;
import com.maven.service.jwt.CustomerServiceImpl;
import com.maven.util.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/service")
public class JwtAuthenticationController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private JwtUtil jwtUtils;
    @Autowired
    StudentRepository studentRepository;
    @PostMapping("add-student")
    public Student createStudent(@RequestBody Student student){
        return studentService.createStudent(student);
    }
    @PostConstruct
    public void createAdmin(){
        Student admin = new Student();
        admin.setId(1L);
        admin.setName("Admin");
        admin.setUsername("admin@gmail.com");
        admin.setRole("ADMIN");
        admin.setEnable(Boolean.TRUE);
        admin.setPassword(new BCryptPasswordEncoder().encode("admin123"));
        studentRepository.save(admin);
    }

//    @PostMapping("/customerRegistration")
//    public Student createNewCustomer(@RequestBody Student student){
//        student.setRole("STUDENT");
//        student.setEnable(Boolean.TRUE);
//        student.setPassword(new BCryptPasswordEncoder().encode(student.getPassword()));
//        return studentService.createStudent(student);
//    }
    @PostMapping("/customerRegistration")
    public Student createStudent(@RequestParam("name")String name,
                                 @RequestParam("username") String username,
                                 @RequestParam("password")String password,
                                 @RequestParam("img")MultipartFile img) throws IOException {


        Student student = new Student();
        student.setName(name);
        student.setUsername(username);
        student.setRole("STUDENT");
        student.setPassword(password);
        student.setEnable(Boolean.TRUE);
        student.setImg(img.getBytes());


return studentService.createStudent(student);

    }

    @PostMapping("/login")
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest request) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect Username or Password.");
        }
        UserDetails userDetails = customerService.loadUserByUsername(request.getUsername());
        Optional<Student> optionalCustomer = studentRepository.getStudentsByUsername(userDetails.getUsername());

        final String jwt = jwtUtils.generateToken(userDetails.getUsername());
        JwtResponse response = new JwtResponse();
        if(optionalCustomer.isPresent()){
            response.setToken(jwt);
            response.setRole(optionalCustomer.get().getRole());
            response.setUsername(optionalCustomer.get().getUsername());
            return response;
        }
        return null;
    }

    @GetMapping("/get-student-by-id/{id}")
    public Student getStudentById(@PathVariable("id") Long id){
        return studentService.getStudentsById(id);
    }

    @GetMapping("/get-all")
    public List<Student> getAllStudent(){
        return studentService.getAllStudent();
    }
    @DeleteMapping("/delete-student/{id}")
    public void deleteStudent(@PathVariable("id")Long id){
        studentService.deleteStudent(id);
    }

    @PutMapping("/update-student")
    public Student updateStudent(@RequestParam("id")Long id,
                                 @RequestParam("name")String name,
                                 @RequestParam("username") String username,
                                 @RequestParam("password")String password,
                                 @RequestParam("img")MultipartFile img) throws IOException {

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setUsername(username);
        student.setRole("STUDENT");
        student.setPassword(new BCryptPasswordEncoder().encode(password));
        student.setEnable(Boolean.TRUE);
        student.setImg(img.getBytes());
        return studentService.updateStudent(student);
    }

}
