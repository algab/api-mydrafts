package br.com.mydrafts.apimydrafts.fixtures;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResultDTO;

import java.time.LocalDate;
import java.util.Collections;

public final class SearchFixture {

    public static ResultDTO searchMovie() {
        return ResultDTO.builder()
            .id(1)
            .title("Shang-Chi e a Lenda dos Dez Anéis")
            .titleOriginal("Shang-Chi and the Legend of the Ten Rings")
            .poster("https://image.tmdb.org/t/p/original/6T0r3jBs0xbmzVnM9u7e3vUXMYk.jpg")
            .media(Media.MOVIE)
            .dateRelease(LocalDate.of(2021, 9, 1))
            .language("en")
            .popularity(100L)
            .build();
    }

    public static ResultDTO searchTV() {
        return ResultDTO.builder()
            .id(1)
            .title("What If...?")
            .titleOriginal("What If...?")
            .poster("https://image.tmdb.org/t/p/original/e7n55C4027aRPHNmjE8XIk8nKvZ.jpg")
            .media(Media.TV)
            .dateRelease(LocalDate.of(2021, 8, 11))
            .language("en")
            .popularity(100L)
            .build();
    }

    public static ResponseDTO responseSearchMovie() {
        return ResponseDTO.builder()
            .results(Collections.singletonList(SearchFixture.searchMovie()))
            .build();
    }

    public static ResponseDTO responseSearchTV() {
        return ResponseDTO.builder()
            .results(Collections.singletonList(SearchFixture.searchTV()))
            .build();
    }

}
