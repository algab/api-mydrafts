package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.clients.TMDBProxy;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResultDTO;
import br.com.mydrafts.apimydrafts.utils.PaginationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TrendingServiceImpl implements TrendingService {

    private TMDBProxy tmdbProxy;

    @Override
    public Page<ResultDTO> trendingTMDB(Pageable page) {
        List<ResultDTO> content = new ArrayList<>();
        this.trendingMovie(content);
        this.trendingTV(content);
        content.sort(Comparator.comparing(ResultDTO::getPopularity).reversed());
        return PaginationUtil.applyPage(content, page);
    }

    private void trendingMovie(List<ResultDTO> content) {
        ResponseDTO movies = this.tmdbProxy.trendingMovie();
        content.addAll(movies.getResults().subList(0, 10));
    }

    private void trendingTV(List<ResultDTO> content) {
        ResponseDTO tv = this.tmdbProxy.trendingTV();
        content.addAll(tv.getResults().subList(0, 10));
    }

}
