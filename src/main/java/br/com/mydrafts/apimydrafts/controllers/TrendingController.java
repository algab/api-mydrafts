package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.dto.tmdb.ResultDTO;
import br.com.mydrafts.apimydrafts.services.TrendingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@AllArgsConstructor
public class TrendingController {

    private TrendingService service;

    @GetMapping
    public ResponseEntity<Page<ResultDTO>> trendingTMDB(@PageableDefault Pageable page) {
        log.info("TrendingController.trendingTMDB - Start - Input: page {}", page);

        Page<ResultDTO> response = this.service.trendingTMDB(page);

        log.info("TrendingController.trendingTMDB - End - Input: page {} - Output: {}", page, response.getContent());
        return ResponseEntity.ok(response);
    }

}
