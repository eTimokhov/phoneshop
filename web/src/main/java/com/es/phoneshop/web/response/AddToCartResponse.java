package com.es.phoneshop.web.response;

public class AddToCartResponse {
    private boolean success;
    private Long phoneId;
    private String errorMessage;

    public AddToCartResponse() {
    }

    public AddToCartResponse(boolean success) {
        this.success = success;
    }

    public AddToCartResponse(boolean success, Long phoneId, String errorMessage) {
        this.success = success;
        this.phoneId = phoneId;
        this.errorMessage = errorMessage;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}