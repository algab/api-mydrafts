package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.Draft;
import br.com.mydrafts.apimydrafts.documents.Production;
import br.com.mydrafts.apimydrafts.documents.User;
import br.com.mydrafts.apimydrafts.dto.*;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.DraftRepository;
import br.com.mydrafts.apimydrafts.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class DraftServiceImpl implements DraftService {

    @Autowired
    private DraftRepository draftRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductionService productionService;

    @Autowired
    private ModelMapper mapper;

    private static final String MESSAGE_DRAFT_CONFLICT = "Draft already registered";
    private static final String MESSAGE_USER_NOT_FOUND = "User not found";
    private static final String MESSAGE_DRAFT_NOT_FOUND = "Draft not found";
    private static final String MESSAGE_TV_BAD_REQUEST = "The season cannot be null";

    @Override
    public DraftDTO save(DraftFormDTO body) {
        log.info("DraftServiceImpl.save - Start - Input: body {}", body);

        User user = this.userRepository.findById(body.getUserID())
                .orElseThrow(() -> {
                    log.error("DraftServiceImpl.save - Error: {}", MESSAGE_USER_NOT_FOUND);
                    return new BusinessException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), MESSAGE_USER_NOT_FOUND);
                });

        Draft draft = setDataDraft(body, user);
        DraftDTO draftResult = mapper.map(this.draftRepository.save(draft), DraftDTO.class);
        log.info("DraftServiceImpl.save - End - Input: body {} - Output: {}", body, draftResult);
        return draftResult;
    }

    @Override
    public DraftDTO searchDraft(String id) {
        log.info("DraftServiceImpl.searchDraft - Start - Input: id {}", id);

        Draft draft = this.draftRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("DraftServiceImpl.searchDraft - Error: {}", MESSAGE_DRAFT_NOT_FOUND);
                    return new BusinessException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), MESSAGE_DRAFT_NOT_FOUND);
                });

        DraftDTO draftResult = mapper.map(draft, DraftDTO.class);
        log.info("DraftServiceImpl.searchDraft - End - Input: id {} - Output: {}", id, draftResult);
        return draftResult;
    }

    @Override
    public DraftDTO updateDraft(String id, DraftFormDTO body) {
        log.info("DraftServiceImpl.updateDraft - Start - Input: id {}, body {}", id, body);

        Draft findDraft = this.draftRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("DraftServiceImpl.updateDraft - Search draft - Error: {}", MESSAGE_DRAFT_NOT_FOUND);
                    return new BusinessException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), MESSAGE_DRAFT_NOT_FOUND);
                });

        if (!this.userRepository.existsById(body.getUserID())) {
            log.error("DraftServiceImpl.updateDraft - Search user - Error: {}", MESSAGE_USER_NOT_FOUND);
            throw new BusinessException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), MESSAGE_USER_NOT_FOUND);
        }

        Draft draft = updateDataDraft(findDraft, body);
        DraftDTO draftResult = mapper.map(this.draftRepository.save(draft), DraftDTO.class);
        log.info("DraftServiceImpl.updateDraft - End - Input: id {}, body {} - Output: {}", id, body, draftResult);
        return draftResult;
    }

    @Override
    public void deleteDraft(String id) {
        log.info("DraftServiceImpl.deleteDraft - Start - Input: id {}", id);

        Draft draft = this.draftRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("DraftServiceImpl.updateDraft - Error: {}", MESSAGE_DRAFT_NOT_FOUND);
                    return new BusinessException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), MESSAGE_DRAFT_NOT_FOUND);
                });

        log.info("DraftServiceImpl.deleteDraft - End - Input: id {}", id);
        this.draftRepository.delete(draft);
    }

    private Draft setDataDraft(DraftFormDTO body, User user) {
        Draft draft = Draft.builder().description(body.getDescription()).rating(body.getRating()).build();
        Production production = searchProduction(body.getTmdbID(), body.getSeason(), body.getMedia());
        if (production != null) {
            if (this.draftRepository.existsByUserAndProduction(user, production)) {
                log.error("DraftServiceImpl.setDataDraft - Error: {}", MESSAGE_DRAFT_CONFLICT);
                throw new BusinessException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), MESSAGE_DRAFT_CONFLICT);
            }
            draft.setProduction(production);
        } else {
            Production productionResponse = this.productionService.mountProduction(body.getTmdbID(), body.getMedia(), body.getSeason());
            draft.setProduction(productionResponse);
        }
        draft.setUser(user);
        draft.setRating(body.getRating());
        draft.setDescription(body.getDescription());
        return draft;
    }

    private Production searchProduction(Integer tmdbID, Integer season, Media media) {
        if (media.equals(Media.MOVIE)) {
            return this.productionService.searchByTmdbID(tmdbID);
        }
        return verifyTV(tmdbID, season);
    }

    private Production verifyTV(Integer tmdbID, Integer season) {
        if (season == null) {
            log.error("DraftServiceImpl.verifyTV - Error: {}", MESSAGE_TV_BAD_REQUEST);
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), MESSAGE_TV_BAD_REQUEST);
        }
        return this.productionService.searchByTmdbIdAndSeason(tmdbID, season);
    }

    private Draft updateDataDraft(Draft draft, DraftFormDTO body) {
        draft.setRating(body.getRating());
        draft.setDescription(body.getDescription());
        return draft;
    }

}
