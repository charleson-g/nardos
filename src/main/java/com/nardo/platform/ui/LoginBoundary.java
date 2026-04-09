package com.nardo.platform.ui;

import com.nardo.platform.security.Authenticator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginBoundary {

    private final Authenticator authenticator;

    @Autowired
    public LoginBoundary(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, 
                               @RequestParam String password, 
                               HttpSession session, 
                               Model model) {
                               
        if (authenticator.authenticate(username, password) && "nardo".equals(username)) {
            // Success! Store the user's role in their session
            session.setAttribute("ROLE", authenticator.getUserRole(username));
            return "redirect:/admin/dashboard";
        }
        
        // Failed login
        model.addAttribute("error", "Invalid username or password.");
        return "login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clear the session
        return "redirect:/login";
    }
}