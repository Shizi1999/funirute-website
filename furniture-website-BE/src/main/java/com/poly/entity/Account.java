package com.poly.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.type.EAuthProvider;
import com.poly.type.ERole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity{
    @Email
    @NotBlank
    private String email;
    private String password;
    private String avatar;
    @Column(name = "cloudinary_id")
    private String cloudinaryId;
    private Boolean isActivated = true;
<<<<<<< HEAD
    private ERole role = ERole.ROLE_ADMIN;
=======
    private ERole role = ERole.ROLE_USER;
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private EAuthProvider provider;
    @JsonIgnore
    @Column()
    private String providerId;
>>>>>>> 9a6ef441493a7ebce48afe73fd36607053f95f52
}
