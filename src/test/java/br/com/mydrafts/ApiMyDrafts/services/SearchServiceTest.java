package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.clients.TMDBClient;
import br.com.mydrafts.ApiMyDrafts.constants.Media;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBResultDTO;
import br.com.mydrafts.ApiMyDrafts.utils.SearchUtil;
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
public class SearchServiceTest {

    @Autowired
    private SearchService service;

    @MockBean
    private TMDBClient client;

    @Test
    @DisplayName("Search movie by name")
    public void searchMovie() {
        when(client.searchMovie(any(String.class), any(String.class), any(String.class))).thenReturn(SearchUtil.responseSearchMovie());

        Page<TMDBResultDTO> page = service.searchTMDB(PageRequest.of(0, 10), Media.movie, "Shang-Chi");

        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getTotalElements()).isEqualTo(1L);
        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Search tv show by name")
    public void searchTV() {
        when(client.searchTv(any(String.class), any(String.class), any(String.class))).thenReturn(SearchUtil.responseSearchTV());

        Page<TMDBResultDTO> page = service.searchTMDB(PageRequest.of(0, 40), Media.tv, "What");

        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getTotalElements()).isEqualTo(1L);
        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent().size()).isEqualTo(1);
    }
}
