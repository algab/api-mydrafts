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
import java.util.stream.Collectors;

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
        List<TMDBResultDTO> content = new ArrayList<>();
        this.searchMovie(content, name);
        this.searchTV(content, name);
        Integer initSize = Long.valueOf(page.getOffset()).intValue();
        Integer endSize = endPageSize(initSize, page.getPageSize(), content.size());
        return new PageImpl<>(content.subList(initSize, endSize), page, content.size());
    }

    private void searchMovie(List<TMDBResultDTO> content, String name) {
        TMDBResponseDTO movies = this.client.searchMovie(this.apiKey, this.language, name);
        movies.getResults().stream().map(result -> {
            result.setMedia("movie");
            return result;
        }).collect(Collectors.toList());
        if (movies.getResults().size() > 10) {
            content.addAll(movies.getResults().subList(0, 10));
        } else {
            content.addAll(movies.getResults().subList(0, movies.getResults().size()));
        }
    }

    private void searchTV(List<TMDBResultDTO> content, String name) {
        TMDBResponseDTO tv = this.client.searchTv(this.apiKey, this.language, name);
        tv.getResults().stream().map(result -> {
            result.setMedia("tv");
            return result;
        }).collect(Collectors.toList());
        if (tv.getResults().size() > 10) {
            content.addAll(tv.getResults().subList(0, 10));
        } else {
            content.addAll(tv.getResults().subList(0, tv.getResults().size()));
        }

    }

    private Integer endPageSize(Integer initSize, Integer pageSize, Integer total) {
        Integer endSize = initSize + pageSize;
        if (endSize > 20) {
            Integer rest = endSize - 20;
            endSize -= rest;
        }
        return endSize;
    }
}
