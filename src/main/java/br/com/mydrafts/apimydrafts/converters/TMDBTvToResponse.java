package br.com.mydrafts.apimydrafts.converters;

import br.com.mydrafts.apimydrafts.dto.tmdb.*;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TMDBTvToResponse extends AbstractConverter<TvDTO, TvResponseDTO> {

    @Override
    protected TvResponseDTO convert(TvDTO tv) {
        return TvResponseDTO.builder()
            .id(tv.getId())
            .title(tv.getTitle())
            .titleOriginal(tv.getTitleOriginal())
            .tagline(tv.getTagline())
            .overview(tv.getOverview())
            .poster(tv.getPoster())
            .backdrop(tv.getBackdrop())
            .dateRelease(tv.getDateRelease())
            .lastEpisode(tv.getLastEpisode())
            .language(tv.getLanguage())
            .seasons(tv.getSeasons())
            .created(tv.getCreated().stream().map(CreatedDTO::getName).collect(Collectors.toList()))
            .genres(tv.getGenres().stream().map(GenresDTO::getName).collect(Collectors.toList()))
            .companies(tv.getCompanies().stream().map(CompaniesDTO::getName).collect(Collectors.toList()))
            .networks(tv.getNetworks().stream().map(NetworkDTO::getName).collect(Collectors.toList()))
            .build();
    }

}
