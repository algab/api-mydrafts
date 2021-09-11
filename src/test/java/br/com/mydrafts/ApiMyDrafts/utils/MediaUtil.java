package br.com.mydrafts.ApiMyDrafts.utils;

import br.com.mydrafts.ApiMyDrafts.dto.TMDBCrewDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBMovieResponseDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBTvResponseDTO;

import java.time.LocalDate;
import java.util.Arrays;

public final class MediaUtil {
    public static TMDBMovieResponseDTO getMovie() {
        return TMDBMovieResponseDTO.builder()
                .id(1)
                .title("O Esquadrão Suicida")
                .titleOriginal("The Suicide Squad")
                .tagline("Eles estão loucos... para salvar o mundo.")
                .overview("Eles estão loucos... para salvar o mundo.")
                .poster("https://image.tmdb.org/t/p/original/wTS3dS2DJiMFFgqKDz5fxMTri.jpg")
                .backdrop("https://image.tmdb.org/t/p/original/jlGmlFOcfo8n5tURmhC7YVd4Iyy.jpg")
                .dateRelease(LocalDate.of(2021, 07, 28))
                .language("en")
                .genres(Arrays.asList("Ação"))
                .companies(Arrays.asList("Warner Bros. Pictures"))
                .crew(Arrays.asList(crew()))
                .build();
    }

    public static TMDBTvResponseDTO getTV() {
        return TMDBTvResponseDTO.builder()
                .id(1)
                .title("The Office")
                .titleOriginal("The Office")
                .tagline("The Office")
                .overview("The Office")
                .poster("https://image.tmdb.org/t/p/original/oNZAJgcGVDxjv4PtJ2xU6MPZlHh.jpg")
                .backdrop("https://image.tmdb.org/t/p/original/vNpuAxGTl9HsUbHqam3E9CzqCvX.jpg")
                .dateRelease(LocalDate.of(2005, 03, 24))
                .lastEpisode(LocalDate.of(2013, 05, 16))
                .language("en")
                .created(Arrays.asList("Greg Daniels"))
                .genres(Arrays.asList("Comédia"))
                .networks(Arrays.asList("NBC"))
                .build();
    }

    public static TMDBCrewDTO crew() {
        return TMDBCrewDTO.builder()
                .id(1)
                .gender(2)
                .name("James Gunn")
                .job("Director")
                .photo("https://image.tmdb.org/t/p/original/nHr6yzPF15jQz5eBke1SDNWectu.jpg")
                .build();
    }
}
