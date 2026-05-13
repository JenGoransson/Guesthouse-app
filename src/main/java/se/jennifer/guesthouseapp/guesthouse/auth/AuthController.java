package se.jennifer.guesthouseapp.guesthouse.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String showLogin() {
        System.out.println("🔥 AUTHCONTROLLER: showLogin() körs!");
        return "login";
    }

    @GetMapping("/register")
    public String showRegister() {
        System.out.println("🔥 AUTHCONTROLLER: showRegister() körs!");
        return "register";
    }
}


