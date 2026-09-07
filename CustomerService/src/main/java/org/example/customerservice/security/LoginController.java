package org.example.customerservice.security;


import org.example.customerservice.Model.Customer;
import org.example.customerservice.Repo.CustomerRepo;
import org.example.customerservice.dto.LoginRequest;
import org.example.customerservice.dto.LoginResponse;
import org.example.customerservice.error.BadRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final CustomerRepo customerRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwTService jwTService;

    public LoginController(CustomerRepo customerRepo, PasswordEncoder passwordEncoder, JwTService jwTService) {
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwTService = jwTService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Customer customer = customerRepo
                .findByEmail(request.email()).orElseThrow(() -> new BadRequest("Invalid email or passowrd"));

        boolean passwordMatches = passwordEncoder.matches(request.password(), customer.getPasswordHash());

        if (!passwordMatches) {
            throw new BadRequest("Invalid email or password");
        }
        String token = jwTService.generateToken(customer.getId(),customer.getEmail());

        return new LoginResponse(token);
    }
}
