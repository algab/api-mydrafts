package br.com.mydrafts.ApiMyDrafts.utils;

import br.com.mydrafts.ApiMyDrafts.constants.Media;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBResultDTO;

import java.time.LocalDate;

public final class SearchUtil {
    public static TMDBResultDTO searchMovie() {
        return TMDBResultDTO.builder()
                .id(1)
                .title("Shang-Chi e a Lenda dos Dez Anéis")
                .titleOriginal("Shang-Chi and the Legend of the Ten Rings")
                .poster("https://image.tmdb.org/t/p/original/6T0r3jBs0xbmzVnM9u7e3vUXMYk.jpg")
                .media(Media.movie)
                .dateRelease(LocalDate.of(2021, 9, 01))
                .language("en")
                .popularity(100L)
                .build();
    }

    public static TMDBResultDTO searchTV() {
        return TMDBResultDTO.builder()
                .id(1)
                .title("What If...?")
                .titleOriginal("What If...?")
                .poster("https://image.tmdb.org/t/p/original/e7n55C4027aRPHNmjE8XIk8nKvZ.jpg")
                .media(Media.tv)
                .dateRelease(LocalDate.of(2021, 8, 11))
                .language("en")
                .popularity(100L)
                .build();
    }
}
