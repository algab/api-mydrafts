package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.dto.DraftDTO;
import br.com.mydrafts.ApiMyDrafts.dto.DraftFormDTO;
import br.com.mydrafts.ApiMyDrafts.services.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/drafts")
public class DraftController {

    @Autowired
    private DraftService service;

    @PostMapping
    public ResponseEntity<DraftDTO> save(@RequestBody @Valid DraftFormDTO body) {
        DraftDTO draft = this.service.save(body);
        return ResponseEntity.status(201).body(draft);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Page<DraftDTO>> getDraftsByUser(@PathVariable("id") String id, @PageableDefault Pageable page) {
        Page<DraftDTO> pageDraft = this.service.getDrafts(page, id);
        return ResponseEntity.ok(pageDraft);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DraftDTO> search(@PathVariable("id") String id) {
        DraftDTO draft = this.service.searchDraft(id);
        return ResponseEntity.ok(draft);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DraftDTO> update(@PathVariable("id") String id, @RequestBody @Valid DraftFormDTO body) {
        DraftDTO draft = this.service.updateDraft(id, body);
        return ResponseEntity.ok(draft);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        this.service.deleteDraft(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
