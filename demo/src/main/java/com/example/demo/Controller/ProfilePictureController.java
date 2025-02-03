package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.MyAppUser;
import com.example.demo.Model.MyAppUserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/profile")
public class ProfilePictureController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private MyAppUserRepository userRepository;

    @PostMapping("/upload")
    public String uploadProfilePicture(@AuthenticationPrincipal MyAppUser user, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "No file selected!";
        }

        try {
            // Generate unique filename
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            // Ensure directory exists
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Save file to disk
            file.transferTo(filePath.toFile());

            // Update user photo path in database
            user.setPhotoPath("/" + UPLOAD_DIR + fileName);
            userRepository.save(user);

            return "File uploaded successfully: " + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error uploading file!";
        }
    }
}
