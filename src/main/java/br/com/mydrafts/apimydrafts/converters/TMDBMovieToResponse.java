package br.com.mydrafts.apimydrafts.converters;

import br.com.mydrafts.apimydrafts.dto.tmdb.CompanyDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.GenreDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieResponseDTO;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TMDBMovieToResponse extends AbstractConverter<MovieDTO, MovieResponseDTO> {

    @Override
    protected MovieResponseDTO convert(MovieDTO movie) {
        return MovieResponseDTO.builder()
            .id(movie.getId())
            .title(movie.getTitle())
            .titleOriginal(movie.getTitleOriginal())
            .tagline(movie.getTagline())
            .overview(movie.getOverview())
            .poster(movie.getPoster())
            .backdrop(movie.getBackdrop())
            .dateRelease(movie.getDateRelease())
            .language(movie.getLanguage())
            .genres(movie.getGenres().stream().map(GenreDTO::getName).collect(Collectors.toList()))
            .companies(movie.getCompanies().stream().map(CompanyDTO::getName).collect(Collectors.toList()))
            .build();
    }

}
