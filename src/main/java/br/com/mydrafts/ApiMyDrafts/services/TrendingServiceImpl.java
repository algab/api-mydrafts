package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.clients.TMDBProxy;
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

@Slf4j
@Service
public class TrendingServiceImpl implements TrendingService {

    @Autowired
    private TMDBProxy tmdbProxy;

    @Override
    public Page<TMDBResultDTO> trendingTMDB(Pageable page) {
        List<TMDBResultDTO> content = new ArrayList<>();
        this.trendingMovie(content);
        this.trendingTV(content);
        content.sort(Comparator.comparing(TMDBResultDTO::getPopularity).reversed());
        return Pagination.applyPage(content, page);
    }

    private void trendingMovie(List<TMDBResultDTO> content) {
        TMDBResponseDTO movies = this.tmdbProxy.trendingMovie();
        content.addAll(movies.getResults().subList(0, 10));
    }

    private void trendingTV(List<TMDBResultDTO> content) {
        TMDBResponseDTO tv = this.tmdbProxy.trendingTV();
        content.addAll(tv.getResults().subList(0, 10));
    }
}
