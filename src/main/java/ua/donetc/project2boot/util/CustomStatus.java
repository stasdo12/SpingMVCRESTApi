package ua.donetc.project2boot.util;

import lombok.Data;
import lombok.Getter;

@Getter
public enum CustomStatus {


    SUCCESS(0, "Success"),
    NOT_FOUND(1, "NOT FOUND"),
    EXCEPTION(2, "Exception");

    private final int code;
    private final String message;

    CustomStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
