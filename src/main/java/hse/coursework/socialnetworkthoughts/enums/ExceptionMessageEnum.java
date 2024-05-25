package hse.coursework.socialnetworkthoughts.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessageEnum {

    INVALID_AUTHENTICATION_TOKEN_MESSAGE("Недействительный токен аутентификации"),

    PROFILE_NOT_FOUND_MESSAGE("Профиль не найден"),
    POST_NOT_FOUND_MESSAGE("Пост не найден"),
    COMMENT_NOT_FOUND_MESSAGE("Комментарий не найден"),

    NOT_POST_OWNER_MESSAGE("Вы не являетесь владельцем поста или пост не найден"),
    NOT_COMMENT_OWNER_MESSAGE("Вы не являетесь владельцем комментария или комментарий не найден"),

    SUBSCRIBE_TO_YOURSELF_MESSAGE("Нельзя подписаться на самого себя"),
    ALREADY_SUBSCRIBED_MESSAGE("Вы уже подписаны на данного пользователя"),

    POST_ALREADY_LIKED_MESSAGE("Вы уже оценили этот пост"),
    POST_NOT_LIKED_MESSAGE("Вы еще не оценили этот комментарий"),

    REPOSTING_OWN_POST_MESSAGE("Вы не можете репостнуть свой пост"),

    COULD_NOT_DETERMINE_FILE_FORMAT_MESSAGE("Не удалось определить формат файла"),

    UNEXPECTED_ERROR_MESSAGE("Непредвиденная ошибка");

    private final String value;
}
