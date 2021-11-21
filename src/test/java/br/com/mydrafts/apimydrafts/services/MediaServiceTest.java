package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.clients.TMDBClient;
import br.com.mydrafts.apimydrafts.dto.TMDBMovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.TMDBTvResponseDTO;
import br.com.mydrafts.apimydrafts.builder.MediaBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Media Service")
class MediaServiceTest {

    @Autowired
    private MediaService service;

    @MockBean
    private TMDBClient client;

    @Test
    @DisplayName("Get movie successful")
    void getMovie() {
        when(client.movie(any(Integer.class), any(String.class), any(String.class))).thenReturn(MediaBuilder.movie());
        when(client.movieCredits(any(Integer.class), any(String.class), any(String.class))).thenReturn(MediaBuilder.credits());

        TMDBMovieResponseDTO result = service.getMovie(1);

        assertThat(result.getId()).isEqualTo(MediaBuilder.movie().getId());
        assertThat(result.getTitle()).isEqualTo(MediaBuilder.movie().getTitle());
    }

    @Test
    @DisplayName("Get tv successful")
    void getTV() {
        when(client.tv(any(Integer.class), any(String.class), any(String.class))).thenReturn(MediaBuilder.tv());

        TMDBTvResponseDTO result = service.getTV(1);

        assertThat(result.getId()).isEqualTo(MediaBuilder.tv().getId());
        assertThat(result.getTitle()).isEqualTo(MediaBuilder.tv().getTitle());
    }

}
