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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private static final String MESSAGE_EMAIL_CONFLICT = "Email is conflict";
    private static final String MESSAGE_USER_NOT_FOUND = "User not found";
    private static final String MESSAGE_DRAFT_NOT_FOUND = "Draft not found";

    @Override
    public DraftDTO save(DraftFormDTO body) {
        Draft draft = Draft.builder().description(body.getDescription()).rating(body.getRating()).build();
        User user = this.userRepository.findById(body.getUserID())
                .orElseThrow(() -> new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_USER_NOT_FOUND));
        draft.setUser(user);
        Optional<Production> production = this.productionRepository.findByTmdbID(body.getTmdbID());
        if (production.isPresent()) {
            if (this.draftRepository.existsByUserAndProduction(user, production.get())) {
                throw new BusinessException(STATUS_CONFLICT, CONFLICT, MESSAGE_EMAIL_CONFLICT);
            }
            draft.setProduction(production.get());
        } else {
            Production saveProduction = this.productionRepository.save(this.tmdbProxy.findProduction(body.getMedia(), body.getTmdbID()));
            draft.setProduction(saveProduction);
        }
        return mapper.map(this.draftRepository.save(draft), DraftDTO.class);
    }

    @Override
    public Page<DraftDTO> getDrafts(Pageable page, String userID) {
        User user = this.userRepository.findById(userID)
                .orElseThrow(() -> new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_USER_NOT_FOUND));
        Page<Draft> drafts = this.draftRepository.findByUser(user, page);
        List<DraftDTO> draftsDTO = drafts.getContent().stream()
                .map(draft -> mapper.map(draft, DraftDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(draftsDTO, page, drafts.getTotalElements());
    }

    @Override
    public DraftDTO searchDraft(String id) {
        Draft draft = this.draftRepository.findById(id)
                .orElseThrow(() -> new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_DRAFT_NOT_FOUND));
        return mapper.map(draft, DraftDTO.class);
    }

    @Override
    public DraftDTO updateDraft(String id, DraftFormDTO body) {
        Draft draft = this.draftRepository.findById(id)
                .orElseThrow(() -> new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_DRAFT_NOT_FOUND));
        this.userRepository.findById(body.getUserID())
                .orElseThrow(() -> new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_USER_NOT_FOUND));
        draft.setRating(body.getRating());
        draft.setDescription(body.getDescription());
        return mapper.map(this.draftRepository.save(draft), DraftDTO.class);
    }

    @Override
    public void deleteDraft(String id) {
        Draft draft = this.draftRepository.findById(id)
                .orElseThrow(() -> new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_DRAFT_NOT_FOUND));
        this.draftRepository.delete(draft);
    }

}
