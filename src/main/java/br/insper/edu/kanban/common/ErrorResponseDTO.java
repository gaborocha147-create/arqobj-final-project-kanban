package br.insper.edu.kanban.common;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponseDTO {

    private int status;
    private List<String> errors = new ArrayList<>();
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
