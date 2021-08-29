package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.dto.TMDBResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrendingService {

    Page<TMDBResultDTO> trendingTMDB(Pageable page);

}
