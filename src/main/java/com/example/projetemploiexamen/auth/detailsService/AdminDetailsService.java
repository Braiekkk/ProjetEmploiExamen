package com.example.projetemploiexamen.auth.detailsService;

import com.example.projetemploiexamen.admin.Admin;
import com.example.projetemploiexamen.admin.AdminRepository;
import com.example.projetemploiexamen.auth.details.AdminInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminDetailsService implements UserDetailsService{
    // this is the class that actually checks the database for the admin / user -> further develop
    @Autowired
    private AdminRepository AdminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Admin> userDetail = AdminRepository.findByEmail(email); // Assuming 'email' is used as username
        return userDetail.map(AdminInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found: " + email));
    }




}
