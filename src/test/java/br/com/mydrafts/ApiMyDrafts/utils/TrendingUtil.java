package br.com.mydrafts.ApiMyDrafts.utils;

import br.com.mydrafts.ApiMyDrafts.constants.Media;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBResponseDTO;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBResultDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class TrendingUtil {
    public static TMDBResultDTO trending() {
        return TMDBResultDTO.builder()
                .id(1)
                .title("Spider-Man Far From Home")
                .titleOriginal("Spider-Man Far From Home")
                .poster("http://teste.com/spider.jpg")
                .media(Media.movie)
                .dateRelease(LocalDate.of(2019, 07, 04))
                .language("en")
                .popularity(100L)
                .build();
    }

    public static TMDBResponseDTO responseTrendingMovie() {
        List<TMDBResultDTO> listMovies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listMovies.add(TMDBResultDTO.builder()
                    .id(i)
                    .title(String.format("Spider-Man %d", i))
                    .titleOriginal(String.format("Spider-Man %d", i))
                    .poster("http://teste.com/spider.jpg")
                    .media(Media.movie)
                    .dateRelease(LocalDate.of(2019, 07, i + 1))
                    .language("en")
                    .popularity(Long.valueOf(100 + i))
                    .build());
        }
        return TMDBResponseDTO.builder()
                .results(listMovies)
                .build();
    }

    public static TMDBResponseDTO responseTrendingTV() {
        List<TMDBResultDTO> listTV = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listTV.add(TMDBResultDTO.builder()
                    .id(i)
                    .title(String.format("The Simpsons Season %d", i))
                    .titleOriginal(String.format("The Simpsons Season %d", i))
                    .poster(String.format("http://teste.com/simpsons-%d.jpg", i))
                    .media(Media.tv)
                    .dateRelease(LocalDate.of(2019, 07, i + 1))
                    .language("en")
                    .popularity(Long.valueOf(10 + i))
                    .build());
        }
        return TMDBResponseDTO.builder()
                .results(listTV)
                .build();
    }
}
