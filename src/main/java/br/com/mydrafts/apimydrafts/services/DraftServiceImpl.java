package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.documents.DraftDocument;
import br.com.mydrafts.apimydrafts.documents.ProductionDocument;
import br.com.mydrafts.apimydrafts.documents.UserDocument;
import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.dto.DraftFormDTO;
import br.com.mydrafts.apimydrafts.dto.DraftUpdateFormDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvResponseDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.DraftRepository;
import br.com.mydrafts.apimydrafts.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static br.com.mydrafts.apimydrafts.constants.MyDraftsMessage.DRAFT_CONFLICT;
import static br.com.mydrafts.apimydrafts.constants.MyDraftsMessage.DRAFT_NOT_FOUND;
import static br.com.mydrafts.apimydrafts.constants.MyDraftsMessage.DRAFT_TV_BAD_REQUEST;
import static br.com.mydrafts.apimydrafts.constants.MyDraftsMessage.DRAFT_TV_SEASON;
import static br.com.mydrafts.apimydrafts.constants.MyDraftsMessage.USER_NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
public class DraftServiceImpl implements DraftService {

    private DraftRepository draftRepository;

    private UserRepository userRepository;

    private ProductionService productionService;

    private ModelMapper mapper;

    @Override
    public DraftDTO save(DraftFormDTO body) {
        UserDocument user = this.userRepository.findById(body.getUserID())
            .orElseThrow(() -> {
                log.error("DraftServiceImpl.save - Error: {}", USER_NOT_FOUND);
                return new BusinessException(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    USER_NOT_FOUND
                );
            });
        DraftDTO draft = mapper.map(this.draftRepository.save(setDataDraft(body, user)), DraftDTO.class);
        log.info("DraftServiceImpl.save - Draft saved - draft: [{}]", draft);
        return draft;
    }

    @Override
    public DraftDTO searchDraft(String id) {
        DraftDocument draft = this.draftRepository.findById(id)
            .orElseThrow(() -> {
                log.error("DraftServiceImpl.searchDraft - Error: {}", DRAFT_NOT_FOUND);
                return new BusinessException(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    DRAFT_NOT_FOUND
                );
            });
        return mapper.map(draft, DraftDTO.class);
    }

    @Override
    public DraftDTO updateDraft(String id, DraftUpdateFormDTO body) {
        DraftDocument findDraft = this.draftRepository.findById(id)
            .orElseThrow(() -> {
                log.error("DraftServiceImpl.updateDraft - Search draft - Error: {}", DRAFT_NOT_FOUND);
                return new BusinessException(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    DRAFT_NOT_FOUND
                );
            });
        if (!this.userRepository.existsById(body.getUserID())) {
            log.error("DraftServiceImpl.updateDraft - Search user - Error: {}", USER_NOT_FOUND);
            throw new BusinessException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), USER_NOT_FOUND);
        }
        DraftDTO draft = mapper.map(this.draftRepository.save(updateDataDraft(findDraft, body)), DraftDTO.class);
        log.info("DraftServiceImpl.updateDraft - Draft updated - draft: [{}]", draft);
        return draft;
    }

    @Override
    public void deleteDraft(String id) {
        DraftDocument draft = this.draftRepository.findById(id)
            .orElseThrow(() -> {
                log.error("DraftServiceImpl.updateDraft - Error: {}", DRAFT_NOT_FOUND);
                return new BusinessException(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    DRAFT_NOT_FOUND
                );
            });
        this.draftRepository.delete(draft);
        log.info("DraftServiceImpl.deleteDraft - Draft removed - id: [{}]", id);
    }

    private DraftDocument setDataDraft(DraftFormDTO body, UserDocument user) {
        DraftDocument draft = DraftDocument.builder().description(body.getDescription()).rating(body.getRating()).build();
        ProductionDocument production = searchProduction(body.getTmdbID(), body.getSeason(), body.getMedia());
        if (this.draftRepository.existsByUserAndProduction(user, production)) {
            log.error("DraftServiceImpl.setDataDraft - Error: {}", DRAFT_CONFLICT);
            throw new BusinessException(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                DRAFT_CONFLICT
            );
        }
        draft.setUser(user);
        draft.setRating(body.getRating());
        draft.setDescription(body.getDescription());
        draft.setSeason(body.getSeason());
        draft.setProduction(production);
        return draft;
    }

    private ProductionDocument searchProduction(Integer tmdbID, Integer season, Media media) {
        if (media.equals(Media.MOVIE)) {
            Optional<ProductionDocument> production = this.productionService.searchProduction(tmdbID, media);
            return production.orElseGet(() -> this.productionService.mountProduction(tmdbID, media));
        }
        return verifyTV(tmdbID, season, media);
    }

    private ProductionDocument verifyTV(Integer tmdbID, Integer season, Media media) {
        if (Objects.isNull(season)) {
            log.error("DraftServiceImpl.verifyTV - Error: {}", DRAFT_TV_BAD_REQUEST);
            throw new BusinessException(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                DRAFT_TV_BAD_REQUEST
            );
        }
        Optional<ProductionDocument> production = this.productionService.searchProduction(tmdbID, media);
        if (production.isPresent()) {
            return checkSeason(production.get(), season);
        } else {
            ProductionDocument productionSaved = this.productionService.mountProduction(tmdbID, media);
            return checkSeason(productionSaved, season);
        }
    }

    private ProductionDocument checkSeason(ProductionDocument production, Integer season) {
        TvResponseDTO tv = (TvResponseDTO) production.getData();
        if (seasonNotExists(tv, season)) {
            log.error("DraftServiceImpl.checkSeason - Error: {}", DRAFT_TV_SEASON);
            throw new BusinessException(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                DRAFT_TV_SEASON
            );
        } else {
            return production;
        }
    }

    private DraftDocument updateDataDraft(DraftDocument draft, DraftUpdateFormDTO body) {
        if (draft.getProduction().getMedia().equals(Media.TV)) {
            if (Objects.isNull(body.getSeason())) {
                log.error("DraftServiceImpl.updateDataDraft - Error: {}", DRAFT_TV_BAD_REQUEST);
                throw new BusinessException(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    DRAFT_TV_BAD_REQUEST
                );
            } else if (seasonNotExists((TvResponseDTO) draft.getProduction().getData(), body.getSeason())) {
                log.error("DraftServiceImpl.updateDataDraft - Error: {}", DRAFT_TV_SEASON);
                throw new BusinessException(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    DRAFT_TV_SEASON
                );
            }
        }
        draft.setRating(body.getRating());
        draft.setSeason(body.getSeason());
        draft.setDescription(body.getDescription());
        return draft;
    }

    private boolean seasonNotExists(TvResponseDTO tv, Integer season) {
        return tv.getSeasons().stream().noneMatch(data -> data.getNumber().equals(season));
    }

}
