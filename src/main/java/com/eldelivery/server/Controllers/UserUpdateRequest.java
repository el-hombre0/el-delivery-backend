package com.eldelivery.server.Controllers;

import com.eldelivery.server.Models.User.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest{
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;
}
