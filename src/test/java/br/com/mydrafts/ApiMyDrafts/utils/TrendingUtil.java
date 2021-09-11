package br.com.mydrafts.ApiMyDrafts.utils;

import br.com.mydrafts.ApiMyDrafts.constants.Media;
import br.com.mydrafts.ApiMyDrafts.dto.TMDBResultDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

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
}
