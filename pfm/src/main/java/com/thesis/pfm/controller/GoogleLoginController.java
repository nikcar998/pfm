package com.thesis.pfm.controller;

import com.thesis.pfm.config.JwtTokenProvider;
import com.thesis.pfm.controller.dto.GoogleLoginDto;
import com.thesis.pfm.controller.dto.GoogleRegisterInputDto;
import com.thesis.pfm.model.Customer;
import com.thesis.pfm.service.CustomUserDetailsService;
import com.thesis.pfm.service.CustomerService;
import com.thesis.pfm.service.GoogleTokenService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class GoogleLoginController {

    @Autowired
    private GoogleTokenService googleTokenService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomerService customerservice;

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody String token) {

        String jwtToken;

        try {
            jwtToken = googleTokenService.verifyAndGenerateJwt(token);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid Google token");
        }

        String email = tokenProvider.getUserEmailFromJWT(jwtToken);

        try {
            customUserDetailsService.loadUserByUsername(email);

            return ResponseEntity.ok(GoogleLoginDto.builder()
                    .jwtToken(jwtToken)
                    .isRegistered(true)
                    .email(email)   //TODO: CHECK IF USEFUL
                    .build());
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.ok(GoogleLoginDto.builder()
                    .jwtToken(jwtToken)
                    .isRegistered(false)
                    .email(email)
                    .build());
        }
    }

    @PostMapping("/google/register")
    public ResponseEntity<String> googleRegister(@RequestBody GoogleRegisterInputDto dto) {

        String jwtToken;

        try {
            jwtToken = googleTokenService.verifyAndGenerateJwt(dto.getToken());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid Google token");
        }

        String email = tokenProvider.getUserEmailFromJWT(jwtToken);


        List<Customer> cust = customerservice.getCustomerByEmail(email);

        if(!cust.isEmpty()){
            return ResponseEntity.badRequest().body("User Already Registered");
        }

        Customer newCustomer = Customer.builder()
                .name(dto.getFirstName())
                .surname(dto.getLastName())
                .email(dto.getEmail())
                .bornDate(dto.getDateOfBirth())
                .build();

        customerservice.saveCustomer(newCustomer);

        return ResponseEntity.ok(jwtToken);
    }
}
