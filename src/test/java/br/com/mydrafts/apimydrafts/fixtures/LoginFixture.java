package br.com.mydrafts.apimydrafts.fixtures;

import br.com.mydrafts.apimydrafts.dto.LoginDTO;

public final class LoginFixture {

    public static LoginDTO getLogin() {
        return LoginDTO.builder()
            .token("token")
            .user(UserFixture.getUserDTO())
            .build();
    }

}
