package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.clients.TMDBProxy;
import br.com.mydrafts.ApiMyDrafts.documents.Draft;
import br.com.mydrafts.ApiMyDrafts.documents.Production;
import br.com.mydrafts.ApiMyDrafts.documents.User;
import br.com.mydrafts.ApiMyDrafts.dto.*;
import br.com.mydrafts.ApiMyDrafts.exceptions.BusinessException;
import br.com.mydrafts.ApiMyDrafts.repository.DraftRepository;
import br.com.mydrafts.ApiMyDrafts.repository.ProductionRepository;
import br.com.mydrafts.ApiMyDrafts.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class DraftServiceImpl implements DraftService {

    @Autowired
    private DraftRepository draftRepository;

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TMDBProxy tmdbProxy;

    @Autowired
    private ModelMapper mapper;

    private static final String CONFLICT = "CONFLICT";
    private static final String NOT_FOUND = "NOT FOUND";
    private static final Integer STATUS_CONFLICT = 409;
    private static final Integer STATUS_NOT_FOUND = 404;
    private static final String MESSAGE_DRAFT_CONFLICT = "Draft already registered";
    private static final String MESSAGE_USER_NOT_FOUND = "User not found";
    private static final String MESSAGE_DRAFT_NOT_FOUND = "Draft not found";

    @Override
    public DraftDTO save(DraftFormDTO body) {
        log.info("DraftServiceImpl.save - Start - Input: body {}", body);
        Draft draft = Draft.builder().description(body.getDescription()).rating(body.getRating()).build();
        User user = this.userRepository.findById(body.getUserID())
                .orElseThrow(() -> {
                    log.error("DraftServiceImpl.save - Error: {}", MESSAGE_USER_NOT_FOUND);
                    return new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_USER_NOT_FOUND);
                });
        draft.setUser(user);
        Optional<Production> production = this.productionRepository.findByTmdbID(body.getTmdbID());
        if (production.isPresent()) {
            if (this.draftRepository.existsByUserAndProduction(user, production.get())) {
                log.error("DraftServiceImpl.save - Error: {}", MESSAGE_DRAFT_CONFLICT);
                throw new BusinessException(STATUS_CONFLICT, CONFLICT, MESSAGE_DRAFT_CONFLICT);
            }
            draft.setProduction(production.get());
        } else {
            Production saveProduction = this.productionRepository.save(this.tmdbProxy.findProduction(body.getMedia(), body.getTmdbID()));
            draft.setProduction(saveProduction);
        }
        log.info("DraftServiceImpl.save - End");
        return mapper.map(this.draftRepository.save(draft), DraftDTO.class);
    }

    @Override
    public DraftDTO searchDraft(String id) {
        log.info("DraftServiceImpl.searchDraft - Start - Input: id {}", id);
        Draft draft = this.draftRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("DraftServiceImpl.searchDraft - Error: {}", MESSAGE_DRAFT_NOT_FOUND);
                    return new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_DRAFT_NOT_FOUND);
                });
        log.info("DraftServiceImpl.searchDraft - End");
        return mapper.map(draft, DraftDTO.class);
    }

    @Override
    public DraftDTO updateDraft(String id, DraftFormDTO body) {
        log.info("DraftServiceImpl.updateDraft - Start - Input: id {}, body {}", id, body);
        Draft draft = this.draftRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("DraftServiceImpl.updateDraft - Error: {}", MESSAGE_DRAFT_NOT_FOUND);
                    return new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_DRAFT_NOT_FOUND);
                });
        this.userRepository.findById(body.getUserID())
                .orElseThrow(() -> {
                    log.error("DraftServiceImpl.updateDraft - Error: {}", MESSAGE_USER_NOT_FOUND);
                    return new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_USER_NOT_FOUND);
                });
        draft.setRating(body.getRating());
        draft.setDescription(body.getDescription());
        DraftDTO draftResponse = mapper.map(this.draftRepository.save(draft), DraftDTO.class);
        log.info("DraftServiceImpl.updateDraft - End - Output: {}", draftResponse);
        return draftResponse;
    }

    @Override
    public void deleteDraft(String id) {
        log.info("DraftServiceImpl.deleteDraft - Start - Input: id {}", id);
        Draft draft = this.draftRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("DraftServiceImpl.updateDraft - Error: {}", MESSAGE_DRAFT_NOT_FOUND);
                    return new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_DRAFT_NOT_FOUND);
                });
        log.info("DraftServiceImpl.deleteDraft - End");
        this.draftRepository.delete(draft);
    }

}
