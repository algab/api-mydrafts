package br.com.mydrafts.apimydrafts.builder;

import br.com.mydrafts.apimydrafts.dto.tmdb.CompaniesDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.CreatedDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.CreditsDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.CrewDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.GenresDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.MovieResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.NetworkDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.TvResponseDTO;

import java.time.LocalDate;
import java.util.Arrays;

public final class MediaBuilder {
    public static MovieResponseDTO getMovie() {
        return MovieResponseDTO.builder()
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
                .crew(Arrays.asList(MediaBuilder.crewDirector()))
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
                .dateRelease(LocalDate.of(2005, 03, 24))
                .lastEpisode(LocalDate.of(2013, 05, 16))
                .language("en")
                .created(Arrays.asList("Greg Daniels"))
                .genres(Arrays.asList("Comédia"))
                .networks(Arrays.asList("NBC"))
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
                .dateRelease(LocalDate.of(2021, 07, 28))
                .genres(Arrays.asList(MediaBuilder.genres()))
                .companies(Arrays.asList(MediaBuilder.companies()))
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
                .dateRelease(LocalDate.of(2005, 03, 24))
                .lastEpisode(LocalDate.of(2013, 05, 16))
                .language("en")
                .created(Arrays.asList(MediaBuilder.created()))
                .genres(Arrays.asList(MediaBuilder.genres()))
                .companies(Arrays.asList(MediaBuilder.companies()))
                .networks(Arrays.asList(MediaBuilder.network()))
                .build();
    }

    public static CreditsDTO credits() {
        return CreditsDTO.builder()
                .id(1)
                .crew(Arrays.asList(MediaBuilder.crewDirector(), MediaBuilder.crewActor(), MediaBuilder.crewWriter(), MediaBuilder.crewExecutiveProducer()))
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

}
