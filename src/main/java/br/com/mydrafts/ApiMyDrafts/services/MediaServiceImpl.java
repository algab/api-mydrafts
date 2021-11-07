package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.clients.TMDBProxy;
import br.com.mydrafts.ApiMyDrafts.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private TMDBProxy tmdbProxy;

    @Autowired
    private ModelMapper mapper;

    @Override
    public TMDBMovieResponseDTO getMovie(Integer id) {
        log.info("MediaServiceImpl.getMovie - Start - Input: id {}", id);
        TMDBMovieDTO movie = this.tmdbProxy.getMovie(id);
        TMDBCreditsDTO credits = this.tmdbProxy.getMovieCredits(id);
        TMDBMovieResponseDTO response = mapper.map(movie, TMDBMovieResponseDTO.class);
        response.setCrew(credits.getCrew());
        log.info("MediaServiceImpl.getMovie - End");
        return response;
    }

    @Override
    public TMDBTvResponseDTO getTV(Integer id) {
        log.info("MediaServiceImpl.getTV - Start - Input: id {}", id);
        TMDBTvDTO tv = this.tmdbProxy.getTV(id);
        log.info("MediaServiceImpl.getTV - End");
        return mapper.map(tv, TMDBTvResponseDTO.class);
    }

}
