package com.farhan.Socio.dto;

import com.farhan.Socio.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDTO {
    private String name;
    private String password;
    private Role role;
    private Boolean isPrivate;
    private LocalDate dob;
}
