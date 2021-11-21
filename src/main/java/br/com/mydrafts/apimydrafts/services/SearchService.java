package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService {

    Page<ResultDTO> searchTMDB(Pageable page, Media media, String name);

}
