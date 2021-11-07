package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.dto.FavoriteDTO;
import br.com.mydrafts.ApiMyDrafts.dto.FavoriteFormDTO;
import br.com.mydrafts.ApiMyDrafts.services.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService service;

    @PostMapping
    public ResponseEntity<FavoriteDTO> save(@RequestBody @Valid FavoriteFormDTO body) {
        log.info("FavoriteController.save - Start - Input: body {}", body);

        FavoriteDTO favorite = this.service.save(body);

        log.info("FavoriteController.save - End - Output: favorite {}", favorite);
        return ResponseEntity.status(201).body(favorite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        log.info("FavoriteController.delete - Start - Input: id {}", id);

        this.service.delete(id);

        log.info("FavoriteController.delete - End");
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
