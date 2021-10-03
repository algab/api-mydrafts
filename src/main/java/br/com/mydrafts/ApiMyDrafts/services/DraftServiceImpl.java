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

    @Override
    public DraftDTO save(DraftFormDTO body) {
        Draft draft = Draft.builder().description(body.getDescription()).rating(body.getRating()).build();
        User user = this.userRepository.findById(body.getUserID())
                .orElseThrow(() -> new BusinessException(404, "NOT FOUND", "User not found"));
        draft.setUser(user);
        Optional<Production> production = this.productionRepository.findByTmdbID(body.getTmdbID());
        if (!production.isEmpty()) {
            if (this.draftRepository.existsByUserAndProduction(user, production.get())) {
                throw new BusinessException(409, "CONFLICT", "Draft already registered");
            }
            draft.setProduction(production.get());
        } else {
            draft.setProduction(findProduction(body.getMedia(), body.getTmdbID()));
        }
        return mapper.map(this.draftRepository.save(draft), DraftDTO.class);
    }

    @Override
    public Page<DraftDTO> getDrafts(Pageable page, String userID) {
        User user = this.userRepository.findById(userID)
                .orElseThrow(() -> new BusinessException(404, "NOT FOUND", "User not found"));
        Page<Draft> drafts = this.draftRepository.findByUser(user, page);
        List<DraftDTO> draftsDTO = drafts.getContent().stream()
                .map(draft -> mapper.map(draft, DraftDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(draftsDTO, page, drafts.getTotalElements());
    }

    @Override
    public DraftDTO searchDraft(String id) {
        Draft draft = this.draftRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "NOT FOUND", "Draft not found"));
        return mapper.map(draft, DraftDTO.class);
    }

    @Override
    public DraftDTO updateDraft(String id, DraftFormDTO body) {
        Draft draft = this.draftRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "NOT FOUND", "Draft not found"));
        this.userRepository.findById(body.getUserID())
                .orElseThrow(() -> new BusinessException(404, "NOT FOUND", "User not found"));
        draft.setRating(body.getRating());
        draft.setDescription(body.getDescription());
        return mapper.map(this.draftRepository.save(draft), DraftDTO.class);
    }

    @Override
    public void deleteDraft(String id) {
        Draft draft = this.draftRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "NOT FOUND", "Draft not found"));
        this.draftRepository.delete(draft);
    }

    private Production findProduction(String media, Integer tmdbID) {
        Production production = Production.builder().media(media).tmdbID(tmdbID).build();
        if (media.equals("movie")) {
            TMDBMovieResponseDTO movie = getMovie(tmdbID);
            production.setMovie(movie);
        } else {
            TMDBTvResponseDTO tv = getTV(tmdbID);
            production.setTv(tv);
        }
        return this.productionRepository.save(production);
    }

    private TMDBMovieResponseDTO getMovie(Integer id) {
        TMDBMovieDTO movie = this.tmdbProxy.getMovie(id);
        TMDBCreditsDTO credits = this.tmdbProxy.getMovieCredits(id);
        TMDBMovieResponseDTO response = mapper.map(movie, TMDBMovieResponseDTO.class);
        response.setCrew(credits.getCrew());
        return response;
    }

    private TMDBTvResponseDTO getTV(Integer id) {
        return mapper.map(this.tmdbProxy.getTV(id), TMDBTvResponseDTO.class);
    }

}
