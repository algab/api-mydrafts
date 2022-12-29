package br.com.mydrafts.apimydrafts.config;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;

class MapperConfigTest {

    private final MapperConfig mapperConfig = new MapperConfig();

    @Test
    void shouldReturnModelMapper() {
        ModelMapper mapper = mapperConfig.mapper();

        assertThat(mapper).isExactlyInstanceOf(ModelMapper.class);
    }

}
