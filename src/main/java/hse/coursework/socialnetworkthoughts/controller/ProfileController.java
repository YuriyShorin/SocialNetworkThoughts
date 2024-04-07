package hse.coursework.socialnetworkthoughts.controller;

import hse.coursework.socialnetworkthoughts.dto.profile.ProfileResponse;
import hse.coursework.socialnetworkthoughts.security.model.User;
import hse.coursework.socialnetworkthoughts.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Profile controller", description = "Profile API")
@RequestMapping("/api/v1/profile")
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @Operation(summary = "Get authenticated user profile")
    @ApiResponse(
            responseCode = "200",
            description = "Profile found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponse.class))})
    @GetMapping
    public ResponseEntity<ProfileResponse> getAuthenticatedUserProfile(@AuthenticationPrincipal User user) {
        return profileService.getAuthenticatedUserProfile(user);
    }

    @Operation(summary = "Get profile by id")
    @ApiResponse(
            responseCode = "200",
            description = "Profile found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponse.class))})
    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileResponse> getProfileById(@PathVariable UUID profileId) {
        return profileService.getProfileById(profileId);
    }

    @Operation(summary = "Subscribe")
    @ApiResponse(
            responseCode = "200",
            description = "Subscribed"
    )
    @PostMapping("/subscribe/{profileId}")
    public ResponseEntity<?> subscribe(@PathVariable UUID profileId, @AuthenticationPrincipal User user) {
        return profileService.subscribe(profileId, user);
    }

    @Operation(summary = "Unsubscribe")
    @ApiResponse(
            responseCode = "200",
            description = "Unsubscribed"
    )
    @PostMapping("/unsubscribe/{profileId}")
    public ResponseEntity<?> unsubscribe(@PathVariable UUID profileId, @AuthenticationPrincipal User user) {
        return profileService.unsubscribe(profileId, user);
    }
}
