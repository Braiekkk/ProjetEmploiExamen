package com.example.projetemploiexamen.auth.detailsService;

import com.example.projetemploiexamen.admin.Admin;
import com.example.projetemploiexamen.auth.details.AdminInfoDetails;
import com.example.projetemploiexamen.auth.details.StudentInfoDetails;
import com.example.projetemploiexamen.student.Student;
import com.example.projetemploiexamen.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentDetailsService implements UserDetailsService{
    // this is the class that actually checks the database for the admin / user -> further develop
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return studentRepository.findByEmail(email)
                .map(StudentInfoDetails::new) // ✅ Convert to AdminInfoDetails
                .orElseThrow(() -> new UsernameNotFoundException("Student not found: " + email));
    }




}

