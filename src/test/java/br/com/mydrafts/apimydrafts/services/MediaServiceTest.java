package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.clients.TMDBClient;
import br.com.mydrafts.apimydrafts.dto.TMDBMovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.TMDBTvResponseDTO;
import br.com.mydrafts.apimydrafts.utils.MediaUtil;
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
public class MediaServiceTest {

    @Autowired
    private MediaService service;

    @MockBean
    private TMDBClient client;

    @Test
    @DisplayName("Get movie successful")
    public void getMovie() {
        when(client.movie(any(Integer.class), any(String.class), any(String.class))).thenReturn(MediaUtil.movie());
        when(client.movieCredits(any(Integer.class), any(String.class), any(String.class))).thenReturn(MediaUtil.credits());

        TMDBMovieResponseDTO result = service.getMovie(1);

        assertThat(result.getId()).isEqualTo(MediaUtil.movie().getId());
        assertThat(result.getTitle()).isEqualTo(MediaUtil.movie().getTitle());
    }

    @Test
    @DisplayName("Get tv successful")
    public void getTV() {
        when(client.tv(any(Integer.class), any(String.class), any(String.class))).thenReturn(MediaUtil.tv());

        TMDBTvResponseDTO result = service.getTV(1);

        assertThat(result.getId()).isEqualTo(MediaUtil.tv().getId());
        assertThat(result.getTitle()).isEqualTo(MediaUtil.tv().getTitle());
    }

}
