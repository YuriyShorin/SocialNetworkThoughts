package hse.coursework.socialnetworkthoughts.controller;

import hse.coursework.socialnetworkthoughts.dto.profile.ProfileResponseDto;
import hse.coursework.socialnetworkthoughts.dto.profile.SubscriptionResponseDto;
import hse.coursework.socialnetworkthoughts.dto.profile.UpdateProfileRequestDto;
import hse.coursework.socialnetworkthoughts.security.model.User;
import hse.coursework.socialnetworkthoughts.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Profile controller", description = "API для профилей")
@RequestMapping("/api/v1/profile")
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @Operation(summary = "Получение профиля для авторизированного пользователя")
    @GetMapping
    public ResponseEntity<ProfileResponseDto> getAuthenticatedUserProfile(@AuthenticationPrincipal User user) {
        return profileService.getAuthenticatedUserProfile(user);
    }

    @Operation(summary = "Изменить профиль")
    @PutMapping
    public ResponseEntity<?> updateProfile(
            @ModelAttribute UpdateProfileRequestDto updateProfileRequestDto,
            @AuthenticationPrincipal User user) {
        return profileService.updateProfile(user, updateProfileRequestDto);
    }

    @Operation(summary = "Получить профиль по id")
    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileResponseDto> getProfileById(@PathVariable UUID profileId) {
        return profileService.getProfileById(profileId);
    }

    @Operation(summary = "Подписаться на профиль по id")
    @PostMapping("/subscribe/{profileId}")
    public ResponseEntity<?> subscribe(
            @PathVariable UUID profileId,
            @AuthenticationPrincipal User user) {
        return profileService.subscribe(profileId, user);
    }

    @Operation(summary = "Отписаться на профиль по id")
    @PostMapping("/unsubscribe/{profileId}")
    public ResponseEntity<?> unsubscribe(
            @PathVariable UUID profileId,
            @AuthenticationPrincipal User user) {
        return profileService.unsubscribe(profileId, user);
    }

    @Operation(summary = "Получение подписок авторизированного пользователя")
    @GetMapping("/subscriptions")
    public List<SubscriptionResponseDto> getProfileSubscriptions(@AuthenticationPrincipal User user) {
        return profileService.getProfileSubscriptions(user);
    }

    @Operation(summary = "Получение подписок определенного пользователя")
    @GetMapping("/{id}/subscriptions")
    public List<SubscriptionResponseDto> getProfileSubscriptionsByProfileId(
            @PathVariable(value = "id") UUID profileId,
            @AuthenticationPrincipal User user) {
        return profileService.getProfileSubscriptionsByProfileId(profileId, user);
    }

    @Operation(summary = "Получение подписчиков авторизированного пользователя")
    @GetMapping("/subscribers")
    public List<SubscriptionResponseDto> getProfileSubscribers(@AuthenticationPrincipal User user) {
        return profileService.getProfileSubscribers(user);
    }

    @Operation(summary = "Получение подписчиков определенного пользователя")
    @GetMapping("/{id}/subscribers")
    public List<SubscriptionResponseDto> getProfileSubscribersByProfileId(
            @PathVariable(value = "id") UUID profileId,
            @AuthenticationPrincipal User user) {
        return profileService.getProfileSubscribersByProfileId(profileId, user);
    }
}
