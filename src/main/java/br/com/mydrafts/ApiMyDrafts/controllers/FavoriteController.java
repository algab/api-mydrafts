package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.dto.FavoriteDTO;
import br.com.mydrafts.ApiMyDrafts.dto.FavoriteFormDTO;
import br.com.mydrafts.ApiMyDrafts.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService service;

    @PostMapping
    public ResponseEntity<FavoriteDTO> save(@RequestBody @Valid FavoriteFormDTO body) {
        FavoriteDTO favorite = this.service.save(body);
        return ResponseEntity.status(201).body(favorite);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Page<FavoriteDTO>> getFavorites(@PathVariable("id") String id, @PageableDefault Pageable page) {
        Page<FavoriteDTO> content = this.service.getFavorites(page, id);
        return ResponseEntity.ok(content);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        this.service.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
