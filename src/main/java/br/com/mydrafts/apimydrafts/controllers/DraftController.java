package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.dto.DraftFormDTO;
import br.com.mydrafts.apimydrafts.services.DraftService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/drafts")
public class DraftController {

    @Autowired
    private DraftService service;

    @PostMapping
    public ResponseEntity<DraftDTO> save(@RequestBody @Valid DraftFormDTO body) {
        log.info("DraftController.save - Start - Input: body {}", body);

        DraftDTO draft = this.service.save(body);

        log.info("DraftController.save - End - Input: body {} - Output: {}", body, draft);
        return ResponseEntity.status(201).body(draft);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DraftDTO> search(@PathVariable("id") String id) {
        log.info("DraftController.search - Start - Input: id {}", id);

        DraftDTO draft = this.service.searchDraft(id);

        log.info("DraftController.search - End - Input: id {} - Output: {}", id, draft);
        return ResponseEntity.ok(draft);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DraftDTO> update(@PathVariable("id") String id, @RequestBody @Valid DraftFormDTO body) {
        log.info("DraftController.update - Start - Input: id {}, body {}", id, body);

        DraftDTO draft = this.service.updateDraft(id, body);

        log.info("DraftController.update - End - Input: id {}, body {} - Output: {}", id, body, draft);
        return ResponseEntity.ok(draft);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        log.info("DraftController.delete - Start - Input: id {}", id);

        this.service.deleteDraft(id);

        log.info("DraftController.delete - End - Input: id {}", id);
        return ResponseEntity.noContent().build();
    }

}
