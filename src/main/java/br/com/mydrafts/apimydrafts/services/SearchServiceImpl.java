package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.clients.TMDBProxy;
import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResultDTO;
import br.com.mydrafts.apimydrafts.utils.Pagination;
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
    public Page<ResultDTO> searchTMDB(Pageable page, Media media, String name) {
        log.info("SearchServiceImpl.searchTMDB - Start - Input: page {}, media {}, name {}", page, media, name);

        List<ResultDTO> content = new ArrayList<>();
        if (media == Media.TV) {
            this.searchTV(content, name);
        } else {
            this.searchMovie(content, name);
        }
        content.sort(Comparator.comparing(ResultDTO::getPopularity).reversed());

        Page<ResultDTO> pageResult = Pagination.applyPage(content, page);
        log.info("SearchServiceImpl.searchTMDB - End - Input: page {}, media {}, name {} - Output: {}", page, media, name, pageResult);
        return pageResult;
    }

    private void searchMovie(List<ResultDTO> content, String name) {
        ResponseDTO movies = this.tmdbProxy.searchMovie(name);
        movies.getResults().stream().map(result -> {
            result.setMedia(Media.MOVIE);
            return result;
        }).collect(Collectors.toList());
        content.addAll(movies.getResults());
    }

    private void searchTV(List<ResultDTO> content, String name) {
        ResponseDTO tv = this.tmdbProxy.searchTV(name);
        tv.getResults().stream().map(result -> {
            result.setMedia(Media.TV);
            return result;
        }).collect(Collectors.toList());
        content.addAll(tv.getResults());
    }

}
