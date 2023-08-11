package com.poly.payload.response;

public class SuccessAPIResponse extends APIResponse {
    public SuccessAPIResponse() {
        this.message = "Success";
        this.success = true;
    }

    public SuccessAPIResponse(Object data) {
        this.message = "Success";
        this.success = true;
        this.data = data;
    }
    public SuccessAPIResponse(String message) {
        this.message = message;
        this.success = true;
    }

    public SuccessAPIResponse(String message,Object data) {
        this.message = message;
        this.success = true;
        this.data = data;
    }
}
