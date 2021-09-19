package br.com.mydrafts.ApiMyDrafts.utils;

import br.com.mydrafts.ApiMyDrafts.dto.*;

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
                .crew(Arrays.asList(MediaUtil.crewDirector()))
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

    public static TMDBMovieDTO movie() {
        return TMDBMovieDTO.builder()
                .id(1)
                .title("O Esquadrão Suicida")
                .titleOriginal("The Suicide Squad")
                .tagline("Eles estão loucos... para salvar o mundo.")
                .overview("Eles estão loucos... para salvar o mundo.")
                .poster("https://image.tmdb.org/t/p/original/wTS3dS2DJiMFFgqKDz5fxMTri.jpg")
                .backdrop("https://image.tmdb.org/t/p/original/jlGmlFOcfo8n5tURmhC7YVd4Iyy.jpg")
                .dateRelease(LocalDate.of(2021, 07, 28))
                .genres(Arrays.asList(MediaUtil.genres()))
                .companies(Arrays.asList(MediaUtil.companies()))
                .language("en")
                .build();
    }

    public static TMDBTvDTO tv() {
        return TMDBTvDTO.builder()
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
                .created(Arrays.asList(MediaUtil.created()))
                .genres(Arrays.asList(MediaUtil.genres()))
                .companies(Arrays.asList(MediaUtil.companies()))
                .networks(Arrays.asList(MediaUtil.network()))
                .build();
    }

    public static TMDBCreditsDTO credits() {
        return TMDBCreditsDTO.builder()
                .id(1)
                .crew(Arrays.asList(MediaUtil.crewDirector(), MediaUtil.crewActor(), MediaUtil.crewWriter(), MediaUtil.crewExecutiveProducer()))
                .build();
    }

    public static TMDBCrewDTO crewDirector() {
        return TMDBCrewDTO.builder()
                .id(1)
                .gender(2)
                .name("James Gunn")
                .job("Director")
                .photo("https://image.tmdb.org/t/p/original/nHr6yzPF15jQz5eBke1SDNWectu.jpg")
                .build();
    }

    public static TMDBCrewDTO crewActor() {
        return TMDBCrewDTO.builder()
                .id(1)
                .gender(1)
                .name("Margot Robbie")
                .job("Actor")
                .photo("https://image.tmdb.org/t/p/original/nHr6yzPF15jQz5eBke1SDNWectu.jpg")
                .build();
    }

    public static TMDBCrewDTO crewWriter() {
        return TMDBCrewDTO.builder()
                .id(2)
                .gender(2)
                .name("James Gunn")
                .job("Writer")
                .photo("https://image.tmdb.org/t/p/original/nHr6yzPF15jQz5eBke1SDNWectu.jpg")
                .build();
    }

    public static TMDBCrewDTO crewExecutiveProducer() {
        return TMDBCrewDTO.builder()
                .id(2)
                .gender(2)
                .name("James Gunn")
                .job("Executive Producer")
                .photo("https://image.tmdb.org/t/p/original/nHr6yzPF15jQz5eBke1SDNWectu.jpg")
                .build();
    }

    public static TMDBGenresDTO genres() {
        return TMDBGenresDTO.builder()
                .id(1)
                .name("Ação")
                .build();
    }

    public static TMDBCompaniesDTO companies() {
        return TMDBCompaniesDTO.builder()
                .id(1)
                .name("DC Comics")
                .logo("http://teste.com/dc-comics.jpg")
                .country("us")
                .build();
    }

    public static TMDBCreatedDTO created() {
        return TMDBCreatedDTO.builder()
                .id(1)
                .name("Greg Daniels")
                .gender(2)
                .photo("http://teste.com/greg-daniels.jpg")
                .build();
    }

    public static TMDBNetworkDTO network() {
        return TMDBNetworkDTO.builder()
                .id(1)
                .name("NBC")
                .country("US")
                .logo("http://teste.com/nbc.jpg")
                .build();
    }
}
