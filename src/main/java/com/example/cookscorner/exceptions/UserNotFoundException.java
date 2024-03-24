package com.example.cookscorner.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends EntityNotFoundException {
    private final String detail;
    private final String name;

    public UserNotFoundException(String detail, String name) {
        super(String.format("User not found with %s: %s", detail, name));
        this.detail = detail;
        this.name = name;
    }
}
