package hse.coursework.socialnetworkthoughts.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessageEnum {

    PROFILE_NOT_FOUND_MESSAGE("Профиль не найден"),
    NOT_COMMENT_OWNER_MESSAGE("Вы не являетесь владельцем комментария или комментарий не найден"),
    NOT_POST_OWNER_MESSAGE("Вы не являетесь владельцем поста или пост не найден"),
    POST_ALREADY_LIKED_MESSAGE("Вы уже оценили этот пост"),
    POST_NOT_FOUND_MESSAGE("Пост не найден"),
    POST_NOT_LIKED_MESSAGE("Вы еще не оценили этот комментарий"),
    INVALID_AUTHENTICATION_TOKEN_MESSAGE("Недействительный токен аутентификации"),
    UNEXPECTED_ERROR_MESSAGE("Непредвиденная ошибка"),
    COULD_NOT_DETERMINE_FILE_FORMAT_MESSAGE("Не удалось определить формат файла"),
    SUBSCRIBE_TO_YOURSELF_MESSAGE("Нельзя подписаться на самого себя"),
    ALREADY_SUBSCRIBED_MESSAGE("Вы уже подписаны на данного пользователя");

    private final String value;
}
