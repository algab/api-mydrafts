package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.clients.TMDBClient;
import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResultDTO;
import br.com.mydrafts.apimydrafts.builder.SearchBuilder;
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
@DisplayName("Tests for Search Service")
class SearchServiceTest {

    @Autowired
    private SearchService service;

    @MockBean
    private TMDBClient client;

    @Test
    @DisplayName("Search movie by name")
    void searchMovie() {
        when(client.searchMovie(any(String.class), any(String.class), any(String.class))).thenReturn(SearchBuilder.responseSearchMovie());

        Page<ResultDTO> page = service.searchTMDB(PageRequest.of(0, 10), Media.MOVIE, "Shang-Chi");

        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getTotalElements()).isEqualTo(1L);
        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Search tv show by name")
     void searchTV() {
        when(client.searchTv(any(String.class), any(String.class), any(String.class))).thenReturn(SearchBuilder.responseSearchTV());

        Page<ResultDTO> page = service.searchTMDB(PageRequest.of(0, 10), Media.TV, "What");

        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getTotalElements()).isEqualTo(1L);
        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Search content empty")
    void searchEmpty() {
        when(client.searchTv(any(String.class), any(String.class), any(String.class))).thenReturn(SearchBuilder.responseSearchTV());

        Page<ResultDTO> page = service.searchTMDB(PageRequest.of(5, 20), Media.TV, "What");

        assertThat(page.getContent()).isEmpty();
        assertThat(page.getNumber()).isEqualTo(5);
    }
}
