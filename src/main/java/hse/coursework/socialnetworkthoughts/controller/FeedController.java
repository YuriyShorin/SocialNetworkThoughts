package hse.coursework.socialnetworkthoughts.controller;

import hse.coursework.socialnetworkthoughts.security.model.User;
import hse.coursework.socialnetworkthoughts.service.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Feed controller", description = "API для ленты новостей")
@RequestMapping("/api/v1/feed")
@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @Operation(summary = "Получить ленту новостей")
    @GetMapping
    public ResponseEntity<?> getFeed(@AuthenticationPrincipal User user) {
        return feedService.getFeed(user);
    }
}