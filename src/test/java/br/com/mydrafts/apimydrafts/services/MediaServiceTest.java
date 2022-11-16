package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.clients.TMDBProxy;
import br.com.mydrafts.apimydrafts.converters.TMDBTvToResponse;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvResponseDTO;
import br.com.mydrafts.apimydrafts.fixtures.MediaFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Media Service")
class MediaServiceTest {

    private MediaService service;

    @Mock
    private TMDBProxy proxy;

    @BeforeEach
    void setup() {
        ModelMapper mapper = new ModelMapper();
        mapper.addConverter(new TMDBTvToResponse());
        service = new MediaServiceImpl(proxy, mapper);
    }

    @Test
    @DisplayName("Get movie successful")
    void getMovie() {
        when(proxy.getMovie(any(Integer.class))).thenReturn(MediaFixture.movie());
        when(proxy.getMovieCredits(any(Integer.class))).thenReturn(MediaFixture.credits());

        MovieResponseDTO result = service.getMovie(1);

        assertThat(result.getId()).isEqualTo(MediaFixture.movie().getId());
        assertThat(result.getTitle()).isEqualTo(MediaFixture.movie().getTitle());
    }

    @Test
    @DisplayName("Get tv successful")
    void getTV() {
        when(proxy.getTV(any(Integer.class))).thenReturn(MediaFixture.tv());

        TvResponseDTO result = service.getTV(1);

        assertThat(result.getId()).isEqualTo(MediaFixture.tv().getId());
        assertThat(result.getTitle()).isEqualTo(MediaFixture.tv().getTitle());
    }

}
