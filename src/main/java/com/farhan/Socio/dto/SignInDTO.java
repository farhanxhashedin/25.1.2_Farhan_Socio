package com.farhan.Socio.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignInDTO {
    private String name;
    private String email;
    private String password;
}
