package ua.donetc.project2boot.util;

import lombok.Data;

import java.util.Collection;


@Data
public class CustomResponse<T> {


    private int code;

    private String message;
    private Collection<T> responseList;

    public CustomResponse(Collection<T> response, CustomStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.responseList = response;
    }
}
