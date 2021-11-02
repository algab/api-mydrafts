package br.com.mydrafts.ApiMyDrafts.utils;

import br.com.mydrafts.ApiMyDrafts.constants.Gender;
import br.com.mydrafts.ApiMyDrafts.documents.User;
import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserFormDTO;

public final class UserUtil {

    public static UserFormDTO userForm() {
        return UserFormDTO.builder()
                .name("Alvaro")
                .gender(Gender.MASCULINO)
                .email("alvaro@email.com")
                .password("12345678")
                .build();
    }

    public static User getUser() {
        return User.builder()
                .id("61586ad5362766670067edd5")
                .name("Alvaro")
                .gender(Gender.MASCULINO)
                .email("alvaro@email.com")
                .password("$2a$10$Em8McmNwrOTvMu2Xelw0e.i33d5RTMVkciU74H5XmjpbCyREqvrrG")
                .build();
    }

    public static UserDTO getUserDTO() {
        return UserDTO.builder()
                .id("61586ad5362766670067edd5")
                .name("Alvaro")
                .email("alvaro@email.com")
                .gender(Gender.MASCULINO)
                .build();
    }

}
