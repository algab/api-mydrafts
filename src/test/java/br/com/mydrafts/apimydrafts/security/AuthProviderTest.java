package br.com.mydrafts.apimydrafts.security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@DisplayName("Tests for Auth Provider")
class AuthProviderTest {

    private static final String USER_ID = "61586ad5362766670067edd5";

    @Test
    @DisplayName("Test method authenticate")
    void authenticateShouldReturnSuccessful() {
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(USER_ID, null, null);

        Authentication auth = new AuthProvider().authenticate(user);

        Assertions.assertThat(USER_ID).isEqualTo(auth.getPrincipal());
    }

}
