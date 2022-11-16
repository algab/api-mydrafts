package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.dto.tmdb.ResultDTO;
import br.com.mydrafts.apimydrafts.exceptions.handler.RestExceptionHandler;
import br.com.mydrafts.apimydrafts.services.TrendingService;
import br.com.mydrafts.apimydrafts.utils.TestUtil;
import br.com.mydrafts.apimydrafts.builder.TrendingBuilder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Trending Controller")
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
        String json = TestUtil.readFileAsString("/json/trending.json");
        when(this.service.trendingTMDB(PageRequest.of(0, 10))).thenReturn(trending());

        RequestBuilder request = MockMvcRequestBuilders.get(PATH_TRENDING)
            .content(json)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        mockMvc.perform(request).andExpect(jsonPath("totalPages").value(1));
        mockMvc.perform(request).andExpect(jsonPath("totalElements").value(1));
        mockMvc.perform(request).andExpect(jsonPath("content").isArray());
    }

    private Page<ResultDTO> trending() {
        PageRequest page = PageRequest.of(0, 10);
        List<ResultDTO> contents = Collections.singletonList(TrendingBuilder.trending());
        return new PageImpl<>(contents, page, 1);
    }

}
