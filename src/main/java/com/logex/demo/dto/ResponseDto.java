package com.logex.demo.dto;


import java.util.Arrays;
import java.util.List;


public class ResponseDto<T> {

    private List<String> responseMessage;
    private boolean isError;
    private Long totalPages;
    private T response;
    private List<T> responseList;

    public ResponseDto(String responseMessage, boolean isError) {
        this.responseMessage = Arrays.asList(responseMessage);
        this.isError = isError;
    }

    public ResponseDto(List<String> responseMessage, boolean isError) {
        this.responseMessage = responseMessage;
        this.isError = isError;
    }

    public ResponseDto(String responseMessage, boolean isError, T response) {
        this.responseMessage = Arrays.asList(responseMessage);
        this.isError = isError;
        this.response = response;
    }

    public ResponseDto(List<String> responseMessage, boolean isError, T response) {
        this.responseMessage = responseMessage;
        this.isError = isError;
        this.response = response;
    }

    public ResponseDto(boolean isError, Long totalPages, T response) {
        this.responseMessage = responseMessage;
        this.isError = isError;
        this.totalPages = totalPages;
        this.response = response;
    }

    public ResponseDto(String responseMessage, boolean isError, Long totalPages, T response) {
        this.responseMessage = Arrays.asList(responseMessage);
        this.isError = isError;
        this.totalPages = totalPages;
        this.response = response;
    }

    public ResponseDto(List<String> responseMessage, boolean isError, Long totalPages, T response) {
        this.responseMessage = responseMessage;
        this.isError = isError;
        this.totalPages = totalPages;
        this.response = response;
    }

    public ResponseDto(boolean isError, T response) {
        this.isError = isError;
        this.response = response;
    }

    public ResponseDto(Boolean isError, List<T> responseList) {
        this.isError = isError;
        this.responseList = responseList;
    }


    public List<String> getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(List<String> responseMessage) {
        this.responseMessage = responseMessage;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
