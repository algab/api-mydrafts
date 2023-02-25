package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.clients.TMDBProxy;
import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResultDTO;
import br.com.mydrafts.apimydrafts.fixtures.SearchFixture;
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
@DisplayName("Tests for SearchService")
class SearchServiceTest {

    private SearchService service;

    @Mock
    private TMDBProxy proxy;

    @BeforeEach
    void setup() {
        service = new SearchServiceImpl(proxy);
    }

    @Test
    @DisplayName("Search movie by name")
    void searchMovie() {
        when(proxy.searchMovie("Shang-Chi")).thenReturn(SearchFixture.responseSearchMovie());

        Page<ResultDTO> page = service.searchTMDB(PageRequest.of(0, 10), Media.MOVIE, "Shang-Chi");

        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getTotalElements()).isEqualTo(1L);
        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("Search tv show by name")
     void searchTV() {
        when(proxy.searchTV("What")).thenReturn(SearchFixture.responseSearchTV());

        Page<ResultDTO> page = service.searchTMDB(PageRequest.of(0, 10), Media.TV, "What");

        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getTotalElements()).isEqualTo(1L);
        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("Search content empty")
    void searchEmpty() {
        when(proxy.searchTV("What")).thenReturn(SearchFixture.responseSearchTV());

        Page<ResultDTO> page = service.searchTMDB(PageRequest.of(5, 20), Media.TV, "What");

        assertThat(page.getContent()).isEmpty();
        assertThat(page.getNumber()).isEqualTo(5);
    }

}
