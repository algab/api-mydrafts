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
import java.util.List;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {
    @Value("${tmdb.api-key}")
    private String apiKey;

    @Value("${tmdb.language}")
    private String language;

    @Autowired
    private TMDBClient client;

    @Override
    public Page<TMDBResultDTO> searchTMDB(Pageable page, String name) {
        Integer init = Long.valueOf(page.getOffset()).intValue();
        Integer end = init + page.getPageSize();
        TMDBResponseDTO movies = this.client.searchMovie(apiKey, language, name);
        TMDBResponseDTO tv = this.client.searchTv(apiKey, language, name);
        List<TMDBResultDTO> content = new ArrayList<>();
        if (movies.getResults().size() > 10) {
            content.addAll(movies.getResults().subList(0, 10));
        } else {
            content.addAll(movies.getResults().subList(0, movies.getResults().size()));
        }
        if (tv.getResults().size() > 10) {
            content.addAll(tv.getResults().subList(0, 10));
        } else {
            content.addAll(tv.getResults().subList(0, tv.getResults().size()));
        }
        if (end > 20) {
            Integer rest = end - 20;
            end -= rest;
        } else {
            end = content.size();
        }
        return new PageImpl<>(content.subList(init, end), page, content.size());
    }
}
