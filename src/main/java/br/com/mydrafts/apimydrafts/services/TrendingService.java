package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.dto.TMDBResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrendingService {

    Page<TMDBResultDTO> trendingTMDB(Pageable page);

}
