package br.com.mydrafts.apimydrafts.fixtures;

import br.com.mydrafts.apimydrafts.constants.Media;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResponseDTO;
import br.com.mydrafts.apimydrafts.dto.tmdb.ResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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

    public static Page<ResultDTO> getPageSearchMovie() {
        PageRequest page = PageRequest.of(0, 10);
        List<ResultDTO> contents = Collections.singletonList(SearchFixture.searchMovie());
        return new PageImpl<>(contents, page, 1);
    }

    public static Page<ResultDTO> getPageSearchTV() {
        PageRequest page = PageRequest.of(0, 10);
        List<ResultDTO> contents = Collections.singletonList(SearchFixture.searchTV());
        return new PageImpl<>(contents, page, 1);
    }

}
