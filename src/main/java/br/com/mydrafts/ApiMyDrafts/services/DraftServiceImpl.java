package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.clients.TMDBClient;
import br.com.mydrafts.ApiMyDrafts.documents.Draft;
import br.com.mydrafts.ApiMyDrafts.documents.Production;
import br.com.mydrafts.ApiMyDrafts.documents.User;
import br.com.mydrafts.ApiMyDrafts.dto.*;
import br.com.mydrafts.ApiMyDrafts.exceptions.BusinessException;
import br.com.mydrafts.ApiMyDrafts.repository.DraftRepository;
import br.com.mydrafts.ApiMyDrafts.repository.ProductionRepository;
import br.com.mydrafts.ApiMyDrafts.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DraftServiceImpl implements DraftService {

    @Value("${tmdb.api-key}")
    private String apiKey;

    @Value("${tmdb.language}")
    private String language;

    @Autowired
    private DraftRepository draftRepository;

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TMDBClient client;

    @Autowired
    private ModelMapper mapper;

    @Override
    public DraftDTO save(DraftFormDTO body) {
        Draft draft = Draft.builder().description(body.getDescription()).rating(body.getRating()).build();
        User user = this.userRepository.findById(body.getUserID())
                .orElseThrow(() -> new BusinessException(404, "NOT FOUND", "User not found"));
        draft.setUser(mapper.map(user, UserDTO.class));
        Optional<Production> production = this.productionRepository.findByTmdbID(body.getTmdbID());
        if (!production.isEmpty()) {
            draft.setProduction(production.get());
        } else {
            draft.setProduction(findProduction(body.getMedia(), body.getTmdbID()));
        }
        return mapper.map(this.draftRepository.save(draft), DraftDTO.class);
    }

    @Override
    public Page<DraftDTO> getDrafts(String userID) {
        return null;
    }

    @Override
    public DraftDTO searchDraft(String id) {
        Draft draft = this.draftRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "NOT FOUND", "Draft not found"));
        return mapper.map(draft, DraftDTO.class);
    }

    @Override
    public DraftDTO updateDraft(String id, DraftFormDTO body) {
        return null;
    }

    @Override
    public void deleteDraft(String id) {

    }

    private Production findProduction(String media, String tmdbID) {
        Production production = Production.builder().media(media).tmdbID(tmdbID).build();
        if (media.equals("movie")) {
            TMDBMovieResponseDTO movie = getMovie(Integer.valueOf(tmdbID));
            production.setMovie(movie);
        } else {
            TMDBTvResponseDTO tv = getTV(Integer.valueOf(tmdbID));
            production.setTv(tv);
        }
        return this.productionRepository.save(production);
    }

    private TMDBMovieResponseDTO getMovie(Integer id) {
        TMDBMovieDTO movie = this.client.movie(id, apiKey, language);
        TMDBCreditsDTO credits = this.client.movieCredits(id, apiKey, language);
        TMDBMovieResponseDTO response = mapper.map(movie, TMDBMovieResponseDTO.class);
        response.setCrew(credits.getCrew().stream()
                .filter(crew -> crew.getJob().equals("Director") || crew.getJob().equals("Writer") || crew.getJob().equals("Executive Producer"))
                .collect(Collectors.toList()));
        return response;
    }

    private TMDBTvResponseDTO getTV(Integer id) {
        TMDBTvDTO tv = this.client.tv(id, apiKey, language);
        return mapper.map(tv, TMDBTvResponseDTO.class);
    }

}
