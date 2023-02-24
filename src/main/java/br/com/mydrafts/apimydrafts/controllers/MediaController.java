package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.dto.tmdb.MovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvResponseDTO;
import br.com.mydrafts.apimydrafts.services.MediaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/media")
@AllArgsConstructor
public class MediaController {

    private MediaService service;

    @GetMapping(path = "/movie/{id}")
    public ResponseEntity<MovieResponseDTO> getMovie(@PathVariable("id") Integer id) {
        long start = System.currentTimeMillis();
        log.debug("MediaController.getMovie - Start - Input: id [{}]", id);

        MovieResponseDTO movie = this.service.getMovie(id);

        log.debug("MediaController.getMovie - End - Input: id [{}] - Output: [{}] - time: {} ms",
            id, movie, System.currentTimeMillis() - start
        );
        return ResponseEntity.ok(movie);
    }

    @GetMapping(path = "/tv/{id}")
    public ResponseEntity<TvResponseDTO> getTV(@PathVariable("id") Integer id) {
        long start = System.currentTimeMillis();
        log.debug("MediaController.getTV - Start - Input: id [{}]", id);

        TvResponseDTO tv = this.service.getTV(id);

        log.debug("MediaController.getTV - End - Input: id [{}] - Output: [{}] - time: {} ms",
            id, tv, System.currentTimeMillis() - start
        );
        return ResponseEntity.ok(tv);
    }

}
