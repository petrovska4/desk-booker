package org.example.restapi.api;

import org.example.core.model.Employee;
import org.example.core.model.enumeration.RoleEnum;
import org.example.core.security.JwtUtil;
import org.example.core.security.LoginDto;
import org.example.core.service.EmployeeService;
import org.example.restapi.dto.create.EmployeeCreateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(EmployeeService employeeService, PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody EmployeeCreateDto dto) {
        if (employeeService.existsByEmail(dto.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        employee.setPosition(dto.getPosition());
        employee.setRole(RoleEnum.EMPLOYEE);

        employeeService.create(employee);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String role = employeeService.findByEmail(userDetails.getUsername()).getRole().name();

        String token = jwtUtil.generateToken(userDetails.getUsername(), role);

        return ResponseEntity.ok(Map.of("token", token));
    }

}
