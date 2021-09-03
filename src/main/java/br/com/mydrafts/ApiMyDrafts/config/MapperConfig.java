package br.com.mydrafts.ApiMyDrafts.config;

import br.com.mydrafts.ApiMyDrafts.converters.TMDBMovie;
import br.com.mydrafts.ApiMyDrafts.converters.TMDBTv;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new TMDBMovie());
        modelMapper.addConverter(new TMDBTv());
        return modelMapper;
    }

}
