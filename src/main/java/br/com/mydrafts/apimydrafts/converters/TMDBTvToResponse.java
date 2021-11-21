package br.com.mydrafts.apimydrafts.converters;

import br.com.mydrafts.apimydrafts.dto.tmdb.TvDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvResponseDTO;
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
                .created(tv.getCreated().stream().map(created -> created.getName()).collect(Collectors.toList()))
                .genres(tv.getGenres().stream().map(genre -> genre.getName()).collect(Collectors.toList()))
                .companies(tv.getCompanies().stream().map(company -> company.getName()).collect(Collectors.toList()))
                .networks(tv.getNetworks().stream().map(network -> network.getName()).collect(Collectors.toList()))
                .build();
    }

}
