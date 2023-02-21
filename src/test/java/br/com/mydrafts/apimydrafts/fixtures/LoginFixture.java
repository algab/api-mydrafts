package br.com.mydrafts.apimydrafts.fixtures;

import br.com.mydrafts.apimydrafts.dto.LoginDTO;
import br.com.mydrafts.apimydrafts.dto.LoginFormDTO;

public final class LoginFixture {

    public static LoginDTO getLogin() {
        return LoginDTO.builder()
            .token("token")
            .user(UserFixture.getUserDTO())
            .build();
    }

    public static LoginFormDTO getLoginForm() {
        return LoginFormDTO.builder()
            .email("alvaro@email.com")
            .password("12345678")
            .build();
    }

}
