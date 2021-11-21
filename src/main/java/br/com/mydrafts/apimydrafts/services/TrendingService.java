package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.dto.tmdb.ResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrendingService {

    Page<ResultDTO> trendingTMDB(Pageable page);

}
