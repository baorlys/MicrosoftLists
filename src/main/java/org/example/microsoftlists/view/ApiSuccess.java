package org.example.microsoftlists.view;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiSuccess extends ApiResponse {
    Object data;


    public ApiSuccess(String message, Object data) {
        super(message);
        this.data = data;
    }
}