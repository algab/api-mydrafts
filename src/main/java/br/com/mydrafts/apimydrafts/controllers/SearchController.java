package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResultDTO;
import br.com.mydrafts.apimydrafts.services.SearchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/search")
@AllArgsConstructor
public class SearchController {

    private SearchService service;

    @GetMapping
    public ResponseEntity<Page<ResultDTO>> searchTMDB(
        @PageableDefault Pageable page,
        @RequestParam(value = "media", required = false) String type,
        @RequestParam("name") String name
    ) {
        long start = System.currentTimeMillis();
        log.debug("SearchController.searchTMDB - Start - Input: page [{}], type [{}], name [{}]", page, type, name);

        Page<ResultDTO> response = this.service.searchTMDB(page, Media.fromValue(type), name);

        log.debug("SearchController.searchTMDB - End - Input: page [{}], type [{}], name [{}] - Output: [{}] - time: {} ms",
            page, type, name, response.getContent(), System.currentTimeMillis() - start
        );
        return ResponseEntity.ok(response);
    }

}
