package hse.coursework.socialnetworkthoughts.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeparatorEnum {

    DOT("."),
    COLON(":"),
    SLASH("/");

    private final String value;
}
