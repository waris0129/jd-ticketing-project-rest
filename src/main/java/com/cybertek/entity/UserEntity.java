package com.cybertek.entity;

import com.cybertek.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "users")
@Where(clause = "userentity0_.is_deleted=false")
public class UserEntity extends BaseEntity{

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean enabled;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

}
