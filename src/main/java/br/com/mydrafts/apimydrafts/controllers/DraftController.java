package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.annotations.AuthorizeBody;
import br.com.mydrafts.apimydrafts.annotations.AuthorizeData;
import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.dto.DraftFormDTO;
import br.com.mydrafts.apimydrafts.dto.DraftUpdateFormDTO;
import br.com.mydrafts.apimydrafts.services.DraftService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/drafts")
@AllArgsConstructor
public class DraftController {

    private DraftService service;

    @PostMapping
    @AuthorizeBody("Draft")
    public ResponseEntity<DraftDTO> save(@RequestBody @Valid DraftFormDTO body) {
        long start = System.currentTimeMillis();
        log.info("DraftController.save - Start - Input: body [{}]", body);

        DraftDTO draft = this.service.save(body);

        log.info("DraftController.save - End - Input: body [{}] - Output: [{}] - time: {} ms",
            body, draft, System.currentTimeMillis() - start
        );
        return ResponseEntity.status(201).body(draft);
    }

    @GetMapping("/{id}")
    @AuthorizeData("Draft")
    public ResponseEntity<DraftDTO> search(@PathVariable("id") String id) {
        long start = System.currentTimeMillis();
        log.info("DraftController.search - Start - Input: id [{}]", id);

        DraftDTO draft = this.service.searchDraft(id);

        log.info("DraftController.search - End - Input: id [{}] - Output: [{}] - time: {} ms",
            id, draft, System.currentTimeMillis() - start
        );
        return ResponseEntity.ok(draft);
    }

    @PutMapping("/{id}")
    @AuthorizeData("Draft")
    public ResponseEntity<DraftDTO> update(@PathVariable("id") String id, @RequestBody @Valid DraftUpdateFormDTO body) {
        long start = System.currentTimeMillis();
        log.info("DraftController.update - Start - Input: id [{}], body [{}]", id, body);

        DraftDTO draft = this.service.updateDraft(id, body);

        log.info("DraftController.update - End - Input: id [{}], body [{}] - Output: [{}] - time: {} ms",
            id, body, draft, System.currentTimeMillis() - start
        );
        return ResponseEntity.ok(draft);
    }

    @DeleteMapping("/{id}")
    @AuthorizeData("Draft")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        long start = System.currentTimeMillis();
        log.info("DraftController.delete - Start - Input: id [{}]", id);

        this.service.deleteDraft(id);

        log.info("DraftController.delete - End - Input: id [{}] - time: {} ms", id, System.currentTimeMillis() - start);
        return ResponseEntity.noContent().build();
    }

}
