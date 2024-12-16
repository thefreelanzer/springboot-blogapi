package com.blog.blog.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppResponse<T> {
    private T data;

    private boolean success;

    private String message;


    /**
     * constructor.
     *
     * @param datum  datum
     * @param status success
     * @param msg    message
     */
    public AppResponse(final T datum, final boolean status,
                       final String msg) {
        this.data = datum;
        this.success = status;
        this.message = msg;
    }

    /**
     * The default constructor.
     */
    public AppResponse() {

    }
}
