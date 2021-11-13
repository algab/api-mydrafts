package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.dto.TMDBMovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.TMDBTvResponseDTO;
import br.com.mydrafts.apimydrafts.services.MediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/media")
public class MediaController {

    @Autowired
    private MediaService service;

    @GetMapping(path = "/movie/{id}")
    public ResponseEntity<TMDBMovieResponseDTO> getMovie(@PathVariable("id") Integer id) {
        log.info("MediaController.getMovie - Start - Input: id {}", id);

        TMDBMovieResponseDTO movie = this.service.getMovie(id);

        log.info("MediaController.getMovie - End - Input: id {} - Output: {}", id, movie);
        return ResponseEntity.ok(movie);
    }

    @GetMapping(path = "/tv/{id}")
    public ResponseEntity<TMDBTvResponseDTO> getTV(@PathVariable("id") Integer id) {
        log.info("MediaController.getTV - Start - Input: id {}", id);

        TMDBTvResponseDTO tv = this.service.getTV(id);

        log.info("MediaController.getTV - End - Input: id {} - Output: {}", id, tv);
        return ResponseEntity.ok(tv);
    }
}
