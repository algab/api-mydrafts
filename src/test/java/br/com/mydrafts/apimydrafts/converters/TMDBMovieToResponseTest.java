package br.com.mydrafts.apimydrafts.converters;

import br.com.mydrafts.apimydrafts.dto.tmdb.MovieResponseDTO;
import br.com.mydrafts.apimydrafts.fixtures.MediaFixture;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class TMDBMovieToResponseTest {

    private final TMDBMovieToResponse converter = new TMDBMovieToResponse();

    @Test
    void shouldReturnConversionSuccessfully() {
        MovieResponseDTO movieResponse = converter.convert(MediaFixture.movie());

        assertThat(movieResponse.getGenres()).isEqualTo(Collections.singletonList("Ação"));
        assertThat(movieResponse.getCompanies()).isEqualTo(Collections.singletonList("DC Comics"));
    }

}
