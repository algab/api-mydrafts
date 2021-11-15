package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.clients.TMDBClient;
import br.com.mydrafts.apimydrafts.dto.TMDBResultDTO;
import br.com.mydrafts.apimydrafts.utils.TrendingUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Trending Service")
class TrendingServiceTest {

    @Autowired
    private TrendingService service;

    @MockBean
    private TMDBClient client;

    @Test
    @DisplayName("Get Trending Movies and TV Successful")
    void getTrendingSuccessful() {
        when(client.trendingMovie(any(String.class), any(String.class))).thenReturn(TrendingUtil.responseTrendingMovie());
        when(client.trendingTv(any(String.class), any(String.class))).thenReturn(TrendingUtil.responseTrendingTV());

        Page<TMDBResultDTO> result = service.trendingTMDB(PageRequest.of(0, 10));

        assertThat(result.getNumber()).isZero();
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getTotalElements()).isEqualTo(20L);
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent().size()).isEqualTo(10);
    }

    @Test
    @DisplayName("Get Trending Pageable")
    void getTrendingPageable() {
        when(client.trendingMovie(any(String.class), any(String.class))).thenReturn(TrendingUtil.responseTrendingMovie());
        when(client.trendingTv(any(String.class), any(String.class))).thenReturn(TrendingUtil.responseTrendingTV());

        Page<TMDBResultDTO> result = service.trendingTMDB(PageRequest.of(0, 40));

        assertThat(result.getNumber()).isZero();
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(20L);
        assertThat(result.getContent()).isNotEmpty();
    }

}
