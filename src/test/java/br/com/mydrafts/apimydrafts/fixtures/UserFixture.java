package br.com.mydrafts.apimydrafts.fixtures;

import br.com.mydrafts.apimydrafts.constants.Gender;
import br.com.mydrafts.apimydrafts.documents.User;
import br.com.mydrafts.apimydrafts.dto.UserDTO;
import br.com.mydrafts.apimydrafts.dto.UserFormDTO;

public final class UserFixture {

    public static UserFormDTO getUserForm() {
        return UserFormDTO.builder()
            .firstName("Alvaro")
            .lastName("Oliveira")
            .gender(Gender.MALE)
            .email("alvaro@email.com")
            .password("12345678")
            .build();
    }

    public static User getUser() {
        return User.builder()
            .id("61586ad5362766670067edd5")
            .firstName("Alvaro")
            .lastName("Oliveira")
            .gender(Gender.MALE)
            .email("alvaro@email.com")
            .password("$2a$10$Em8McmNwrOTvMu2Xelw0e.i33d5RTMVkciU74H5XmjpbCyREqvrrG")
            .build();
    }

    public static UserDTO getUserDTO() {
        return UserDTO.builder()
            .id("61586ad5362766670067edd5")
            .firstName("Alvaro")
            .lastName("Oliveira")
            .email("alvaro@email.com")
            .gender(Gender.MALE)
            .build();
    }

}
