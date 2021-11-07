package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.dto.TMDBResultDTO;
import br.com.mydrafts.ApiMyDrafts.services.TrendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/trending")
public class TrendingController {

    @Autowired
    private TrendingService service;

    @GetMapping
    public ResponseEntity<Page<TMDBResultDTO>> trendingTMDB(@PageableDefault Pageable page) {
        log.info("TrendingController.trendingTMDB - Start - Input: page {}", page);

        Page<TMDBResultDTO> response = this.service.trendingTMDB(page);

        log.info("TrendingController.trendingTMDB - End - Output: response {}", response.toList());
        return ResponseEntity.ok(response);
    }
}
