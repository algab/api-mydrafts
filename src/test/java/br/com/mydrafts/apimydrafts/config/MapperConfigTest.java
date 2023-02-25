package br.com.mydrafts.apimydrafts.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for MapperConfig")
class MapperConfigTest {

    private final MapperConfig mapperConfig = new MapperConfig();

    @Test
    @DisplayName("Test bean ModelMapper")
    void shouldReturnModelMapper() {
        ModelMapper mapper = mapperConfig.mapper();

        assertThat(mapper).isExactlyInstanceOf(ModelMapper.class);
    }

}
