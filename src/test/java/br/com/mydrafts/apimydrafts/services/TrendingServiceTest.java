package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.clients.TMDBProxy;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResultDTO;
import br.com.mydrafts.apimydrafts.fixtures.TrendingFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Trending Service")
class TrendingServiceTest {

    private TrendingService service;

    @Mock
    private TMDBProxy proxy;

    @BeforeEach
    void setup() {
        service = new TrendingServiceImpl(proxy);
    }

    @Test
    @DisplayName("Get Trending Movies and TV Successful")
    void getTrendingSuccessful() {
        when(proxy.trendingMovie()).thenReturn(TrendingFixture.responseTrendingMovie());
        when(proxy.trendingTV()).thenReturn(TrendingFixture.responseTrendingTV());

        Page<ResultDTO> result = service.trendingTMDB(PageRequest.of(0, 10));

        assertThat(result.getNumber()).isZero();
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getTotalElements()).isEqualTo(20L);
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent()).hasSize(10);
    }

    @Test
    @DisplayName("Get Trending Pageable")
    void getTrendingPageable() {
        when(proxy.trendingMovie()).thenReturn(TrendingFixture.responseTrendingMovie());
        when(proxy.trendingTV()).thenReturn(TrendingFixture.responseTrendingTV());

        Page<ResultDTO> result = service.trendingTMDB(PageRequest.of(0, 40));

        assertThat(result.getNumber()).isZero();
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(20L);
        assertThat(result.getContent()).isNotEmpty();
    }

}
