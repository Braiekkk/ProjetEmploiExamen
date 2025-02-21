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
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository; // ✅ Fixed variable naming

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return adminRepository.findByEmail(email)
                .map(AdminInfoDetails::new) // ✅ Convert to AdminInfoDetails
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found: " + email));
    }
}
