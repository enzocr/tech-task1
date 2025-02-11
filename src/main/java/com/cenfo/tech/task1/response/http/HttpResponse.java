package com.cenfo.tech.task1.response.http;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class HttpResponse<T> {

    private String message;
    private T data;
    private MetaResponse meta;


    public HttpResponse(String message) {
        this.message = message;
    }

    public HttpResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public HttpResponse(String message, MetaResponse meta) {
        this.message = message;
        this.meta = meta;
    }

    public HttpResponse(String message, T data, MetaResponse meta) {
        this.message = message;
        this.data = data;
        this.meta = meta;
    }
}