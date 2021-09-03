package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.clients.TMDBClient;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBMovieDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBMovieResponseDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBTvDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBTvResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MediaServiceImpl implements MediaService {
    @Value("${tmdb.api-key}")
    private String apiKey;

    @Value("${tmdb.language}")
    private String language;

    @Autowired
    private TMDBClient client;

    @Autowired
    private ModelMapper mapper;

    @Override
    public TMDBMovieResponseDTO getMovie(Integer id) {
        TMDBMovieDTO movie = this.client.movie(id, apiKey, language);
        return mapper.map(movie, TMDBMovieResponseDTO.class);
    }

    @Override
    public TMDBTvResponseDTO getTV(Integer id) {
        TMDBTvDTO tv = this.client.tv(id, apiKey, language);
        return mapper.map(tv, TMDBTvResponseDTO.class);
    }
}
