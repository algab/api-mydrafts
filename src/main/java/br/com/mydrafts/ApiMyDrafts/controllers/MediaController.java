package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.dto.TMDBMovieDTO;
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
    public ResponseEntity<TMDBMovieDTO> getMovie(@PathVariable("id") Integer id) {
        TMDBMovieDTO movie = this.service.getMovie(id);
        return ResponseEntity.ok(movie);
    }
}
