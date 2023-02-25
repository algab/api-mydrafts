package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.exceptions.handler.RestExceptionHandler;
import br.com.mydrafts.apimydrafts.services.TrendingService;
import br.com.mydrafts.apimydrafts.fixtures.TrendingFixture;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for TrendingController")
class TrendingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TrendingService service;

    private static final String PATH_TRENDING = "/v1/trending";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TrendingController(service))
            .setControllerAdvice(new RestExceptionHandler())
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .build();
    }

    @Test
    @DisplayName("Controller trending movies and tv")
    void trendingTMDB() throws Exception {
        when(this.service.trendingTMDB(PageRequest.of(0, 10)))
            .thenReturn(TrendingFixture.getPageTrending());

        RequestBuilder request = MockMvcRequestBuilders.get(PATH_TRENDING);

        mockMvc.perform(request).andExpect(status().isOk())
            .andExpect(jsonPath("totalPages").value(1))
            .andExpect(jsonPath("totalElements").value(1))
            .andExpect(jsonPath("content").isArray());
    }

}
