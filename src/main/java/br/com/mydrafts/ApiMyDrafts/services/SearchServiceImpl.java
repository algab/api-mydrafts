package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.clients.TMDBProxy;
import br.com.mydrafts.ApiMyDrafts.constants.Media;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBResponseDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBResultDTO;
import br.com.mydrafts.ApiMyDrafts.utils.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private TMDBProxy tmdbProxy;

    @Override
    public Page<TMDBResultDTO> searchTMDB(Pageable page, Media media, String name) {
        List<TMDBResultDTO> content = new ArrayList<>();
        if (media == Media.tv) {
            this.searchTV(content, name);
        } else {
            this.searchMovie(content, name);
        }
        content.sort(Comparator.comparing(TMDBResultDTO::getPopularity).reversed());
        return Pagination.applyPage(content, page);
    }

    private void searchMovie(List<TMDBResultDTO> content, String name) {
        TMDBResponseDTO movies = this.tmdbProxy.searchMovie(name);
        movies.getResults().stream().map(result -> {
            result.setMedia(Media.movie);
            return result;
        }).collect(Collectors.toList());
        content.addAll(movies.getResults());
    }

    private void searchTV(List<TMDBResultDTO> content, String name) {
        TMDBResponseDTO tv = this.tmdbProxy.searchTV(name);
        tv.getResults().stream().map(result -> {
            result.setMedia(Media.tv);
            return result;
        }).collect(Collectors.toList());
        content.addAll(tv.getResults());
    }
}
