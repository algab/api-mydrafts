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

import java.util.ArrayList;
import java.util.Comparator;
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
        List<TMDBResultDTO> content = new ArrayList<>();
        this.trendingMovie(content);
        this.trendingTV(content);
        content.sort(Comparator.comparing(TMDBResultDTO::getPopularity).reversed());
        Integer initSize = Long.valueOf(page.getOffset()).intValue();
        Integer endSize = endPageSize(initSize, page.getPageSize());
        return new PageImpl<>(content.subList(initSize, endSize), page, 20);
    }

    private void trendingMovie(List<TMDBResultDTO> content) {
        TMDBResponseDTO movies = this.client.trendingMovie(this.apiKey, this.language);
        content.addAll(movies.getResults().subList(0, 10));
    }

    private void trendingTV(List<TMDBResultDTO> content) {
        TMDBResponseDTO tv = this.client.trendingTv(this.apiKey, this.language);
        content.addAll(tv.getResults().subList(0, 10));
    }

    private Integer endPageSize(Integer initSize, Integer pageSize) {
        Integer endSize = initSize + pageSize;
        if (endSize > 20) {
            int rest = endSize - 20;
            endSize -= rest;
        }
        return endSize;
    }
}
