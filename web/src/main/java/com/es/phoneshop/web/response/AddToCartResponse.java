package com.es.phoneshop.web.response;

public class AddToCartResponse {
    private boolean success;
    private Long phoneId;
    private String errorCode;

    public AddToCartResponse() {
    }

    public AddToCartResponse(boolean success) {
        this.success = success;
    }

    public AddToCartResponse(boolean success, Long phoneId, String errorCode) {
        this.success = success;
        this.phoneId = phoneId;
        this.errorCode = errorCode;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}