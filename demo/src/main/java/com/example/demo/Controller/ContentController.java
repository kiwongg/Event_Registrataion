package com.example.demo.Controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/req/login")
    public String login(){
        return "login";
    }

    @GetMapping("/req/signup")
    public String signup(){
        return "signup";
    }

    @GetMapping("/index")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails){
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername()); // Pass username to template
            // Assuming userDetails is your MyAppUser, and it has a getPhotoPath() method
            //model.addAttribute("photoPath", userDetails.get); // Use appropriate method to retrieve the photo path
        } else {
            model.addAttribute("username", "Guest"); // Default value if not logged in
        }
        return "index"; // Ensure you have index.html in templates
    }
}
