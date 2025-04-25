package com.farhan.Socio.service;

import com.farhan.Socio.entity.Role;
import com.farhan.Socio.entity.User;
import com.farhan.Socio.repository.UserRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserUploadService {

    @Autowired
    private UserRepository userRepository;

    public String uploadUsers(MultipartFile file) throws IOException {
        List<User> users = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] line;
            reader.readNext(); // skip header
            while ((line = reader.readNext()) != null) {
                String name = line[0];
                String email = line[1];
                String password = line[2];
                String dob = line[3];

                if (userRepository.existsByEmail(email)) continue;

                User user = new User();
                user.setId(UUID.randomUUID().toString());
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password); // Hash in real apps
                user.setDob(LocalDate.parse(dob));
                user.setRole(Role.USER);
                users.add(user);
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        userRepository.saveAll(users);
        return users.size() + " users uploaded successfully.";
    }
}
