package com.ecommerce.backend.exception;

import lombok.Data;

@Data
public class IdNotFound extends RuntimeException {
    public IdNotFound(String message) {
        super(message);
    }
}
