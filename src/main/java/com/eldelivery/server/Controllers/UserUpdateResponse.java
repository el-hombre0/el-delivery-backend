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
public class UserUpdateResponse{

        /**
         * Токен, возвращаемый обратно пользователю
         */
        private String token;
        /**
         * Данные пользователя
         */
        private String firstName;
        private String lastName;
        private String password;
        private String email;
        private Role role;
}
