package br.com.mydrafts.apimydrafts.fixtures;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResultDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class TrendingFixture {

    public static ResultDTO trending() {
        return ResultDTO.builder()
            .id(1)
            .title("Spider-Man Far From Home")
            .titleOriginal("Spider-Man Far From Home")
            .poster("http://teste.com/spider.jpg")
            .media(Media.MOVIE)
            .dateRelease(LocalDate.of(2019, 7, 4))
            .language("en")
            .popularity(100L)
            .build();
    }

    public static ResponseDTO responseTrendingMovie() {
        List<ResultDTO> listMovies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listMovies.add(ResultDTO.builder()
                .id(i)
                .title(String.format("Spider-Man %d", i))
                .titleOriginal(String.format("Spider-Man %d", i))
                .poster("http://teste.com/spider.jpg")
                .media(Media.MOVIE)
                .dateRelease(LocalDate.of(2019, 7, i + 1))
                .language("en")
                .popularity((long) (100 + i))
                .build());
        }
        return ResponseDTO.builder()
            .results(listMovies)
            .build();
    }

    public static ResponseDTO responseTrendingTV() {
        List<ResultDTO> listTV = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listTV.add(ResultDTO.builder()
                .id(i)
                .title(String.format("The Simpsons Season %d", i))
                .titleOriginal(String.format("The Simpsons Season %d", i))
                .poster(String.format("http://teste.com/simpsons-%d.jpg", i))
                .media(Media.TV)
                .dateRelease(LocalDate.of(2019, 7, i + 1))
                .language("en")
                .popularity((long) (10 + i))
                .build());
        }
        return ResponseDTO.builder()
            .results(listTV)
            .build();
    }

}
