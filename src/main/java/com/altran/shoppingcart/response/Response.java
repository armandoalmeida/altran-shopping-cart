package com.altran.shoppingcart.response;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

    private T data;
    private List<String> errors;

    public Response() {
    }

    public Response(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getErrors() {
        if (this.errors == null) {
            this.errors = new ArrayList<String>();
        }
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
