package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.annotations.AuthorizeBody;
import br.com.mydrafts.apimydrafts.annotations.AuthorizeData;
import br.com.mydrafts.apimydrafts.dto.FavoriteDTO;
import br.com.mydrafts.apimydrafts.dto.FavoriteFormDTO;
import br.com.mydrafts.apimydrafts.services.FavoriteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/favorites")
@AllArgsConstructor
public class FavoriteController {

    private FavoriteService service;

    @PostMapping
    @AuthorizeBody("Favorite")
    public ResponseEntity<FavoriteDTO> save(@RequestBody @Valid FavoriteFormDTO body) {
        long start = System.currentTimeMillis();
        log.info("FavoriteController.save - Start - Input: body [{}]", body);

        FavoriteDTO favorite = this.service.save(body);

        log.info("FavoriteController.save - End - Input: body [{}] - time: {} ms", body, System.currentTimeMillis() - start);
        return ResponseEntity.status(201).body(favorite);
    }

    @DeleteMapping("/{id}")
    @AuthorizeData("Favorite")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        long start = System.currentTimeMillis();
        log.info("FavoriteController.delete - Start - Input: id [{}]", id);

        this.service.delete(id);

        log.info("FavoriteController.delete - End - Input: id [{}] - time: {} ms", id, System.currentTimeMillis() - start);
        return ResponseEntity.noContent().build();
    }

}
