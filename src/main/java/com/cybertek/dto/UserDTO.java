package com.cybertek.dto;

import com.cybertek.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private long id;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String confirmPassword;
    private boolean enabled;
    private String phone;
    private RoleDTO role;
    private Gender gender;
}
