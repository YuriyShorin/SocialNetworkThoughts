package hse.coursework.socialnetworkthoughts.controller;

import hse.coursework.socialnetworkthoughts.dto.feed.FeedResponseDto;
import hse.coursework.socialnetworkthoughts.dto.profile.SearchProfileResponseDto;
import hse.coursework.socialnetworkthoughts.security.model.User;
import hse.coursework.socialnetworkthoughts.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Search controller", description = "API для поиска")
@RequestMapping("/api/v1/search")
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "Найти список профилей по никнейму")
    @GetMapping("/profiles/{nickname}")
    public ResponseEntity<List<SearchProfileResponseDto>> searchProfilesByNickname(
            @PathVariable String nickname,
            @AuthenticationPrincipal User user) {
        return searchService.searchProfilesByNickname(nickname, user);
    }

    @Operation(summary = "Найти список постов по теме")
    @GetMapping("/posts/{theme}")
    public ResponseEntity<List<FeedResponseDto>> searchPostsByTheme(
            @PathVariable String theme,
            @AuthenticationPrincipal User user) {
        return searchService.searchPostsByTheme(theme, user);
    }
}
