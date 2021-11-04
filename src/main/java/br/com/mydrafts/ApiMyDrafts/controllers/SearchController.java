package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.constants.Media;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBResultDTO;
import br.com.mydrafts.ApiMyDrafts.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/search")
public class SearchController {

    @Autowired
    private SearchService service;

    @GetMapping
    public ResponseEntity<Page<TMDBResultDTO>> searchTMDB(
            @PageableDefault Pageable page,
            @RequestParam(value = "media", required = false) String type,
            @RequestParam("name") String name
    ) {
        Page<TMDBResultDTO> response = this.service.searchTMDB(page, Media.fromValue(type), name);
        return ResponseEntity.ok(response);
    }
}
