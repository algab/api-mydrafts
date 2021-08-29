package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.clients.TMDBClient;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBResponseDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TrendingServiceImpl implements TrendingService {
    @Value("${tmdb.api-key}")
    private String apiKey;

    @Value("${tmdb.language}")
    private String language;

    @Autowired
    private TMDBClient client;

    @Override
    public Page<TMDBResultDTO> trendingTMDB(Pageable page) {
        Integer init = Long.valueOf(page.getOffset()).intValue();
        Integer end = init + page.getPageSize();
        if (end > 20) {
            Integer rest = end - 20;
            end -= rest;
        }
        TMDBResponseDTO movies = this.client.trendingMovie(apiKey, language);
        TMDBResponseDTO tv = this.client.trendingTv(apiKey, language);
        List<TMDBResultDTO> content = movies.getResults().subList(0, 10);
        content.addAll(tv.getResults().subList(0, 10));
        return new PageImpl<>(content.subList(init, end), page, 20);
    }
}
