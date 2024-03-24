package hse.coursework.socialnetworkthoughts.controller;

import hse.coursework.socialnetworkthoughts.security.model.User;
import hse.coursework.socialnetworkthoughts.service.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Search controller", description = "Search API")
@RequestMapping("/api/v1/feed")
@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @Operation(summary = "Get feed")
    @ApiResponse(
            responseCode = "200",
            description = "Feed found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})
    @GetMapping
    public ResponseEntity<?> getFeed(@AuthenticationPrincipal User user) {
        return feedService.getFeed(user);
    }
}
