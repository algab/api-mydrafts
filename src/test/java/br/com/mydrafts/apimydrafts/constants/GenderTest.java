package br.com.mydrafts.apimydrafts.constants;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test for Gender Constant")
class GenderTest {

    @Test
    @DisplayName("Validate value Male")
    void whenPassingTheValueMaleMustReturnTrue() {
        Gender gender = Gender.fromValue("male");

        assertThat(gender).isEqualTo(Gender.MALE);
    }

    @Test
    @DisplayName("Validate value female")
    void whenPassingTheValueFemaleMustReturnTrue() {
        Gender gender = Gender.fromValue("female");

        assertThat(gender).isEqualTo(Gender.FEMALE);
    }

    @Test
    @DisplayName("Validate value null")
    void whenPassingTheValueTestMustReturnNull() {
        Gender gender = Gender.fromValue("test");

        assertThat(gender).isNull();
    }

}
