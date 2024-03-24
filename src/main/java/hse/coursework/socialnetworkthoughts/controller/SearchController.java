package hse.coursework.socialnetworkthoughts.controller;

import hse.coursework.socialnetworkthoughts.dto.post.SearchPostResponse;
import hse.coursework.socialnetworkthoughts.dto.profile.SearchProfileResponse;
import hse.coursework.socialnetworkthoughts.security.model.User;
import hse.coursework.socialnetworkthoughts.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Search controller", description = "Search API")
@RequestMapping("/api/v1/search")
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "Search profiles by nickname")
    @ApiResponse(
            responseCode = "200",
            description = "Profiles found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})
    @GetMapping("/profiles/{nickname}")
    public ResponseEntity<List<SearchProfileResponse>> searchProfilesByNickname(@PathVariable String nickname, @AuthenticationPrincipal User user) {
        return searchService.searchProfilesByNickname(nickname, user);
    }

    @Operation(summary = "Search posts by theme")
    @ApiResponse(
            responseCode = "200",
            description = "Posts found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})
    @GetMapping("/posts/{theme}")
    public ResponseEntity<List<SearchPostResponse>> searchPostsByTheme(@PathVariable String theme) {
        return searchService.searchPostsByTheme(theme);
    }
}
