package br.com.mydrafts.ApiMyDrafts.converters;

import br.com.mydrafts.ApiMyDrafts.dto.TMDBMovieDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBMovieResponseDTO;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TMDBMovie extends AbstractConverter<TMDBMovieDTO, TMDBMovieResponseDTO> {

    @Override
    protected TMDBMovieResponseDTO convert(TMDBMovieDTO movie) {
        return TMDBMovieResponseDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .titleOriginal(movie.getTitleOriginal())
                .tagline(movie.getTagline())
                .overview(movie.getOverview())
                .poster(movie.getPoster())
                .backdrop(movie.getBackdrop())
                .dateRelease(movie.getDateRelease())
                .language(movie.getLanguage())
                .genres(movie.getGenres().stream().map(genre -> genre.getName()).collect(Collectors.toList()))
                .companies(movie.getCompanies().stream().map(company -> company.getName()).collect(Collectors.toList()))
                .build();
    }

}
