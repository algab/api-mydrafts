package br.com.mydrafts.ApiMyDrafts.config;

import br.com.mydrafts.ApiMyDrafts.converters.TMDBMovieToResponse;
import br.com.mydrafts.ApiMyDrafts.converters.TMDBTvToResponse;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new TMDBMovieToResponse());
        modelMapper.addConverter(new TMDBTvToResponse());
        return modelMapper;
    }

}
