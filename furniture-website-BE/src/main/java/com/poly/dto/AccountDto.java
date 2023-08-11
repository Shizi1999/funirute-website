package com.poly.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poly.type.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String email;
    private String avatar;
    private Boolean isActivated;
    private ERole role;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdAt;
    private String createdBy;
}
