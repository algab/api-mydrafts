package br.com.mydrafts.apimydrafts.fixtures;

import br.com.mydrafts.apimydrafts.dto.tmdb.CompaniesDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.CreatedDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.CreditsDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.CrewDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.GenresDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.NetworkDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.SeasonDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvResponseDTO;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

public final class MediaFixture {

    public static MovieResponseDTO getMovie() {
        return MovieResponseDTO.builder()
            .id(1)
            .title("O Esquadrão Suicida")
            .titleOriginal("The Suicide Squad")
            .tagline("Eles estão loucos... para salvar o mundo.")
            .overview("Eles estão loucos... para salvar o mundo.")
            .poster("https://image.tmdb.org/t/p/original/wTS3dS2DJiMFFgqKDz5fxMTri.jpg")
            .backdrop("https://image.tmdb.org/t/p/original/jlGmlFOcfo8n5tURmhC7YVd4Iyy.jpg")
            .dateRelease(LocalDate.of(2021, 7, 28))
            .language("en")
            .genres(Collections.singletonList("Ação"))
            .companies(Collections.singletonList("Warner Bros. Pictures"))
            .crew(Collections.singletonList(MediaFixture.crewDirector()))
            .build();
    }

    public static TvResponseDTO getTV() {
        return TvResponseDTO.builder()
            .id(1)
            .title("The Office")
            .titleOriginal("The Office")
            .tagline("The Office")
            .overview("The Office")
            .poster("https://image.tmdb.org/t/p/original/oNZAJgcGVDxjv4PtJ2xU6MPZlHh.jpg")
            .backdrop("https://image.tmdb.org/t/p/original/vNpuAxGTl9HsUbHqam3E9CzqCvX.jpg")
            .dateRelease(LocalDate.of(2005, 3, 24))
            .lastEpisode(LocalDate.of(2013, 5, 16))
            .language("en")
            .created(Collections.singletonList("Greg Daniels"))
            .genres(Collections.singletonList("Comédia"))
            .networks(Collections.singletonList("NBC"))
            .build();
    }

    public static MovieDTO movie() {
        return MovieDTO.builder()
            .id(1)
            .title("O Esquadrão Suicida")
            .titleOriginal("The Suicide Squad")
            .tagline("Eles estão loucos... para salvar o mundo.")
            .overview("Eles estão loucos... para salvar o mundo.")
            .poster("https://image.tmdb.org/t/p/original/wTS3dS2DJiMFFgqKDz5fxMTri.jpg")
            .backdrop("https://image.tmdb.org/t/p/original/jlGmlFOcfo8n5tURmhC7YVd4Iyy.jpg")
            .dateRelease(LocalDate.of(2021, 7, 28))
            .genres(Collections.singletonList(MediaFixture.genres()))
            .companies(Collections.singletonList(MediaFixture.companies()))
            .language("en")
            .build();
    }

    public static TvDTO tv() {
        return TvDTO.builder()
            .id(1)
            .title("The Office")
            .titleOriginal("The Office")
            .tagline("The Office")
            .overview("The Office")
            .poster("https://image.tmdb.org/t/p/original/oNZAJgcGVDxjv4PtJ2xU6MPZlHh.jpg")
            .backdrop("https://image.tmdb.org/t/p/original/vNpuAxGTl9HsUbHqam3E9CzqCvX.jpg")
            .dateRelease(LocalDate.of(2005, 3, 24))
            .lastEpisode(LocalDate.of(2013, 5, 16))
            .language("en")
            .created(Collections.singletonList(MediaFixture.created()))
            .genres(Collections.singletonList(MediaFixture.genres()))
            .companies(Collections.singletonList(MediaFixture.companies()))
            .networks(Collections.singletonList(MediaFixture.network()))
            .seasons(Collections.singletonList(tvSeason()))
            .build();
    }

    public static CreditsDTO credits() {
        return CreditsDTO.builder()
            .id(1)
            .crew(Arrays.asList(MediaFixture.crewDirector(), MediaFixture.crewActor(), MediaFixture.crewWriter(), MediaFixture.crewExecutiveProducer()))
            .build();
    }

    public static CrewDTO crewDirector() {
        return CrewDTO.builder()
            .id(1)
            .gender(2)
            .name("James Gunn")
            .job("Director")
            .photo("https://image.tmdb.org/t/p/original/nHr6yzPF15jQz5eBke1SDNWectu.jpg")
            .build();
    }

    public static CrewDTO crewActor() {
        return CrewDTO.builder()
            .id(1)
            .gender(1)
            .name("Margot Robbie")
            .job("Actor")
            .photo("https://image.tmdb.org/t/p/original/nHr6yzPF15jQz5eBke1SDNWectu.jpg")
            .build();
    }

    public static CrewDTO crewWriter() {
        return CrewDTO.builder()
            .id(2)
            .gender(2)
            .name("James Gunn")
            .job("Writer")
            .photo("https://image.tmdb.org/t/p/original/nHr6yzPF15jQz5eBke1SDNWectu.jpg")
            .build();
    }

    public static CrewDTO crewExecutiveProducer() {
        return CrewDTO.builder()
            .id(2)
            .gender(2)
            .name("James Gunn")
            .job("Executive Producer")
            .photo("https://image.tmdb.org/t/p/original/nHr6yzPF15jQz5eBke1SDNWectu.jpg")
            .build();
    }

    public static GenresDTO genres() {
        return GenresDTO.builder()
            .id(1)
            .name("Ação")
            .build();
    }

    public static CompaniesDTO companies() {
        return CompaniesDTO.builder()
            .id(1)
            .name("DC Comics")
            .logo("http://teste.com/dc-comics.jpg")
            .country("us")
            .build();
    }

    public static CreatedDTO created() {
        return CreatedDTO.builder()
            .id(1)
            .name("Greg Daniels")
            .gender(2)
            .photo("http://teste.com/greg-daniels.jpg")
            .build();
    }

    public static NetworkDTO network() {
        return NetworkDTO.builder()
            .id(1)
            .name("NBC")
            .country("US")
            .logo("http://teste.com/nbc.jpg")
            .build();
    }

    public static SeasonDTO tvSeason() {
        return SeasonDTO.builder()
            .name("Season 1")
            .number(1)
            .totalEpisodes(10)
            .overview("Test")
            .poster("http://test.com/season.jpg")
            .date(LocalDate.of(2021, 12, 10))
            .build();
    }

}
