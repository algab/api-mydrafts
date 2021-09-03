package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.dto.TMDBMovieResponseDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBTvResponseDTO;
import br.com.mydrafts.ApiMyDrafts.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/media")
public class MediaController {
    @Autowired
    private MediaService service;

    @GetMapping(path = "/movie/{id}")
    public ResponseEntity<TMDBMovieResponseDTO> getMovie(@PathVariable("id") Integer id) {
        TMDBMovieResponseDTO movie = this.service.getMovie(id);
        return ResponseEntity.ok(movie);
    }

    @GetMapping(path = "/tv/{id}")
    public ResponseEntity<TMDBTvResponseDTO> getTV(@PathVariable("id") Integer id) {
        TMDBTvResponseDTO tv = this.service.getTV(id);
        return ResponseEntity.ok(tv);
    }
}
